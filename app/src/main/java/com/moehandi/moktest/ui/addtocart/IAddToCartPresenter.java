package com.moehandi.moktest.ui.addtocart;

import com.moehandi.moktest.ui.base.MvpPresenter;

/**
 * Created by moehandi on 22/5/19.
 */

public interface IAddToCartPresenter<V extends IAddToCartView> extends MvpPresenter<V> {

    void onClickIncrement();

    void onClickDecrement();

    void onclickSave();

    void onClickCancel();

    void onQuantityEdited(String quantity);
}
