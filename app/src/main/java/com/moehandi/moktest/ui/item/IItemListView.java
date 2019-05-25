package com.moehandi.moktest.ui.item;

import java.util.List;

import com.moehandi.moktest.data.remote.model.Item;
import com.moehandi.moktest.ui.base.MvpView;

/**
 * Created by moehandi on 22/5/19.
 */

public interface IItemListView extends MvpView {

    void onAllItemsSuccess(List<Item> itemList);

    void onAllItemsFailure(String errorMsg);

    boolean isNetworkConnected();
}
