package com.ali.ssb.interfacesapi;

import com.ali.ssb.Models.getdatabyloginmodel;
import com.ali.ssb.Models.modelreturnoforderinfo;

import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface orderinfoapi {
    @FormUrlEncoded
    @POST(".")
    Call<modelreturnoforderinfo> response(
            @Field("name") String name,
            @Field("customerId") String id,
            @Field("address") String address,
            @Field("cell") String cell,
            @Field("total") String total,
            @Field("discount") String discount,
            @Field("shipping") String shipping,
            @Field("totalbill") String grandtotal,
            @Field("shopId") String shopid,
            @Field("tax") String tax,
            @Field("deliveryCharges") int deliveryCharges,
            @Field("longnitude") String longnitude,
            @Field("latitude") String latitude,
            @Field("paymentMethod") String paymentmethod,
            @Field("paymentStatus") String paymentStatus
    );
}
