package com.ali.ssb.Models;

public class modelorderitems {
    String image,price,size,color,productName,quantity            ;
    productId productId;

    public modelorderitems(String image, String price, String size, String color, String productName, String quantity, com.ali.ssb.Models.productId productId) {
        this.image = image;
        this.price = price;
        this.size = size;
        this.color = color;
        this.productName = productName;
        this.quantity = quantity;
        this.productId = productId;
    }

    public modelorderitems(String productName) {
        this.productName = productName;
    }

    public modelorderitems() {
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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
