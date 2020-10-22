package com.ali.ssb.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class userId {
    @SerializedName("_id")
    @Expose
    String _id;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
