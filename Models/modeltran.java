package com.ali.ssb.Models;

public class modeltran {
    String total,paymentMethod,name,discount,status,paymentStatus,shipping,_id;

    public modeltran(String total, String paymentMethod, String name, String discount, String status, String paymentStatus, String shipping, String _id) {
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.name = name;
        this.discount = discount;
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.shipping = shipping;
        this._id = _id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }
}
