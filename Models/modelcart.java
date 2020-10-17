package com.ali.ssb.Models;

public class modelcart {
    String title,price,discounted,size,color,desc,quantity,image;
    String proid;
    int id,leftstock;

    public modelcart(String proid,String title, String price, String quantity, String discounted, String size, String color, String desc, String image, int id,int leftstock) {
        this.title = title;
        this.price = price;
        this.discounted = discounted;
        this.size = size;
        this.color = color;
        this.desc = desc;
        this.image = image;
        this.quantity = quantity;
        this.id = id;
        this.proid = proid;
        this.leftstock = leftstock;
    }

    public modelcart() {
    }

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public int getLeftstock() {
        return leftstock;
    }

    public void setLeftstock(int leftstock) {
        this.leftstock = leftstock;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

    public String getDiscounted() {
        return discounted;
    }

    public void setDiscounted(String discounted) {
        this.discounted = discounted;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
