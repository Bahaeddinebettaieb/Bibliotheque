package com.example.lenovo.newpj;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AddBook extends AppCompatActivity {

    ActionBar actionBar;
    EditText titre, auteur, specialite, nbCopie, origine, nomDonneur, situation, cin, telephoneDonneur, description;
    ImageView imageIv;
    Button uploadBtn;
    BottomNavigationView navigation;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;

    private static final int IMAGE_PICK_CAMERA_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;


    String[] cameraPerssions;
    String[] storagesPerssions;
    Uri image_uri = null;

    String nomPrenom,email,uid,dp;

    FirebaseAuth firebaseAuth;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);


        actionBar = getSupportActionBar();
        actionBar.setTitle("Add New Book");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        cameraPerssions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagesPerssions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        mRef = FirebaseDatabase.getInstance().getReference("USER");
        Query query = mRef.orderByChild("email").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    nomPrenom = "" + ds.child("nomPrenom").getValue();
                    email = "" + ds.child("email").getValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        titre = (EditText) findViewById(R.id.title);
        auteur = (EditText) findViewById(R.id.auteur);
        specialite = (EditText) findViewById(R.id.specialite);
        origine = (EditText) findViewById(R.id.origine);
        nbCopie = (EditText) findViewById(R.id.nbCopie);
        nomDonneur = (EditText) findViewById(R.id.nomDonneur);
        situation = (EditText) findViewById(R.id.situation);
        cin = (EditText) findViewById(R.id.cin);
        telephoneDonneur = (EditText) findViewById(R.id.telephoneDonneur);
        description = (EditText) findViewById(R.id.description);
        uploadBtn = (Button) findViewById(R.id.uploadBtn);
        imageIv = (ImageView) findViewById(R.id.bImageIv);

        navigation = (BottomNavigationView)findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(selectedListener);


        imageIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickDialog(); 
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleT = titre.getText().toString().trim();
                String auteurT = auteur.getText().toString().trim();
                String specialiteT = specialite.getText().toString().trim();
                String origineT = origine.getText().toString().trim();
                String nbCopieT = nbCopie.getText().toString().trim();
                String nomDonneurT = nomDonneur.getText().toString().trim();
                String situationT = situation.getText().toString().trim();
                String cinT = cin.getText().toString().trim();
                String descriptionT = description.getText().toString().trim();
                String telephonneDonneurT = telephoneDonneur.getText().toString().trim();

                if (TextUtils.isEmpty(titleT)){
                    Toast.makeText(AddBook.this, "Entrer titre...", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(descriptionT)){
                    Toast.makeText(AddBook.this, "Entrer la Description...", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(auteurT)){
                    Toast.makeText(AddBook.this, "Entrer l'auteur...", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(specialiteT)){
                    Toast.makeText(AddBook.this, "Entrer la spécialitée...", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(origineT)){
                    Toast.makeText(AddBook.this, "Entrer l'origine...", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(nbCopieT)){
                    Toast.makeText(AddBook.this, "Entrer le nombre de copie...", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(nomDonneurT)){
                    Toast.makeText(AddBook.this, "Entrer le nom du donneur...", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(situationT)){
                    Toast.makeText(AddBook.this, "Entrer la situation du donneur...", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(cinT)){
                    Toast.makeText(AddBook.this, "Entrer le numéro CIN...", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(telephonneDonneurT)){
                    Toast.makeText(AddBook.this, "Entrer le numéro de téléphone du donneur...", Toast.LENGTH_SHORT).show();
                }

                if (image_uri == null){
                    uploadData(titleT,auteurT,specialiteT,origineT,nbCopieT,nomDonneurT,situationT,cinT,telephonneDonneurT, descriptionT,"noImage");
                }else{
                    uploadData(titleT,auteurT,specialiteT,origineT,nbCopieT,nomDonneurT,situationT,cinT,telephonneDonneurT, descriptionT,String.valueOf(image_uri));
                }
            }
        });

        firebaseAuth= FirebaseAuth.getInstance();
        checkUserStatus();
    }


    private void uploadData(final String titleT, final String auteurT, final String specialiteT, final String origineT, final String nbCopieT, final String nomDonneurT,
                            final String situationT, final String cinT, final String telephonneDonneurT,final String descriptionT,String uri) {

        final String timeStamp = String.valueOf(System.currentTimeMillis());
        String filePathAndName = "Books/" + "book_" + timeStamp;

        if (!uri.equals("noImage")){
            StorageReference ref = FirebaseStorage.getInstance().getReference().child(filePathAndName);
            ref.putFile(Uri.parse(uri)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful());
                    String downloadUri = uriTask.getResult().toString();

                    if (uriTask.isSuccessful()){
                        HashMap<Object,String> hashMap = new HashMap<>();
                        hashMap.put("uid",uid);
                        hashMap.put("nomPrenom",nomPrenom);
                        hashMap.put("email",email);
                        hashMap.put("bId",timeStamp);
                        hashMap.put("bTitre",titleT);
                        hashMap.put("bDescription",descriptionT);
                        hashMap.put("bImage",downloadUri);
                        hashMap.put("bAuteur",auteurT);
                        hashMap.put("bSpecialite",specialiteT);
                        hashMap.put("bOrigine",origineT);
                        hashMap.put("bNbCopie",nbCopieT);
                        hashMap.put("bSituation",situationT);
                        hashMap.put("dNomDonneur",nomDonneurT);
                        hashMap.put("dCIN",cinT);
                        hashMap.put("dTelephone",telephonneDonneurT);

                       // System.out.println("This is my name " + );

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
                        ref.child(timeStamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(AddBook.this, "Book published...", Toast.LENGTH_SHORT).show();

                                titre.setText("");
                                description.setText("");
                                auteur.setText("");
                                imageIv.setImageURI(null);
                                image_uri = null;
                                specialite.setText("");
                                origine.setText("");
                                nbCopie.setText("");
                                nomDonneur.setText("");
                                situation.setText("");
                                cin.setText("");
                                telephoneDonneur.setText("");
                                startActivity(new Intent(AddBook.this,Dashboard.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddBook.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddBook.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            HashMap<Object,String> hashMap = new HashMap<>();
            hashMap.put("uid",uid);
            hashMap.put("nomPrenom",nomPrenom);
            hashMap.put("email",email);
            hashMap.put("bId",timeStamp);
            hashMap.put("bTitre",titleT);
            hashMap.put("bDescription",descriptionT);
            hashMap.put("bImage","noImage");
            hashMap.put("bAuteur",auteurT);
            hashMap.put("bSpecialite",specialiteT);
            hashMap.put("bOrigine",origineT);
            hashMap.put("bNbCopie",nbCopieT);
            hashMap.put("bSituation",situationT);
            hashMap.put("dNomDonneur",nomDonneurT);
            hashMap.put("dCIN",cinT);
            hashMap.put("dTelephone",telephonneDonneurT);

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
            ref.child(timeStamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(AddBook.this, "Book published...", Toast.LENGTH_SHORT).show();

                    titre.setText("");
                    description.setText("");
                    auteur.setText("");
                    imageIv.setImageURI(null);
                    image_uri = null;
                    specialite.setText("");
                    origine.setText("");
                    nbCopie.setText("");
                    nomDonneur.setText("");
                    situation.setText("");
                    cin.setText("");
                    telephoneDonneur.setText("");
                    startActivity(new Intent(AddBook.this,Dashboard.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddBook.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void showImagePickDialog() {
        String[] options = {"Camera","Galery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image from");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    }else{
                        pickFromCamera();
                    }

                }
                if (which ==1){
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }else{
                        pickFromGallery();
                    }
                }
            }
        });
        builder.create().show();
    }

    private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {
        ContentValues cv = new ContentValues();
        cv.put(MediaStore.Images.Media.TITLE,"Temp Pick");
        cv.put(MediaStore.Images.Media.DESCRIPTION,"Temp Descr");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,cv);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(intent,IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this, storagesPerssions,STORAGE_REQUEST_CODE );
    }

    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this, cameraPerssions,CAMERA_REQUEST_CODE );
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkUserStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkUserStatus();
    }

    private void checkUserStatus(){
        FirebaseAuth user = FirebaseAuth.getInstance();
        if (user.getCurrentUser().getUid() !=null){
            email = user.getCurrentUser().getEmail();
            uid = user.getUid();
        }else{
            startActivity(new Intent(AddBook.this,MainActivity.class));
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        menu.findItem(R.id.search).setVisible(false);
        menu.findItem(R.id.addBook).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id== R.id.action_logout){
            firebaseAuth.getInstance().signOut();
            Intent loginIntent = new Intent(AddBook.this,Login.class);
            finish();
            startActivity(loginIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length>0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageAccepted){
                        pickFromCamera();
                    }else {
                        Toast.makeText(this, "Camera & Storage both permission are neccessary...", Toast.LENGTH_SHORT).show();
                    }
                }else{

                }
            }break;
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length>0){
                    boolean storageAccepted =  grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (storageAccepted){
                        pickFromGallery();
                    }else{
                        Toast.makeText(this, "Storage persmissions neccessary...", Toast.LENGTH_SHORT).show();
                    }
                }else{

                }

            }break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK){
            if (requestCode == IMAGE_PICK_GALLERY_CODE){
                image_uri = data.getData();

                imageIv.setImageURI(image_uri);
            }

            else if (requestCode == IMAGE_PICK_CAMERA_CODE){
                imageIv.setImageURI(image_uri);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            Intent intentDash = new Intent(AddBook.this,Dashboard.class);
                            startActivity(intentDash);
                            return true;

                        case R.id.nav_profile:
                            Intent intentProfile = new Intent(AddBook.this,Profile.class);
                            startActivity(intentProfile);
                            return true;

                        case R.id.nav_users:
                            Intent intentGet = new Intent(AddBook.this,GetAllUsers.class);
                            startActivity(intentGet);
                    }
                    return false;
                }
            };
}
