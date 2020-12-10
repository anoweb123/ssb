package com.ali.ssb.interfacesapi;

import com.ali.ssb.Models.modelorderitems;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface orderitemsapi {
    @GET(".")
    Call<List<modelorderitems>> list();
}
