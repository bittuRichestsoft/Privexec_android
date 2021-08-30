package com.privexec.activity.presenter;

import android.content.Context;


public interface BaseView {



    Context getActivityContext();

    boolean isFragmentDestroyed();

    void showProgressLoader();

    void hideProgressLoader();



}
