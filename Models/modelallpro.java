package com.ali.ssb.Models;

public class modelallpro {
    String name, detail,price,promotionRate,promotionTill,quantity,size,color,categoryType,promotionStatus;
    String image;
    String _id;
    userId userId;

    public modelallpro(String name, String detail, String price, String promotionRate, String promotionTill, String quantity, String size, String color, String categoryType, String promotionStatus, String image, String _id, com.ali.ssb.Models.userId userId) {
        this.name = name;
        this.detail = detail;
        this.price = price;
        this.promotionRate = promotionRate;
        this.promotionTill = promotionTill;
        this.quantity = quantity;
        this.size = size;
        this.color = color;
        this.categoryType = categoryType;
        this.promotionStatus = promotionStatus;
        this.image = image;
        this._id = _id;
        this.userId = userId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public com.ali.ssb.Models.userId getUserId() {
        return userId;
    }

    public void setUserId(com.ali.ssb.Models.userId userId) {
        this.userId = userId;
    }

    public modelallpro() {
    }

    public String getPromotionStatus() {
        return promotionStatus;
    }

    public void setPromotionStatus(String promotionStatus) {
        this.promotionStatus = promotionStatus;
    }

    public String getPromotionRate() {
        return promotionRate;
    }

    public void setPromotionRate(String promotionRate) {
        this.promotionRate = promotionRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
