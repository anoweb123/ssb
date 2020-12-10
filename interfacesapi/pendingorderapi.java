package com.ali.ssb.interfacesapi;

import com.ali.ssb.Models.modelpending;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface pendingorderapi {
    @GET(".")
    Call<List<modelpending>> list();
}
