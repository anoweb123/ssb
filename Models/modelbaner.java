package com.ali.ssb.Models;

public class modelbaner {
    shopId shopId;
String _id,image,status;

    public modelbaner(com.ali.ssb.Models.shopId shopId, String _id, String image, String status) {
        this.shopId = shopId;
        this._id = _id;
        this.image = image;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
