package com.ali.ssb.interfacesapi;

import com.ali.ssb.Models.modelsinglepro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface singleforwishapi {
    @GET(".")
    Call<modelsinglepro> list();
}
