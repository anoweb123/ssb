package com.ali.ssb.interfacesapi;

import com.ali.ssb.Models.modelallpro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface allproductsapi {
    @GET("products/allproducts")
    Call<List<modelallpro>> listCall();
}
