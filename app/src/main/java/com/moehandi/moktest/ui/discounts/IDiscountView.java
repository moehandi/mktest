package com.moehandi.moktest.ui.discounts;

import java.util.ArrayList;

import com.moehandi.moktest.data.remote.model.Discount;
import com.moehandi.moktest.ui.base.MvpView;

/**
 * Created by moehandi on 22/5/19.
 */

public interface IDiscountView extends MvpView {

    void displayDiscounts(ArrayList<Discount> discountList);
}
