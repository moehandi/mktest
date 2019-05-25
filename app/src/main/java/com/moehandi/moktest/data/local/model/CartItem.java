package com.moehandi.moktest.data.local.model;

/**
 * Created by moehandi on 23/5/19.
 */

public class CartItem {

    private int itemId;
    private String itemTitle;
    private String thumbnailUrl;
    private int quantity;
    private double totalPrice;
    private double discount;
    private double discountRate;

    public CartItem() {
    }

    public CartItem(int itemId, int quantity, double discount) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.discount = discount;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }
}
