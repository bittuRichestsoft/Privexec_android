package com.privexec.viewmodel

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.lee_da_hang_pte_ltd.utills.CSPreferences
import com.lee_da_hang_pte_ltd.utills.Global_utility
import com.privexec.Api_Call.WebAPicall
import com.privexec.Api_Call.WebAPicall_2
import com.privexec.activity.Saplash
import com.privexec.pojoclass.*
import com.privexec.pojoclass.countryList.CountryListModelClas
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.lang.Exception

class MyAppVM : ViewModel(){
    var TAG="MyAppVM "
    var progressDialog: ProgressDialog? = null
    private val mService  =  WebAPicall()
      fun appdata(
        sessionid: String,
        jsonArray: String,
        context: Context,
        compositeDisposable: CompositeDisposable?
    ,isDialogChk:Boolean) : MutableLiveData<App_dataPojo>? {
        val liveregisterResponse: MutableLiveData<App_dataPojo> = MutableLiveData()
        if(isDialogChk)
          Global_utility.showDialog(context)
          else{
            showCustomLoader(context,true)
        }
        compositeDisposable!!.add(WebAPicall.create().apps_data(sessionid,jsonArray).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                if(isDialogChk)
                {
                    Global_utility.hideDialog(context)
                }
                else{
                    showCustomLoader(context,false);
                }

            Log.e( TAG,"apps_data  code= "+it.code())
                if (it.code() == 200) {
                    if (it.body() != null) {
                        liveregisterResponse.value = it.body()
                    }
                }else if (it.code() == 403) {
                    Global_utility.showtost(context,"Session Expired")
                    CSPreferences.putString(context, "session_id", "")
                    val intent = Intent(context, Saplash::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(intent)
                    (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    (context as Activity).finish()
                }else if (it.code() == 500) {
                     Global_utility.showtost(context,"Internal Server Error")
                }
                else{
                    if(isDialogChk)
                    Global_utility.showtost(context,it.body()?.getMessage().toString())
                }
            }, {

                it.printStackTrace()
                 Global_utility.hideDialog(context);
            }))

        return liveregisterResponse
        //return mService.appdata(context,sessionid,jsonArray)
    }

  fun  apps_dataForSingle (sessionid: String,
    jsonArray: String,
    context: Context,
    compositeDisposable: CompositeDisposable?
    ,isDialogChk:Boolean) : MutableLiveData<App_dataPojo>? {
        val liveregisterResponse: MutableLiveData<App_dataPojo> = MutableLiveData()
        if(isDialogChk)
            Global_utility.showDialog(context)
        else{
            showCustomLoader(context,true)
        }
        compositeDisposable!!.add(WebAPicall.create().apps_dataForSingle(sessionid,jsonArray).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                if(isDialogChk)
                {
                    Global_utility.hideDialog(context)
                }
                else{
                    showCustomLoader(context,false);
                }

                Log.e( TAG,"apps_data  code= "+it.code())
                if (it.code() == 200) {
                    if (it.body() != null) {
                        liveregisterResponse.value = it.body()
                    }
                }else if (it.code() == 403) {
                    Global_utility.showtost(context,"Session Expired")
                    CSPreferences.putString(context, "session_id", "")
                    val intent = Intent(context, Saplash::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(intent)
                    (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    (context as Activity).finish()
                }else if (it.code() == 500) {
                    Global_utility.showtost(context,"Internal Server Error")
                }
                else{
                    if(isDialogChk)
                        Global_utility.showtost(context,it.body()?.getMessage().toString())
                }
            }, {

                it.printStackTrace()
                Global_utility.hideDialog(context);
            }))

        return liveregisterResponse
        //return mService.appdata(context,sessionid,jsonArray)
    }


    private fun showCustomLoader(context: Context, showOrNot: Boolean) {

      if(showOrNot) {
          progressDialog = ProgressDialog(context)
          progressDialog!!.setMessage("Processing Apps ...")
          progressDialog!!.setCancelable(false)
          progressDialog!!.show()
      }
        else {
try{
    progressDialog!!.dismiss()
}
catch (excep:Exception)
{

}
      }
    }

