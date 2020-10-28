package com.ali.ssb.Models;

public class modelslider
{
   String _id,shopName,image,shopCategory,promotionRate,promotionTill,deliveryCharges;

   public modelslider(String _id, String shopName, String image, String shopCategory, String promotionRate, String promotionTill, String deliveryCharges) {
      this._id = _id;
      this.shopName = shopName;
      this.image = image;
      this.shopCategory = shopCategory;
      this.promotionRate = promotionRate;
      this.promotionTill = promotionTill;
      this.deliveryCharges = deliveryCharges;
   }

   public String getDeliveryCharges() {
      return deliveryCharges;
   }

   public void setDeliveryCharges(String deliveryCharges) {
      this.deliveryCharges = deliveryCharges;
   }

   public String get_id() {
      return _id;
   }

   public void set_id(String _id) {
      this._id = _id;
   }

   public String getShopName() {
      return shopName;
   }

   public void setShopName(String shopName) {
      this.shopName = shopName;
   }

   public String getImage() {
      return image;
   }

   public void setImage(String image) {
      this.image = image;
   }

   public String getShopCategory() {
      return shopCategory;
   }

   public void setShopCategory(String shopCategory) {
      this.shopCategory = shopCategory;
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
}
