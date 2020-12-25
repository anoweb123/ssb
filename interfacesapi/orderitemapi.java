package com.ali.ssb.interfacesapi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface orderitemapi {
    @FormUrlEncoded
    @POST(".")
    Call<ResponseBody> response(
            @Field("orderId") String orderid,
            @Field("productName") String name,
            @Field("image") String image,
            @Field("quantity") String quan,
            @Field("discount") String dis,
            @Field("productId") String proid,
            @Field("price") String price
    );
}
