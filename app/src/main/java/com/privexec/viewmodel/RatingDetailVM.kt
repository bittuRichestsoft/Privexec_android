package com.privexec.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.privexec.Api_Call.WebAPicall
import com.privexec.pojoclass.*
import okhttp3.RequestBody

class RatingDetailVM : ViewModel(){

    private val mService  =  WebAPicall()
    fun get_rating(sessionid : String, context : Context) : MutableLiveData<All_ratingPojo>? {
        return mService.ratings(context,sessionid)
    }

    fun get_ratingWithPkgName(sessionid : String,pkgName: String, context : Context) : MutableLiveData<All_ratingPojo>? {
        return mService.ratingsWithPkgName(context,sessionid,pkgName)
    }


    fun searchAppForRate(context : Context,strAppName:String,sessionid : String) : MutableLiveData<All_ratingPojo>? {
        return mService.searchAppForRate(context, strAppName,sessionid)
    }


    fun sendrating(hashMap: HashMap<String,String>, context : Context) : MutableLiveData<SendRating_pojo>? {

        return mService.sendrating(hashMap,context)
    }


    fun getSingleAppRating(sessionid : String, appId: String, context : Context) : MutableLiveData<SingleAppRatingPojo>? {

        return mService.getSingleAppRatingApi(sessionid,appId,context)
    }


}