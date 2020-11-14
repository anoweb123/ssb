package com.ali.ssb.interfacesapi;

import com.ali.ssb.Models.modelcompleted;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface completedorderapi {
    @GET(".")
    Call<List<modelcompleted>> list();
}
