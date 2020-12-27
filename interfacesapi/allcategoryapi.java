package com.ali.ssb.interfacesapi;

import com.ali.ssb.Models.modelcategoryinshop;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface allcategoryapi {

    @GET("/categories/allCategory")
    Call<List<modelcategoryinshop>> list();

}
