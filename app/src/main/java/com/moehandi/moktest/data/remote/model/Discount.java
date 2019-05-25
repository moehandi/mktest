package com.moehandi.moktest.data.remote.model;

/**
 * Created by moehandi on 22/5/19.
 */

public class Discount {

    private int id;

    private String title;

    private double discount;

    public Discount(int id, String title, double discount) {
        this.id = id;
        this.title = title;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
