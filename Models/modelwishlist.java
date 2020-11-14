package com.ali.ssb.Models;

public class modelwishlist {
    String title;
    String desc;
    String price;
    String discounted;
    String color;
    String id;
    String size;
    String image;

    public modelwishlist(String title, String desc, String price, String discounted, String color, String size, String image, String id) {
        this.title = title;
        this.id = id;
        this.desc = desc;
        this.price = price;
        this.discounted = discounted;
        this.color = color;
        this.size = size;
        this.image = image;
    }

    public modelwishlist() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
