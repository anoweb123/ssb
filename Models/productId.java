package com.ali.ssb.Models;

public class productId {
    String size,color,price,promotionRate,_id,userId,quantity,promotionStatus,promotionTill,detail;

    public productId(String size, String color, String price,String detail, String promotionRate, String _id, String userId, String quantity, String promotionStatus, String promotionTill) {
        this.size = size;
        this.color = color;
        this.price = price;
        this.promotionRate = promotionRate;
        this._id = _id;
        this.userId = userId;
        this.detail= detail;
        this.quantity = quantity;
        this.promotionStatus = promotionStatus;
        this.promotionTill = promotionTill;
    }

    public productId() {
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPromotionStatus() {
        return promotionStatus;
    }

    public void setPromotionStatus(String promotionStatus) {
        this.promotionStatus = promotionStatus;
    }

    public String getPromotionTill() {
        return promotionTill;
    }

    public void setPromotionTill(String promotionTill) {
        this.promotionTill = promotionTill;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPromotionRate() {
        return promotionRate;
    }

    public void setPromotionRate(String promotionRate) {
        this.promotionRate = promotionRate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
