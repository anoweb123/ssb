package com.ali.ssb.interfacesapi;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface productsbyshop {
    @GET(".")
    Call<JSONObject> list();

}