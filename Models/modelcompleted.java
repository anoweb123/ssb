package com.ali.ssb.Models;

public class modelcompleted {
    String status,date,price;

    public modelcompleted(String status, String date, String price) {
        this.status = status;
        this.date = date;
        this.price = price;
    }
    public modelcompleted() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
