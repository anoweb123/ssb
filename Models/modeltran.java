package com.ali.ssb.Models;

public class modeltran {
    String status,orderTime,deliveryTime,grandTotal,paymentMethod,paymentStatus,name,_id,address;

    public modeltran(String status, String orderTime, String deliveryTime, String grandTotal, String paymentMethod, String paymentStatus, String name, String _id, String address) {
        this.status = status;
        this.orderTime = orderTime;
        this.deliveryTime = deliveryTime;
        this.grandTotal = grandTotal;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.name = name;
        this._id = _id;
        this.address = address;
    }

    public modeltran() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
