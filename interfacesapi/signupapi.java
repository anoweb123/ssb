package com.ali.ssb.interfacesapi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface signupapi {
    @FormUrlEncoded
    @POST("customer/signup")
    Call<ResponseBody> responsesignup(
            @Field("email") String email,
            @Field("password") String password,
            @Field("name") String name,
            @Field("address") String address,
            @Field("cell") String cell
            );
}
