package com.ali.ssb.interfacesapi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PUT;

public interface forgetpassapi {
    @FormUrlEncoded
    @PUT("customer/forgetPassword")
    Call<ResponseBody> forgetpassword(@Field("email") String email);

}
