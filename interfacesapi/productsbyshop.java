package com.ali.ssb.interfacesapi;

import com.ali.ssb.Models.modelproductbyshop;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface productsbyshop {
    @GET(".")
    Call<List<modelproductbyshop>> list();

}