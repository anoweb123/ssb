package com.ali.ssb.Models;

public class modelcateg {
    String name;
    int iamge;


    public modelcateg(String name, int iamge) {
        this.name = name;
        this.iamge = iamge;
    }

    public int getIamge() {
        return iamge;
    }

    public void setIamge(int iamge) {
        this.iamge = iamge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
