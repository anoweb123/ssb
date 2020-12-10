package com.ali.ssb.interfacesapi;

import android.widget.Toast;

import com.ali.ssb.Models.modelgetresultofimageupdate;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PUT;

public interface imageupdateapi {
    @FormUrlEncoded
    @PUT(".")
    Call<modelgetresultofimageupdate> updateimg(@Field("id") String id, @Field("image") String image, @Field("blob") String blob);

}
