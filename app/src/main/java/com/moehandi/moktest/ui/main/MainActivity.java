package com.moehandi.moktest.ui.main;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.moehandi.moktest.R;
import com.moehandi.moktest.di.ApplicationComponent;
import com.moehandi.moktest.data.local.model.CartItem;
import com.moehandi.moktest.data.remote.model.Item;
import com.moehandi.moktest.ui.addtocart.AddToCartDialogFragment;
import com.moehandi.moktest.ui.base.BaseActivity;
import com.moehandi.moktest.ui.discounts.DiscountListFragment;
import com.moehandi.moktest.ui.item.ItemListFragment;
import com.moehandi.moktest.ui.library.LibraryFragment;
import com.moehandi.moktest.ui.shoppingcart.ShoppingCartFragment;

public class MainActivity extends BaseActivity implements IMainView, ItemListFragment.OnAllItemClickListener,
        LibraryFragment.OnLibraryInteractionListener, ShoppingCartFragment.OnCartItemInteractionListener,
        AddToCartDialogFragment.OnItemAddedToCartListener {

    @BindView(R.id.img_back)
    ImageView imgBack;

    @BindView(R.id.tv_lib_title)
    TextView tvLibTitle;

    @Inject
    IMainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mMainPresenter.attachView(this);
    }

    @Override
    protected void injectComponent(ApplicationComponent component) {
        component.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }

    @OnClick(R.id.img_back)
    public void onClickTooBarBack(View view) {
        mMainPresenter.onClickToolbarBack();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////                  METHODS FROM @IMainView INTERFACE                 //////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void displayLibrary() {
        imgBack.setVisibility(View.GONE);
        tvLibTitle.setText(getString(R.string.text_title_library));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_one, LibraryFragment.newInstance(), LibraryFragment.class.getName());
        fragmentTransaction.commit();
    }

    @Override
    public void displayAllItems() {
        imgBack.setVisibility(View.VISIBLE);
        tvLibTitle.setText(getString(R.string.text_title_all_items));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_one, ItemListFragment.newInstance(), ItemListFragment.class.getName());
        fragmentTransaction.commit();
    }

    @Override
    public void displayDiscountList() {
        imgBack.setVisibility(View.VISIBLE);
        tvLibTitle.setText(getString(R.string.text_title_discounts));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_one, DiscountListFragment.newInstance(), DiscountListFragment.class.getName());
        fragmentTransaction.commit();
    }

    @Override
    public void displayShoppingCart() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_two, ShoppingCartFragment.newInstance(), ShoppingCartFragment.class.getName());
        fragmentTransaction.commit();
    }

    @Override
    public void displayAddToCartDialog(int itemId, String title, double price, int quantity, double discount, boolean editFlag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment prevFragment = fragmentManager.findFragmentByTag(AddToCartDialogFragment.class.getName());
        if (prevFragment != null) {
            fragmentTransaction.remove(prevFragment);
        }
        fragmentTransaction.addToBackStack(null);
        AddToCartDialogFragment.newInstance(itemId, title, price, quantity, discount, editFlag).show(fragmentManager, AddToCartDialogFragment.class.getName());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////     METHODS FROM @ItemListFragment#OnAllItemClickListener INTERFACE        //////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onListFragmentInteraction(Item item) {
        displayAddToCartDialog(item.getId(), item.getTitle(), item.getPrice(), 1, 0, false);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /////////    METHODS FROM @LibraryFragment#OnLibraryInteractionListener INTERFACE      /////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLibraryFragmentInteraction(int clickedItem) {
        switch (clickedItem) {
            case LibraryFragment.ON_CLICK_DISCOUNTS:
                mMainPresenter.onClickDiscounts();
                break;
            case LibraryFragment.ON_CLICK_ALL_ITEMS:
                mMainPresenter.onClickAllItems();
                break;

        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ///////    METHODS FROM @ShoppingCartFragment#OnCartItemInteractionListener INTERFACE     //////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void editCartItem(CartItem item) {
        displayAddToCartDialog(item.getItemId(), item.getItemTitle(), item.getTotalPrice() / item.getQuantity()
                , item.getQuantity(), item.getDiscount(), true);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ///////    METHODS FROM @AddToCartDialogFragment#OnItemAddedToCartListener INTERFACE      //////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onItemAdded() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ShoppingCartFragment cartFragment = (ShoppingCartFragment) fragmentManager.findFragmentByTag(ShoppingCartFragment.class.getName());
        if (cartFragment != null) {
            cartFragment.refreshCartList();
        }

    }
}
