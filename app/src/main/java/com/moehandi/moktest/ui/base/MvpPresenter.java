package com.moehandi.moktest.ui.base;

/**
 * Created by moehandi on 22/5/19.
 */

public interface MvpPresenter<V extends MvpView> {

    void attachView(V view);

    void detachView();

    V getMvpView();

    boolean isViewAttached();

    void checkViewAttached() throws BasePresenter.MvpViewNotAttachedException;
}
