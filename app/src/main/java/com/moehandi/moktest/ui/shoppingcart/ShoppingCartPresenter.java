package com.moehandi.moktest.ui.shoppingcart;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import com.moehandi.moktest.data.local.ShoppingCartManager;
import com.moehandi.moktest.data.local.model.CartItem;
import com.moehandi.moktest.ui.base.BasePresenter;

/**
 * Created by moehandi on 23/5/19.
 */

public class ShoppingCartPresenter<V extends IShoppingCartView> extends BasePresenter<V> implements IShoppingCartPresenter<V> {

    private double mIntitialTotal = 0;
    private double mDiscountTotal = 0;

    private final ShoppingCartManager mShoppingCartManager;
    private final CompositeDisposable mCompositeDisposable;

    public ShoppingCartPresenter(ShoppingCartManager mShoppingCartManager) {
        this.mShoppingCartManager = mShoppingCartManager;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(V view) {
        super.attachView(view);
        getCartItems();
    }

    @Override
    public void getCartItems() {

        Observable<ArrayList<CartItem>> run1 = Observable.fromCallable(new Callable<ArrayList<CartItem>>() {
            @Override
            public ArrayList<CartItem> call() throws Exception {
                return mShoppingCartManager.getItemsFromCart();
            }
        });

        Observable<Double> run2 = Observable.just(mShoppingCartManager.getInitialTotal());

        Observable<Double> run3 = Observable.just(mShoppingCartManager.getDiscountTotal());


        Observable.zip(run1, run2, run3, new Function3<ArrayList<CartItem>, Double, Double, ArrayList<CartItem>>() {
            @Override
            public ArrayList<CartItem> apply(ArrayList<CartItem> items, Double initialTotal, Double discountTotal) throws Exception {
                mIntitialTotal = initialTotal;
                mDiscountTotal = discountTotal;
                if (items.size() > 0) {
                    items.add(getInitialTotalItem(mIntitialTotal));
                    items.add(getDiscountTotalItem(mDiscountTotal));
                }
                return items;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<CartItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(ArrayList<CartItem> items) {
                        getMvpView().displayCartItems(items);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        double total = mIntitialTotal - mDiscountTotal;
                        getMvpView().displayFinalTotalAmount(total);
                    }
                });
    }

    @Override
    public CartItem getInitialTotalItem(double initialTotalAmount) {

        CartItem initialTotal = new CartItem();
        initialTotal.setItemId(-1);
        initialTotal.setItemTitle("Subtotal");
        initialTotal.setTotalPrice(initialTotalAmount);
        return initialTotal;
    }

    @Override
    public CartItem getDiscountTotalItem(double discountTotalAmount) {
        CartItem discountTotal = new CartItem();
        discountTotal.setItemId(-1);
        discountTotal.setItemTitle("Discount");
        discountTotal.setTotalPrice(discountTotalAmount);
        return discountTotal;
    }

    @Override
    public void onClickClearButton() {
        Observable.just(mShoppingCartManager.clearShoppingCart())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        mIntitialTotal = 0;
                        mDiscountTotal = 0;
                        getMvpView().displayCartItems(new ArrayList<CartItem>());
                        getMvpView().displayFinalTotalAmount(0);
                    }
                });

    }

    @Override
    public void detachView() {
        super.detachView();
        mCompositeDisposable.clear();
    }
}
