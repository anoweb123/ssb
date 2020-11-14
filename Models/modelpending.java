package com.ali.ssb.Models;

public class modelpending {
    String status,price,grandTotal,orderTime,_id;

    public modelpending(String status, String price, String grandTotal, String orderTime, String _id) {
        this.status = status;
        this.price = price;
        this.grandTotal = grandTotal;
        this.orderTime = orderTime;
        this._id = _id;
    }

    public modelpending() {
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
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
}
