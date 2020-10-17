package com.ali.ssb.Models;

public class modelwishlist {
    String title;
    String desc;
    String price;
    String discounted;
    String color;
    int id;
    String size;
    int image;

    public modelwishlist(String title, String desc, String price, String discounted, String color, String size, int image, int id) {
        this.title = title;
        this.id = id;
        this.desc = desc;
        this.price = price;
        this.discounted = discounted;
        this.color = color;
        this.size = size;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscounted() {
        return discounted;
    }

    public void setDiscounted(String discounted) {
        this.discounted = discounted;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
