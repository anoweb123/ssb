package com.ali.ssb.interfacesapi;

import com.ali.ssb.Models.modelsinglepro;

import retrofit2.Call;
import retrofit2.http.GET;

public interface singleforwishapi {
    @GET(".")
    Call<modelsinglepro> list();
}
