package com.moehandi.moktest.ui.shoppingcart;

import java.util.ArrayList;

import com.moehandi.moktest.data.local.model.CartItem;
import com.moehandi.moktest.ui.base.MvpView;

/**
 * Created by moehandi on 23/5/19.
 */

public interface IShoppingCartView extends MvpView {

    void displayCartItems(ArrayList<CartItem> items);

    /**
     * Total amount after applying discount
     *
     * @param total
     */
    void displayFinalTotalAmount(double total);


}
