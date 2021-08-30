package com.privexec.activity.presenter;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by Mukesh on 12/09/2016.
 */
public abstract class BasePresenter<V> {

    public CompositeDisposable mCompositeDisposable;

    private WeakReference<V> view;

    public void attachView(V view) {
        this.view = new WeakReference<>(view);
        mCompositeDisposable = new CompositeDisposable();
    }

    protected V getView() {
        return null != view ? view.get() : null;
    }

    public void detachView() {
        view = null;
        if (null != mCompositeDisposable) {
            mCompositeDisposable.clear();
        }
    }


}
