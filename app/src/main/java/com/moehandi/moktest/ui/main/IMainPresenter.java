package com.moehandi.moktest.ui.main;

import com.moehandi.moktest.ui.base.MvpPresenter;

/**
 * Created by moehandi on 22/5/19.
 */

public interface IMainPresenter<V extends IMainView> extends MvpPresenter<V> {

    void onClickDiscounts();

    void onClickAllItems();

    void onClickToolbarBack();
}
