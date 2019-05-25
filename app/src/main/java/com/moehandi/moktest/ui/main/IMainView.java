package com.moehandi.moktest.ui.main;

import com.moehandi.moktest.ui.base.MvpView;

/**
 * Created by moehandi on 22/5/19.
 */

public interface IMainView extends MvpView {

    void displayLibrary();

    void displayAllItems();

    void displayDiscountList();

    void displayShoppingCart();

    void displayAddToCartDialog(int itemId, String title, double price, int quantity, double discount, boolean editFlag);
}
