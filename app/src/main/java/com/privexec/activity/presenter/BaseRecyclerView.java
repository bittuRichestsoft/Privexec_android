package com.privexec.activity.presenter;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by Mukesh on 23/03/2017.
 */

public interface BaseRecyclerView extends BaseView {

    void showSwipeRefreshLoader();

    void hideSwipeRefreshLoader();

    void showNoDataText(int resId, String message);

    void hideNoDataText();

    RecyclerView.Adapter getRecyclerViewAdapter();

    void updateRecyclerViewData(ArrayList<?> dataList);

}
