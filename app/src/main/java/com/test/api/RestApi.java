package com.test.api;

import com.test.entity.GitData;
import com.test.entity.MyRequestBody;
import com.test.entity.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by m on 22.09.2017.
 */

public interface RestApi {

    @GET("users")
    Call<List<Users>> getUesrs();


    @GET("users/{user}/repos")
    Call<List<GitData>> getData(@Path("user") String user);

    @Headers({
            "Content-Type:application/json"
    })
    @POST("fcm/send")
    Call<MyRequestBody> sendToTopic(
            @Header("Authorization") String token,
            @Body MyRequestBody body);

}
