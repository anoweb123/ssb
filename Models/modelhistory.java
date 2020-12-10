package com.ali.ssb.Models;

public class modelhistory {
    String status,price,date;

    public modelhistory(String status, String price, String date) {
        this.status = status;
        this.price = price;
        this.date = date;
    }

    public modelhistory() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
