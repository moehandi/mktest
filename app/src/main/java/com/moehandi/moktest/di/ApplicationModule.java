package com.moehandi.moktest.di;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import com.moehandi.moktest.data.local.DbManager;
import com.moehandi.moktest.data.local.ItemsManager;
import com.moehandi.moktest.data.local.ShoppingCartManager;
import com.moehandi.moktest.data.remote.RestServiceFactory;
import com.moehandi.moktest.ui.addtocart.AddToCartDialogPresenter;
import com.moehandi.moktest.ui.addtocart.IAddToCartPresenter;
import com.moehandi.moktest.ui.discounts.DiscountPresenter;
import com.moehandi.moktest.ui.discounts.IDiscountPresenter;
import com.moehandi.moktest.ui.item.IItemListPresenter;
import com.moehandi.moktest.ui.item.ItemListPresenter;
import com.moehandi.moktest.ui.main.IMainPresenter;
import com.moehandi.moktest.ui.main.MainPresenter;
import com.moehandi.moktest.ui.shoppingcart.IShoppingCartPresenter;
import com.moehandi.moktest.ui.shoppingcart.ShoppingCartPresenter;

/**
 * Created by moehandi on 22/5/19.
 */

@Module
public class ApplicationModule {

    private Application mApp;

    public ApplicationModule(Application mApp) {
        this.mApp = mApp;
    }

    @Singleton
    @Provides
    RestServiceFactory providesRestServiceFactory() {
        return new RestServiceFactory.Impl();
    }

    @Provides
    SQLiteDatabase providesSqLiteDatabase() {
        return DbManager.getInstance(mApp).getDbHelper();
    }

    @Provides
    IMainPresenter providesMainPresenter() {
        return new MainPresenter();
    }

    @Provides
    IItemListPresenter providesItemListPresenter(RestServiceFactory restServiceFactory, ItemsManager itemsManager) {
        return new ItemListPresenter(restServiceFactory, itemsManager);
    }

    @Provides
    IDiscountPresenter providesDiscountPresenter() {
        return new DiscountPresenter();
    }

    @Provides
    IAddToCartPresenter providesAddToCartPresenter(ShoppingCartManager shoppingCartManager) {
        return new AddToCartDialogPresenter(shoppingCartManager);
    }

    @Singleton
    @Provides
    ItemsManager providesItemManager(SQLiteDatabase sqLiteDatabase) {
        return new ItemsManager(sqLiteDatabase);
    }

    @Singleton
    @Provides
    ShoppingCartManager providesShoppingCartManager(SQLiteDatabase sqLiteDatabase) {
        return new ShoppingCartManager(sqLiteDatabase);
    }

    @Provides
    IShoppingCartPresenter providesShoppingCartPresenter(ShoppingCartManager shoppingCartManager) {
        return new ShoppingCartPresenter(shoppingCartManager);
    }

}
