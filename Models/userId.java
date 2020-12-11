package com.ali.ssb.Models;

public class userId {
   String deliveryCharges,shopName,shopCategory,_id;

    public userId(String deliveryCharges, String shopName, String shopCategory, String _id) {
        this.deliveryCharges = deliveryCharges;
        this.shopName = shopName;
        this.shopCategory = shopCategory;
        this._id = _id;
    }

    public String getDeliveryCharges() {
        return deliveryCharges;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setDeliveryCharges(String deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }
}
