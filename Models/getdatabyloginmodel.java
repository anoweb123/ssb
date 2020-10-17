package com.ali.ssb.Models;

public class getdatabyloginmodel {
    String _id,cell,address,email,name,password;

    public getdatabyloginmodel(String _id, String cell, String address, String email, String name, String password) {
        this._id = _id;
        this.cell = cell;
        this.address = address;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public getdatabyloginmodel() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
