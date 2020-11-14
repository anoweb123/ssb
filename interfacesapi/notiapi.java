package com.ali.ssb.interfacesapi;

import com.ali.ssb.Models.modelnotification;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface notiapi {
    @GET("notifications/shows")
    Call<List<modelnotification>> list();
}
