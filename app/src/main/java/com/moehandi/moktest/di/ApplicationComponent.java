package com.moehandi.moktest.di;

import javax.inject.Singleton;

import dagger.Component;
import com.moehandi.moktest.ui.addtocart.AddToCartDialogFragment;
import com.moehandi.moktest.ui.discounts.DiscountListFragment;
import com.moehandi.moktest.ui.main.MainActivity;
import com.moehandi.moktest.ui.item.ItemListFragment;
import com.moehandi.moktest.ui.shoppingcart.ShoppingCartFragment;

/**
 * Created by moehandi on 22/5/19.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MainActivity activity);

    void inject(ItemListFragment fragment);

    void inject(DiscountListFragment fragment);

    void inject(ShoppingCartFragment fragment);

    void inject(AddToCartDialogFragment dialogFragment);
}
