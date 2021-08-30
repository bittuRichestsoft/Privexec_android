package com.privexec.paginate;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lee_da_hang_pte_ltd.utills.CSPreferences;
import com.privexec.R;
import com.privexec.fragmnet.Myapp_deatal;
import com.privexec.otherutility.Global_Class;
import com.privexec.pojoclass.App_dataPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
/*
import butterknife.BindView;
import butterknife.ButterKnife;*/

public class MyAppAdapPaginate  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private boolean isLoadingAdded = false;
    private List<App_dataPojo.Result> mPostItems;
    private  Context ctx;
    String TAG="MyAppAdapPaginate ";


    public MyAppAdapPaginate(/*List<App_dataPojo.Result> postItems ,*/ Context ctx) {
         this.mPostItems =  new ArrayList<>();
        this.ctx=ctx;
    }

    public List<App_dataPojo.Result> getApss() {
        return mPostItems;
    }

    public void setApps(List<App_dataPojo.Result> appsResult) {
        this.mPostItems = appsResult;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);


                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_loading, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_myapp_row, parent, false);

        viewHolder = new MovieVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        App_dataPojo.Result result = mPostItems.get(position); // Movie

        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH movieVH = (MovieVH) holder;

                movieVH.tv_appName.setText(result.getTitle());
                Glide.with(ctx).load(result.getImage()+"").into(movieVH.iv_appImg );
                movieVH.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Global_Class.Companion.fragment((FragmentActivity)ctx  , Myapp_deatal.newInstance(mPostItems,position,mPostItems.get(position)) , true);
                        CSPreferences.Companion.putString(ctx,"appid",mPostItems.get(position).getId().toString());

                    }
                });


                break;

            case LOADING:
//                Do nothing
Log.e(TAG,"LOADING");
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mPostItems == null ? 0 : mPostItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mPostItems.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(App_dataPojo.Result r) {
        mPostItems.add(r);
        notifyItemInserted(mPostItems.size() - 1);
    }

    public void addAll(List<App_dataPojo.Result> moveResults) {
        for (App_dataPojo.Result result : moveResults) {
            add(result);
        }
    }

    public void remove(App_dataPojo.Result r) {
        int position = mPostItems.indexOf(r);
        if (position > -1) {
            mPostItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new App_dataPojo.Result());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mPostItems.size() - 1;
        App_dataPojo.Result result = mPostItems.get(position);

        if (result != null) {
            mPostItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public App_dataPojo.Result getItem(int position) {
        return mPostItems.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class MovieVH extends RecyclerView.ViewHolder {
        private TextView tv_appName;
         private ImageView iv_appImg;
        private ProgressBar mProgress;

        public MovieVH(View itemView) {
            super(itemView);

            tv_appName = (TextView) itemView.findViewById(R.id.appname);
            iv_appImg= (ImageView) itemView.findViewById(R.id.app_icon);
         }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


}
