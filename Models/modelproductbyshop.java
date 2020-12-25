package com.ali.ssb.Models;

public class modelproductbyshop {
    String name,detail,price,image,promotionRate,promotionTill,categorytype,size,color,quantity,type,_id;


    public modelproductbyshop(String _id, String name, String detail, String price, String promotionRate, String image, String categorytype, String size, String color, String days, String quantity, String type) {
        this.name = name;
        this.detail = detail;
        this.price = price;
        this._id = _id;
        this.image = image;
        this.categorytype = categorytype;
        this.size = size;
        this.promotionRate = promotionRate;
        this.promotionTill = promotionTill;
        this.color = color;
        this.quantity = quantity;
        this.type = type;
    }

    public modelproductbyshop() {
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

    public String getPromotionTill() {
        return promotionTill;
    }

    public void setPromotionTill(String promotionTill) {
        this.promotionTill = promotionTill;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategorytype() {
        return categorytype;
    }

    public void setCategorytype(String categorytype) {
        this.categorytype = categorytype;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public static class userId{

        String _id;

        public userId(String _id) {
            this._id = _id;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }
    }

}