    fun checkAppLiveOrNot(
        sessionid: String,
        jsonArray: String,
        context: Context,
        compositeDisposable: CompositeDisposable?
    ) : MutableLiveData<AppOnStoreOrNotPojo>? {
        val liveregisterResponse: MutableLiveData<AppOnStoreOrNotPojo> = MutableLiveData()
        Global_utility.showDialog(context)
        compositeDisposable!!.add(WebAPicall.create().checkLiveOrNotApp(sessionid,jsonArray).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)
                Log.e( TAG,"appdata  code= "+Gson().toJson(it.code()))
                if (it.code() == 200) {
                    if (it.body() != null) {
                        liveregisterResponse.value =  it.body()
                    }
                }else if (it.code() == 403) {
                    Global_utility.showtost(context,"Session Expired")
                    CSPreferences.putString(context, "session_id", "")
                    val intent = Intent(context, Saplash::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(intent)
                    (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    (context as Activity).finish()
                }else if (it.code() == 500) {
                    Global_utility.showtost(context,"Internal Server Error")
                }else{
                    Global_utility.showtost(context,it.body()?.getMessage().toString())
                }
            }, {

                it.printStackTrace()
                Global_utility.hideDialog(context);
            }))

        return liveregisterResponse
        //return mService.appdata(context,sessionid,jsonArray)
    }



    fun oneAppData(sessionid : String, jsonArray: String, context : Context) : MutableLiveData<App_dataPojo>? {

        return mService.oneAppData(context,sessionid,jsonArray)
    }

   fun savedsaranswers(sessionid : String,  appid : String,strEmailContent : String, context : Context) : MutableLiveData<SavedsaranswersPojo>? {

        return mService.savedsaranswers(context,sessionid ,appid,strEmailContent)
    }
   /* fun appCountrydata( context : Context) : MutableLiveData<List<CountryListModelClas>>? {

        return mService2.appCountrydata(context)
    }
    */




    fun newCountryResponse(context: Context) : MutableLiveData<NewAllCountryPojo>? {

        return mService.newCountryData(context)
    }
    fun tutorialVideoResponse(context: Context) : MutableLiveData<TutorialVdoModel>? {

        return mService.getVideoTutorialData(context)
    }

    fun appTouchdata(context: Context, sessionId: String?, statusStr: String) : MutableLiveData<App_dataPojo>? {

        return mService.touchid_StatusData(context,sessionId,statusStr)
    }

    fun notifyNotUsed(
        sessionId: String?,
        monthStr: String,
        activity: FragmentActivity
    ): MutableLiveData<App_dataPojo>? {

        return mService.notifyNotUsed(activity,sessionId,monthStr)
    }
    fun notifyChangePassword(
        sessionId: String?,
        changePassword: String,
        activity: FragmentActivity
    ): MutableLiveData<App_dataPojo>? {

        return mService.notifyChangePassword(activity,sessionId,changePassword)
    }


    fun updateCountry(session_id :String ? , country : String, context : Context) : MutableLiveData<UpdateProfileRes>? {


        return mService.updateProfileCountry(""+session_id,country,context)
    }


    fun autosaveDsar(
        sessionId: String?,
        changePassword: String,
        activity: FragmentActivity
    ): MutableLiveData<App_dataPojo>? {

        return mService.autosaveDsar(activity,sessionId,changePassword)
    }
    fun logoutApi(sessionid : String ,context : Context) : MutableLiveData<App_dataPojo>? {

        return mService.logoutApi(context,sessionid)
    }
    fun deleteAppApi(sessionid : String ,strAppId:String,context : Context) : MutableLiveData<DeleteAppPojo>? {

        return mService.deleteApp(context,sessionid,strAppId)
    }

  //  myappViewmodel.getMyAddedApps(CSPreferences.readString(activity!!, "session_id"), context )!!.observe(this, Observer {

        fun getMyAddedApps(
            sessionid: String,
             context: Context,
            compositeDisposable: CompositeDisposable?
        ) : MutableLiveData<App_dataPojo>? {
            val liveregisterResponse: MutableLiveData<App_dataPojo> = MutableLiveData()
            Global_utility.showDialog(context)
            compositeDisposable!!.add(WebAPicall.create().getAllAppsData(sessionid ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    Global_utility.hideDialog(context)
                    Log.e( TAG,"appdata  code= "+Gson().toJson(it.code()))
                    if (it.code() == 200) {
                        if (it.body() != null) {
                            liveregisterResponse.value = it.body()
                        }
                    }else if (it.code() == 403) {
                        Global_utility.showtost(context,"Session Expired")
                        CSPreferences.putString(context, "session_id", "")
                        val intent = Intent(context, Saplash::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        context.startActivity(intent)
                        (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        (context as Activity).finish()
                    }else if (it.code() == 500) {
                        Global_utility.showtost(context,"Internal Server Error")
                    }else{
                        Global_utility.showtost(context,it.body()?.getMessage().toString())
                    }
                }, {

                    it.printStackTrace()
                    Global_utility.hideDialog(context);
                }))

            return liveregisterResponse
            //return mService.appdata(context,sessionid,jsonArray)
        }
    }