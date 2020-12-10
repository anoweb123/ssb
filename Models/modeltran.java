package com.ali.ssb.Models;

public class modeltran {
    String price,date,type,name,time;

    public modeltran(String price, String date, String type, String name, String time) {
        this.price = price;
        this.date = date;
        this.type = type;
        this.name = name;
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
