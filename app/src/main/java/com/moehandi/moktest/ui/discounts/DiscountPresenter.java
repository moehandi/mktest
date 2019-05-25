package com.moehandi.moktest.ui.discounts;

import java.util.ArrayList;

import com.moehandi.moktest.data.remote.model.Discount;
import com.moehandi.moktest.ui.base.BasePresenter;

/**
 * Created by moehandi on 22/5/19.
 */

public class DiscountPresenter<V extends IDiscountView> extends BasePresenter<V> implements IDiscountPresenter<V> {

    @Override
    public void attachView(V view) {
        super.attachView(view);
        getDiscounts();
    }

    @Override
    public void getDiscounts() {
        ArrayList<Discount> list = new ArrayList<>();
        list.add(new Discount(1, "Discount A", 0));
        list.add(new Discount(2, "Discount B", 10));
        list.add(new Discount(3, "Discount C", 35.5));
        list.add(new Discount(4, "Discount D", 50));
        list.add(new Discount(5, "Discount E", 100));
        getMvpView().displayDiscounts(list);
    }

    @Override
    public void detachView() {
        super.detachView();
    }
}
