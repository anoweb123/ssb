package com.ali.ssb.Models;

public class modelsinglepro {
    String promotionRate,promotionStatus,promotionTill,brandName,_idlocal,name,detail,image,price,size,color,quantity,_id;

    public modelsinglepro(String promotionRate, String promotionStatus, String promotionTill, String brandName, String name, String detail, String image, String price, String size, String color, String quantity, String _id) {
        this.promotionRate = promotionRate;
        this.promotionStatus = promotionStatus;
        this.promotionTill = promotionTill;
        this.brandName = brandName;
        this._idlocal = _idlocal;
        this.name = name;
        this.detail = detail;
        this.image = image;
        this.price = price;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
        this._id = _id;
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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String get_idlocal() {
        return _idlocal;
    }

    public void set_idlocal(String _idlocal) {
        this._idlocal = _idlocal;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
