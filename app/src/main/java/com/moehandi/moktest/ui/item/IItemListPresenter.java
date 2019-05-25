package com.moehandi.moktest.ui.item;

import com.moehandi.moktest.ui.base.MvpPresenter;

/**
 * Created by moehandi on 22/5/19.
 */

public interface IItemListPresenter<V extends IItemListView> extends MvpPresenter<V> {

    void getAllItems();
}
