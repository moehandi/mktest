package com.moehandi.moktest.ui.item;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import com.moehandi.moktest.data.local.ItemsManager;
import com.moehandi.moktest.data.remote.MokaApiService;
import com.moehandi.moktest.data.remote.RestServiceFactory;
import com.moehandi.moktest.data.remote.model.Item;
import com.moehandi.moktest.ui.base.BasePresenter;

/**
 * Created by moehandi on 22/5/19.
 */

public class ItemListPresenter<V extends IItemListView> extends BasePresenter<V> implements IItemListPresenter<V> {

    private final RestServiceFactory mRestServiceFactory;
    private final ItemsManager mItemsManager;
    private final CompositeDisposable mCompositeDisposable;

    public ItemListPresenter(RestServiceFactory mRestServiceFactory, ItemsManager itemsManager) {
        this.mRestServiceFactory = mRestServiceFactory;
        this.mItemsManager = itemsManager;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(V view) {
        super.attachView(view);
        getAllItems();
    }

    @Override
    public void getAllItems() {
        FetchAllItems();

        if (!getMvpView().isNetworkConnected()) {
            getMvpView().showToast("No network connection");
            return;
        }

        MokaApiService apiService = mRestServiceFactory.create(MokaApiService.class);

        apiService.getAllItems()
                .map(new Function<List<Item>, List<Item>>() {
                    @Override
                    public List<Item> apply(List<Item> items) throws Exception {
                        mItemsManager.insertItems(items);
                        return mItemsManager.getItems();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Item>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Item> items) {
                        getMvpView().onAllItemsSuccess(items);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().onAllItemsFailure(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    public void FetchAllItems() {
        Observable.just(mItemsManager.getItems())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Item>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Item> items) {
                        getMvpView().onAllItemsSuccess(items);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().onAllItemsFailure(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void detachView() {
        super.detachView();
        mCompositeDisposable.clear();
    }
}
