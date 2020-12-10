package com.ali.ssb.Models;

public class modelproductslider {
    int img;
    String title,price,discounted;

    public modelproductslider(int img, String title, String price, String discounted) {
        this.img = img;
        this.title = title;
        this.price = price;
        this.discounted = discounted;
    }

    public modelproductslider() {
    }

    public String getDiscounted() {
        return discounted;
    }

    public void setDiscounted(String discounted) {
        this.discounted = discounted;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
