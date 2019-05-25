package com.moehandi.moktest.ui.shoppingcart;

import com.moehandi.moktest.data.local.model.CartItem;
import com.moehandi.moktest.ui.base.MvpPresenter;

/**
 * Created by moehandi on 23/5/19.
 */

public interface IShoppingCartPresenter<V extends IShoppingCartView> extends MvpPresenter<V> {

    void getCartItems();

    CartItem getInitialTotalItem(double initialTotalAmount);

    CartItem getDiscountTotalItem(double discountTotalAmount);

    void onClickClearButton();
}
