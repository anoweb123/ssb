package com.ali.ssb.Models;

public class modelproducts {
    String name, detail,price,promotionRate,promotionTill,quantity,size,color,categoryType;
    String image;
    public modelproducts(String name, String detail, String price, String promotionRate, String promotionTill, String quantity, String size, String color, String categoryType, String image) {
        this.name = name;
        this.detail = detail;
        this.price = price;
        this.promotionTill = promotionTill;
        this.promotionRate = promotionRate;
        this.quantity = quantity;
        this.size = size;
        this.color = color;
        this.categoryType = categoryType;
        this.image = image;
    }

    public modelproducts() {
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
