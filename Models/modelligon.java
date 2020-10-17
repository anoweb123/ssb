package com.ali.ssb.Models;

public class modelligon {
    String _id,email,password,name;

    public modelligon(String _id, String email, String password, String name) {
        this._id = _id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
