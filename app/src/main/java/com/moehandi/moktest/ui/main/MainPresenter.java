package com.moehandi.moktest.ui.main;

import com.moehandi.moktest.ui.base.BasePresenter;

/**
 * Created by moehandi on 22/5/19.
 */

public class MainPresenter<V extends IMainView> extends BasePresenter<V> implements IMainPresenter<V> {

    @Override
    public void attachView(V view) {
        super.attachView(view);

        getMvpView().displayLibrary();
        getMvpView().displayShoppingCart();
    }

    @Override
    public void onClickDiscounts() {
        getMvpView().displayDiscountList();
    }

    @Override
    public void onClickAllItems() {
        getMvpView().displayAllItems();
    }

    @Override
    public void onClickToolbarBack() {
        getMvpView().displayLibrary();
    }

    @Override
    public void detachView() {
        super.detachView();
    }
}
