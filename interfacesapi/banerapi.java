package com.ali.ssb.interfacesapi;

import com.ali.ssb.Models.modelbaner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface banerapi {
    @GET("adminlogin/baner")
    Call<List<modelbaner>> listCall();

}
