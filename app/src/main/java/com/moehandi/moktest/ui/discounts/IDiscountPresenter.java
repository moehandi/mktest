package com.moehandi.moktest.ui.discounts;

import com.moehandi.moktest.ui.base.MvpPresenter;

/**
 * Created by moehandi on 22/5/19.
 */

public interface IDiscountPresenter<V extends IDiscountView> extends MvpPresenter<V> {

    void getDiscounts();
}
