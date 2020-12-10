package com.ali.ssb.Models;

public class modelorderitems {
    String image,price,size,color,productName;
    productId productId;

    public modelorderitems(String image, String size, String color, String productName,  productId productId) {
        this.image = image;
        this.size = size;
        this.color = color;
        this.productName = productName;
        this.productId = productId;
    }

    public modelorderitems(String productName) {
        this.productName = productName;
    }

    public modelorderitems() {
    }

    public productId getProductId() {
        return productId;
    }

    public void setProductId(productId productId) {
        this.productId = productId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


}
