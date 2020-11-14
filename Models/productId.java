package com.ali.ssb.Models;

public class productId {
    String size,color,price;

    public productId(String size, String color, String price) {
        this.size = size;
        this.color = color;
        this.price = price;
    }
    public productId() {
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
