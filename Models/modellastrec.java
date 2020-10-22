package com.ali.ssb.Models;

public class modellastrec {
    String price,qty;
    String image;

    public modellastrec(String price, String qty, String image) {
        this.price = price;
        this.qty = qty;
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
