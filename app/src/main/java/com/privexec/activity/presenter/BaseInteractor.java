package com.privexec.activity.presenter;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by Mukesh on 17/04/2017.
 */

public class BaseInteractor<T> {

    public Disposable getDisposable(Observable<Response<T>> observable,
                                    final NetworkRequestCallbacks networkRequestCallbacks) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<?>>() {
                    @Override
                    public void onNext(Response<?> response) {
                        networkRequestCallbacks.onSuccess(response);
                    }
                    @Override
                    public void onError(Throwable t) {
                        networkRequestCallbacks.onError(t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
/*

open class BaseInteractor<T> {

        fun getDisposable(observable: Observable<Response<T>>,
        networkRequestCallbacks: NetworkRequestCallbacks): Disposable {
        return observable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith<>(object : DisposableObserver<Response<*>>() {
        override fun onNext(response: Response<*>) {
        networkRequestCallbacks.onSuccess(response)
        }

        override fun onError(t: Throwable) {
        networkRequestCallbacks.onError(t)
        }

        override fun onComplete() {

        }
        })
        }
        }
        */