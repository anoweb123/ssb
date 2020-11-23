package com.ali.ssb.interfacesapi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface notificationcount {
    @GET()
    Call<String> list(@Url String url);
}
