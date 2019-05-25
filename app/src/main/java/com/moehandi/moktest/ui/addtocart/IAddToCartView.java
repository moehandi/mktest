package com.moehandi.moktest.ui.addtocart;

import com.moehandi.moktest.ui.base.MvpView;

/**
 * Created by moehandi on 22/5/19.
 */

public interface IAddToCartView extends MvpView {

    int getItemId();

    String getItemTitle();

    double getItemPrice();

    int getItemQuantity();

    double getItemDiscount();

    boolean isEdit();

    double getChosenDiscount();

    void setTitle(String title);

    void setQuantity(int quantity);

    void addedToCart();

}
