package com.ali.ssb.Models;

public class modelcategoryinshop  {
    String name,promotionRate,promotionTill,_id,promotionStatus;

    public modelcategoryinshop(String name, String promotionRate, String promotionTill, String _id, String promotionStatus) {
        this.name = name;
        this.promotionRate = promotionRate;
        this.promotionStatus = promotionStatus;
        this.promotionTill = promotionTill;
        this._id = _id;
    }

    public String getPromotionStatus() {
        return promotionStatus;
    }

    public void setPromotionStatus(String promotionStatus) {
        this.promotionStatus = promotionStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPromotionRate() {
        return promotionRate;
    }

    public void setPromotionRate(String promotionRate) {
        this.promotionRate = promotionRate;
    }

    public String getPromotionTill() {
        return promotionTill;
    }

    public void setPromotionTill(String promotionTill) {
        this.promotionTill = promotionTill;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
