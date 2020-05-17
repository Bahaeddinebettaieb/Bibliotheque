package com.example.lenovo.newpj.notifications;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAA77KhuIE:APA91bEBl4n71sdtJF532m5O_mp5NHBMxAI8KcjuT1tkUjNddw4rkdNDqkd3RrvJPWRYBGJcpt4IrfHnkQ0WFMqjGRJP5xRpk5zBgMCMKYXMgRl12JWgpdreIHu1F5IX8UcEtt3o2yA0"
    })

    @POST("fcm/send")
    Call<Response> sendNotification(@Body Sender body);
}
