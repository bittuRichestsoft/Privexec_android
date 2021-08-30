package com.privexec.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.privexec.Api_Call.ApiInterface;
import com.privexec.Api_Call.WebAPicall;
import com.privexec.BR;
import com.privexec.fragmnet.Preferences;
import com.privexec.pojoclass.App_dataPojo;
import com.privexec.pojoclass.countryList.CountryListModelClas;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class PrefrencesVM extends BaseObservable {
    ApiInterface apiInterface = WebAPicall.Factory.create();

    @Bindable
    boolean loading;

    @Bindable
    String errorMsg;


    @Bindable
    public Boolean isVisible;
    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        notifyPropertyChanged(BR.loading);
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        notifyPropertyChanged(BR.errorMsg);
    }

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
        notifyPropertyChanged(BR.isVisible);
    }
    Preferences preferences;
public  Preferences getPreferences(){
    return preferences;
}
    public PrefrencesVM(String monthStr, String sessionId){
        Preferences preferences = new Preferences();
    getList(monthStr.replace("Month",""), sessionId);
    }

    private void getList(String month, String sessionId) {
        setLoading(true);
        apiInterface.notifyNotUsed(sessionId, month )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<Response<App_dataPojo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<App_dataPojo> listResponse) {
                        if(listResponse.body().getStatus().equals(200)){
                            getPreferences().setData(listResponse.body());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        setLoading(false);
                    }

                    @Override
                    public void onComplete() {
                        setLoading(false);
                    }
                });
    }
}
