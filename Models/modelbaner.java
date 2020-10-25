package com.ali.ssb.Models;

public class modelbaner {
    shopId shopId;
String _id,image;

    public modelbaner(shopId modeluserId, String _id, String image) {
        this.shopId = shopId;
        this._id = _id;
        this.image = image;
    }
    public shopId getShopId() {
        return shopId;
    }

    public void setShopId(shopId shopId) {
        this.shopId = shopId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
