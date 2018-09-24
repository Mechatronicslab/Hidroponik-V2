package com.example.omgimbot.hidroponik.network;

import java.util.Map;

import com.example.omgimbot.hidroponik.features._common.CommonResponse;
import com.example.omgimbot.hidroponik.features.auth.model.LoginResponse;
import com.example.omgimbot.hidroponik.features.auth.model.SignupResponse;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by github.com/adip28 on 7/17/2018.
 */
public interface NetworkService {

    @FormUrlEncoded
    @POST("users/signup")
    Call<SignupResponse> signup(@FieldMap Map<String, Object> params);

    @POST("/users/signin")
    Call<LoginResponse> login();

    @FormUrlEncoded
    @POST("users/updatetoken")
    Call<CommonResponse> updateToken(@FieldMap Map<String, Object> params);


}
