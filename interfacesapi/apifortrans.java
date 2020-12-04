package com.ali.ssb.interfacesapi;

import com.ali.ssb.Models.modelcompleted;
import com.ali.ssb.Models.modeltran;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface apifortrans {
    @GET(".")
    Call<List<modeltran>> list();
}
