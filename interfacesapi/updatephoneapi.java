package com.ali.ssb.interfacesapi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PUT;

public interface updatephoneapi {
    @FormUrlEncoded
    @PUT(".")
    Call<ResponseBody> updatephone(@Field("id") String id, @Field("cell") String phone);
}
