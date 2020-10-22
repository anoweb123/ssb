package com.ali.ssb.Models;

public class modelforsendorderitem {
    String productName,
            image,
            quantity,
            productId;

    public modelforsendorderitem(String productName, String image, String quantity, String productId) {
        this.productName = productName;
        this.image = image;
        this.quantity = quantity;
        this.productId = productId;
    }

    public modelforsendorderitem() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
