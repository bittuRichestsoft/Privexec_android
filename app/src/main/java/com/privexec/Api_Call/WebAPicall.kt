package com.privexec.Api_Call

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.lee_da_hang_pte_ltd.utills.CSPreferences
import com.lee_da_hang_pte_ltd.utills.Global_utility
import com.privexec.activity.Saplash
import com.privexec.pojoclass.*
import com.privexec.pojoclass.countryList.CountryListModelClas
import com.privexec.pojoclass.piiShared.PiiSharedData
import com.privexec.pojoclass.piiTotalApps.PiiTotalAppsResponse
import com.privexec.pojoclass.reportAppByCategory.ReportAppByCategoryPojo
import com.privexec.pojoclass.reportAppByCategory.ReportLastTimeUsedPojo
import com.privexec.pojoclass.reportAppByCategory.ReportPotentiallyDataPojo
import com.privexec.pojoclass.totoalAppsPii.TotalAppsPiiSharedData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WebAPicall {
var TAG="WebAPicall "
    companion object Factory {

      //  var BASE_URL = "https://rrmr.co.in/privexec/api/"  on our server
        // new created instance for testing      http://18.217.96.239:7201/privexec2/
          //var BASE_URL_IP = "http://18.217.96.239:7201/privexec2/"
      var BASE_URL_IP = "http://privexec.net/"
//http://privexec.net:7201/privexec2/
         /*"http://13.59.77.125/"*/    //on client live server
        var BASE_URL_WithApi = BASE_URL_IP +"api/"    //on client live server

        var gson = GsonBuilder().setLenient().create()
        fun create(): ApiInterface {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val okhttpClient = OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES).addInterceptor(logging).build();
           // okhttpClient.addInterceptor(logging)

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_WithApi)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okhttpClient)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }

    // login Api..................
    fun loginApi(hashMap: HashMap<String,String>,context: Context) : MutableLiveData<Login_registerPojo> {
        val liveregisterResponse: MutableLiveData<Login_registerPojo> = MutableLiveData()
        Global_utility.showDialog(context)
        create().getlogin(hashMap).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)
                if (it.code() == 200) {
                    if (it.body() != null) {
                        liveregisterResponse.value = it.body()
                    }
                }else if (it.code() == 500) {
                    Global_utility.showtost(context,"Internal Server Error")
                }else{
                    Global_utility.showtost(context,it.body()?.getMessage().toString())
                }
            }, {
                it.printStackTrace()
                //Global_utility.hideDialog(context);
            })

        return liveregisterResponse
    }
  // Register Api..................
    fun registerApi(hashMap: HashMap<String,String>,context: Context) : MutableLiveData<Login_registerPojo> {
        val liveregisterResponse: MutableLiveData<Login_registerPojo> = MutableLiveData()
        Global_utility.showDialog(context)
        create().getregister(hashMap).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)
                if (it.code() == 200) {
                    if (it.body() != null) {
                        liveregisterResponse.value = it.body()
                    }
                }else if (it.code() == 500) {
                    Global_utility.showtost(context,"Internal Server Error")
                }else{
                    Global_utility.showtost(context,it.body()?.getMessage().toString())
                }
            }, {
                it.printStackTrace()
                //Global_utility.hideDialog(context);
            })

        return liveregisterResponse
    }


// appdata..................
    fun appdata(context: Context,sessionid : String,jsonArray: String) : MutableLiveData<App_dataPojo> {

        val liveregisterResponse: MutableLiveData<App_dataPojo> = MutableLiveData()
    //    Global_utility.showDialog(context)
      /*List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
    nameValuePair.add(new BasicNameValuePair("ActivationCode",
            strActivationCode));
    nameValuePair.add(new BasicNameValuePair("Data", base64));

    Call<EmployeeInfo> call = null;
    try {
        call = NetworkConstants.getNetworkObject().loginUser(getQuery(nameValuePair));
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }*/
        create().apps_data(sessionid,jsonArray).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)
                Log.e("apps_data","code= "+it.code())
                if (it.code() == 200) {
                    if (it.body() != null) {
                        liveregisterResponse.value = it.body()
                    }
                }else if (it.code() == 500) {
                    Global_utility.showtost(context,"Internal Server Error")
                }else if (it.code() == 403) {
                    Global_utility.showtost(context,/*it.body()?.message.toString()*/"session expired")
                    CSPreferences.putString(context, "session_id", "")
                    val intent = Intent(context, Saplash::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(intent)
                    (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    (context as Activity).finish()
                }else{
                    Global_utility.showtost(context,it.body()?.getMessage().toString())
                }
            }, {
                it.printStackTrace()
                 Global_utility.hideDialog(context);
            })

        return liveregisterResponse
    }
 fun logoutApi(context: Context,sessionid : String) : MutableLiveData<App_dataPojo> {
        val livelogoutResponse: MutableLiveData<App_dataPojo> = MutableLiveData()
        Global_utility.showDialog(context)
        create().logoutapi(sessionid).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)
                CSPreferences.putString(context, "session_id", "")
                val intent = Intent(context, Saplash::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                context.startActivity(intent)
                (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                (context as Activity).finish()
              /*  if (it.code() == 200 || it.code() == 403   ) {
                    if (it.body() != null) {
                        livelogoutResponse.value = it.body()
                        context.startActivity(Intent(context, LoginActivity::class.java))
                        CSPreferences.clearPref(context)

                    }
                }else if (it.code() == 500) {
                    Global_utility.showtost(context,"Internal Server Error")
                }else{
                    Global_utility.showtost(context,it.body()?.getMessage().toString())
                }*/
            }, {

                it.printStackTrace()
                //Global_utility.hideDialog(context);
            })

        return livelogoutResponse
    }

    fun deleteApp(context: Context,sessionid : String,strAppId:String) : MutableLiveData<DeleteAppPojo> {

        val livelogoutResponse: MutableLiveData<DeleteAppPojo> = MutableLiveData()
        Global_utility.showDialog(context)
        create().deleteAppApi(sessionid,strAppId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)
                  if (it.code() == 200   ) {
                      if (it.body() != null) {
                         livelogoutResponse.value = it.body()
                      }
                  }else if (it.code() == 500) {
                      Global_utility.showtost(context,"Internal Server Error")
                  } else if (it.code() == 403) {
                      Global_utility.showtost(context,/*it.body()?.message.toString()*/"session expired")
                      CSPreferences.putString(context, "session_id", "")
                      val intent = Intent(context, Saplash::class.java)
                      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                      context.startActivity(intent)
                      (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                      (context as Activity).finish()
                  }else{
                      Global_utility.showtost(context,it.body()?.getMessage().toString())
                  }
            }, {

                it.printStackTrace()
                //Global_utility.hideDialog(context);
            })

        return livelogoutResponse
    }


 fun forgotPasswordData(context: Context,sessionid : String) : MutableLiveData<ForgotPassword> {

        val liveForgotPasswordResponse: MutableLiveData<ForgotPassword> = MutableLiveData()
        Global_utility.showDialog(context)
        create().forgotPasswordApi(sessionid).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)

                if (it.isSuccessful) {
                    Global_utility.showtost(context,it.body()!!.message.toString())
                    liveForgotPasswordResponse.value = it.body()
                    if (it.body() != null) {
                        liveForgotPasswordResponse.value = it.body()
                    }
                    //Global_utility.showtost(context,it.body()?.getMessage().toString())
                }else if (it.code() == 500) {

                    Global_utility.showtost(context,"Internal Server Error")
                }else{
                  //  liveForgotPasswordResponse.value = it.body()
                    Global_utility.showtost(context,/*it.message() +*/"Email doesn't exists".toString())
                }
            }, {
               // Global_utility.showtost(context,it.body()?.getMessage().toString())

                it.printStackTrace()
                //Global_utility.hideDialog(context);
            })

        return liveForgotPasswordResponse
    }


    /*fun updateProfile(hashMap: HashMap<String,String>,context: Context) : MutableLiveData<UpdateProfileRes> {

        val liveUpdateProfileRes: MutableLiveData<UpdateProfileRes> = MutableLiveData()
        Global_utility.showDialog(context)

        create().getUpdateProfile(hashMap).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)
                if (it.code() == 200) {
                    if (it.body() != null) {
                        liveUpdateProfileRes.value = it.body()
                    }
                }else if (it.code() == 500) {
                    Global_utility.showtost(context,"Internal Server Error")
                }else{
                    Global_utility.showtost(context,it.body()?.message.toString())
                }
            }, {

                it.printStackTrace()
                //Global_utility.hideDialog(context);
            })

        return liveUpdateProfileRes
    }
*/

    /*/*  session_id, strName, strEmail, strPhone,strDob,strCountry, imagebody,
            context*/*/
    fun updateProfile(session_id : RequestBody, user_name : RequestBody,email : RequestBody,  phone : RequestBody,dob : RequestBody,  country : RequestBody, imagebody: MultipartBody.Part?, context: Context) : MutableLiveData<UpdateProfileRes>{

        val liveregisterResponse: MutableLiveData<UpdateProfileRes> = MutableLiveData()
        Global_utility.showDialog(context)
        create().getUpdateProfile(session_id,user_name,email,/*phone,*/dob,country,imagebody).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)

             Log.e("WebApiCall","updateProfile  code = "+it.code()+" <<>>   message()"+it.message())
                if (it.code() == 200) {
                    if (it.body() != null) {
                        liveregisterResponse.value = it.body()
                    }
                }
                else if (it.code() == 403) {
                    Global_utility.showtost(context,/*it.body()?.message.toString()*/"session expired")
                    CSPreferences.putString(context, "session_id", "")
                    val intent = Intent(context, Saplash::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(intent)
                    (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    (context as Activity).finish()
                }
                else if (it.code() == 500) {
                    Global_utility.showtost(context,"Internal Server Error")
                }else{
                    Global_utility.showtost(context,it.body()?.message.toString())
                }
            }, {

                it.printStackTrace()
                Global_utility.hideDialog(context);
            })
        return liveregisterResponse
    }


    /*session_id,country,context)*/
    fun updateProfileCountry(session_id : String, user_country : String, context: Context) : MutableLiveData<UpdateProfileRes>{
        val liveregisterResponse: MutableLiveData<UpdateProfileRes> = MutableLiveData()
        Global_utility.showDialog(context)
        create().getUpdateProfileCountry(session_id,user_country ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Log.e("WebApiCall","updateProfileCountry  code = "+it.code()+" <<>>   message()"+it.message())
                Global_utility.hideDialog(context)
                if (it.code() == 200) {
                    if (it.body() != null) {
                        liveregisterResponse.value = it.body()
                    }
                }else if (it.code() == 403) {
                    Global_utility.showtost(context,/*it.body()?.message.toString()*/"session expired")
                    CSPreferences.putString(context, "session_id", "")
                    val intent = Intent(context, Saplash::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(intent)
                    (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    (context as Activity).finish()
                }else if (it.code() == 500) {
                    Global_utility.showtost(context,"Internal Server Error")
                }else{
                    Global_utility.showtost(context,it.body()?.message.toString())
                }
            }, {

                it.printStackTrace()
                 Global_utility.hideDialog(context);
            })
        return liveregisterResponse
    }

    fun  UpdatePassword(hashMap: HashMap<String,String>,context: Context) : MutableLiveData<UpdatePasswordRes> {

        val liveUpdatePasswordRes: MutableLiveData<UpdatePasswordRes> = MutableLiveData()
        Global_utility.showDialog(context)

        create().changePassword(hashMap).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)
                if (it.code() == 200) {
                    if (it.body() != null) {
                        liveUpdatePasswordRes.value = it.body()
                    }
                }else if (it.code() == 500) {
                    Global_utility.showtost(context,"Internal Server Error")
                }else if (it.code() == 403) {
                    Global_utility.showtost(context,/*it.body()?.message.toString()*/"session expired")
                    CSPreferences.putString(context, "session_id", "")
                    val intent = Intent(context, Saplash::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(intent)
                    (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    (context as Activity).finish()
                }
                else if (it.code() == 400) {
                    Global_utility.showtost(context,"Your old password is incorrect."/*+it.body()?.message.toString()*/)
                }else{
                    Global_utility.showtost(context,it.body()?.message.toString())
                }
            }, {

                it.printStackTrace()
                 Global_utility.hideDialog(context);
            })

        return liveUpdatePasswordRes
    }

    // appdata..................
    fun oneAppData(context: Context,sessionid : String,jsonArray: String) : MutableLiveData<App_dataPojo> {

        val liveregisterResponse: MutableLiveData<App_dataPojo> = MutableLiveData()
        Global_utility.showDialog(context)
        create().one_apps_data(sessionid,jsonArray).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)
                if (it.code() == 200) {
                    if (it.body() != null) {
                        liveregisterResponse.value = it.body()
                    }
                }else if (it.code() == 403) {
                    Global_utility.showtost(context,/*it.body()?.message.toString()*/"session expired")
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
            })

        return liveregisterResponse
    }

    // savedsaranswers..................
    fun savedsaranswers(context: Context,sessionid : String, app_id: String,strEmailContent: String) : MutableLiveData<SavedsaranswersPojo> {

        val liveregisterResponse: MutableLiveData<SavedsaranswersPojo> = MutableLiveData()
        Global_utility.showDialog(context)
        create().savedsaranswers(sessionid, app_id,strEmailContent).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)
                if (it.code() == 200) {
                    if (it.body() != null) {
                        liveregisterResponse.value = it.body()
                    }
                }else if (it.code() == 500) {
                    Global_utility.showtost(context,"Internal Server Error")
                }else if (it.code() == 403) {
                    Global_utility.showtost(context,/*it.body()?.message.toString()*/"session expired")
                    CSPreferences.putString(context, "session_id", "")
                    val intent = Intent(context, Saplash::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(intent)
                    (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    (context as Activity).finish()
                }else{
                    Global_utility.showtost(context,it.body()?.getMessage().toString())
                }
            }, {

                it.printStackTrace()
                 Global_utility.hideDialog(context);
            })

        return liveregisterResponse
    }

// ratings..................
    fun ratings(context: Context,sessionid : String) : MutableLiveData<All_ratingPojo> {
        val liveregisterResponse: MutableLiveData<All_ratingPojo> = MutableLiveData()
        Global_utility.showDialog(context)
        create().ratings(sessionid).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)
                if (it.code() == 200) {
                    if (it.body() != null) {
                        liveregisterResponse.value = it.body()
                    }
                }else if (it.code() == 500) {
                    Global_utility.showtost(context,"Internal Server Error")
                }else if (it.code() == 403) {
                    Global_utility.showtost(context,/*it.body()?.message.toString()*/"session expired")
                    CSPreferences.putString(context, "session_id", "")
                    val intent = Intent(context, Saplash::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(intent)
                    (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    (context as Activity).finish()
                }else{
                    Global_utility.showtost(context,it.body()?.getMessage().toString())
                }
            }, {

                it.printStackTrace()
                 Global_utility.hideDialog(context);
            })

        return liveregisterResponse
    }
    fun  ratingsWithPkgName(context: Context,sessionid : String,strPkgName : String) : MutableLiveData<All_ratingPojo> {
        val liveregisterResponse: MutableLiveData<All_ratingPojo> = MutableLiveData()
        Global_utility.showDialog(context)
        create().ratingsWithPkgName(sessionid,strPkgName).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)
                if (it.code() == 200) {
                    if (it.body() != null) {
                        liveregisterResponse.value = it.body()
                    }
                }else if (it.code() == 500) {
                    Global_utility.showtost(context,"Internal Server Error")
                }else if (it.code() == 403) {
                    Global_utility.showtost(context,/*it.body()?.message.toString()*/"session expired")
                    CSPreferences.putString(context, "session_id", "")
                    val intent = Intent(context, Saplash::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(intent)
                    (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    (context as Activity).finish()
                }else{
                    Global_utility.showtost(context,it.body()?.getMessage().toString())
                }
            }, {

                it.printStackTrace()
                Global_utility.hideDialog(context);
            })

        return liveregisterResponse
    }


    // login Api..................
    fun sendrating(hashMap: HashMap<String,String>,context: Context) : MutableLiveData<SendRating_pojo> {

        val liveregisterResponse: MutableLiveData<SendRating_pojo> = MutableLiveData()
        Global_utility.showDialog(context)

        create().rate_app(hashMap).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)
                if (it.code() == 200) {
                    if (it.body() != null) {
                        liveregisterResponse.value = it.body()
                    }
                }else if (it.code() == 500) {
                    Global_utility.showtost(context,"Internal Server Error")
                }else if (it.code() == 403) {
                    Global_utility.showtost(context,/*it.body()?.message.toString()*/"session expired")
                    CSPreferences.putString(context, "session_id", "")
                    val intent = Intent(context, Saplash::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(intent)
                    (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    (context as Activity).finish()
                }else{
                    Global_utility.showtost(context,it.body()?.getMessage().toString())
                }
            }, {

                it.printStackTrace()
                //Global_utility.hideDialog(context);
            })

        return liveregisterResponse
    }


    fun searchAppForRate( context: Context,strAppName:String,sessionid:String) : MutableLiveData<All_ratingPojo> {

        val liveregisterResponse: MutableLiveData<All_ratingPojo> = MutableLiveData()
        Global_utility.showDialog(context)

        create().searchAppForRate(sessionid  ,strAppName).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)
                if (it.code() == 200) {
                    if (it.body() != null) {
                        liveregisterResponse.value = it.body()
                    }
                }else if (it.code() == 500) {
                    Global_utility.showtost(context,"Internal Server Error")
                }else if (it.code() == 403) {
                    Global_utility.showtost(context,/*it.body()?.message.toString()*/"session expired")
                    CSPreferences.putString(context, "session_id", "")
                    val intent = Intent(context, Saplash::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(intent)
                    (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    (context as Activity).finish()
                }else{
                    Global_utility.showtost(context, ""+it.body()!!.getMessage())
                }
            }, {

                it.printStackTrace()
                Global_utility.hideDialog(context);
            })

        return liveregisterResponse
    }


    fun getSingleAppRatingApi( sessionId: String?, appId: String,context: Context) : MutableLiveData<SingleAppRatingPojo> {

        val liveSingleRateResponse: MutableLiveData<SingleAppRatingPojo> = MutableLiveData()
        Global_utility.showDialog(context)

        create().getSingleAppRating(""+sessionId,appId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)
                if (it.code() == 200) {
                    if (it.body() != null) {
                        liveSingleRateResponse.value = it.body()
                    }
                }else if (it.code() == 500) {
                    Global_utility.showtost(context,"Internal Server Error")
                }else if (it.code() == 403) {
                    Global_utility.showtost(context,/*it.body()?.message.toString()*/"session expired")
                    CSPreferences.putString(context, "session_id", "")
                    val intent = Intent(context, Saplash::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(intent)
                    (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    (context as Activity).finish()
                }else{
                    Global_utility.showtost(context,it.body()?.getMessage().toString())
                }
            }, {

                it.printStackTrace()
                Global_utility.hideDialog(context);
            })

        return liveSingleRateResponse
    }


    fun touchid_StatusData(context: Context, sessionId: String?, statusStr: String): MutableLiveData<App_dataPojo> {

        val livetouchIdResponse: MutableLiveData<App_dataPojo> = MutableLiveData()
        Global_utility.showDialog(context)
        create().touchid_StatusData(sessionId!!,statusStr).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)
                if (it.code() == 200) {
                    if (it.body() != null) {
                        livetouchIdResponse.value = it.body()
                    }
                }  else if (it.code() == 403) {
                    Global_utility.showtost(context,/*it.body()?.message.toString()*/"session expired")
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
            })

        return livetouchIdResponse
    }


    fun notifyNotUsed(
        context: FragmentActivity,
        sessionId: String?,
        monthStr: String
    ): MutableLiveData<App_dataPojo> {

        val livenotifyNotUsedResponse: MutableLiveData<App_dataPojo> = MutableLiveData()
        //Global_utility.showDialog(context)
        create().notifyNotUsed(sessionId!!,monthStr).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)
                if (it.code() == 200) {
                    if (it.body() != null) {
                        livenotifyNotUsedResponse.value = it.body()
                    }
                }
                else if (it.code() == 403) {
                    Global_utility.showtost(context,/*it.body()?.message.toString()*/"session expired")
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
            })

        return livenotifyNotUsedResponse
    }


    fun notifyChangePassword(
        context: FragmentActivity, sessionId: String?, changePassword: String): MutableLiveData<App_dataPojo> {
        val livenotifyChangePasswordResponse: MutableLiveData<App_dataPojo> = MutableLiveData()
        //Global_utility.showDialog(context)
        create().notifyChangePassword(sessionId!!,changePassword).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                 Global_utility.hideDialog(context)
                if (it.code() == 200) {
                    if (it.body() != null) {
                        livenotifyChangePasswordResponse.value = it.body()
                    }
                }  else if (it.code() == 403) {
                    Global_utility.showtost(context,/*it.body()?.message.toString()*/"session expired")
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
            })

        return livenotifyChangePasswordResponse
    }



    fun newCountryData(
        context: Context): MutableLiveData<NewAllCountryPojo> {

        val livenotifyChangePasswordResponse: MutableLiveData<NewAllCountryPojo> = MutableLiveData()
        //Global_utility.showDialog(context)
        create().getNewAllCountries().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)
                if (it.code() == 200) {
                    if (it.body() != null) {
                        livenotifyChangePasswordResponse.value = it.body()
                    }
                }else if (it.code() == 500) {
                    Global_utility.showtost(context,"Internal Server Error")
                }else{
                    Global_utility.showtost(context,it.body()?.message.toString())
                }
            }, {

                it.printStackTrace()
                Global_utility.hideDialog(context);
            })

        return livenotifyChangePasswordResponse
    }

    fun getVideoTutorialData(
        context: Context): MutableLiveData<TutorialVdoModel> {

        val livenotifyChangePasswordResponse: MutableLiveData<TutorialVdoModel> = MutableLiveData()
        //Global_utility.showDialog(context)
        create().getTutorialVideo().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)
                if (it.code() == 200) {
                    if (it.body() != null) {
                        livenotifyChangePasswordResponse.value = it.body()
                    }
                }else if (it.code() == 500) {
                    Global_utility.showtost(context,"Internal Server Error")
                }else{
                    Global_utility.showtost(context,it.body()?.message.toString())
                }
            }, {

                it.printStackTrace()
                Global_utility.hideDialog(context);
            })

        return livenotifyChangePasswordResponse
    }


    fun autosaveDsar(
        context: FragmentActivity, sessionId: String?, changePassword: String): MutableLiveData<App_dataPojo> {
         val livenotifyChangePasswordResponse: MutableLiveData<App_dataPojo> = MutableLiveData()
        //Global_utility.showDialog(context)
        create().autosave_Dsar(sessionId!!,changePassword).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
               // Global_utility.hideDialog(context)
                if (it.code() == 200) {
                    if (it.body() != null) {
                        livenotifyChangePasswordResponse.value = it.body()
                    }
                }  else if (it.code() == 403) {
                    Global_utility.showtost(context,/*it.body()?.message.toString()*/"session expired")
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
            })

        return livenotifyChangePasswordResponse
    }


    fun reportOfAppCategoryApi(sessionId: String?, context: FragmentActivity): MutableLiveData<ReportAppByCategoryPojo> {
        val livePersmisionResponse: MutableLiveData<ReportAppByCategoryPojo> = MutableLiveData()
         Global_utility.showDialog(context)
        create().reportOfAppCategoryApi(sessionId!!).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)
                if (it.code() == 200) {
                    if (it.body() != null) {
                        livePersmisionResponse.value = it.body()
                    }
                }
                else if (it.code() == 403) {
                    Global_utility.showtost(context,/*it.body()?.message.toString()*/"session expired")
                    CSPreferences.putString(context, "session_id", "")
                    val intent = Intent(context, Saplash::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(intent)
                    (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    (context as Activity).finish()
                }
                else if (it.code() == 500) {
                    Global_utility.showtost(context,"Internal Server Error")
                }else{
                    Global_utility.showtost(context,it.body()?.message.toString())
                }
            }, {

                it.printStackTrace()
                 Global_utility.hideDialog(context);
            })

        return livePersmisionResponse
    }


    fun timeUsedByAppApi(sessionId: String?, context: FragmentActivity): MutableLiveData<ReportLastTimeUsedPojo> {
        val livePersmisionResponse: MutableLiveData<ReportLastTimeUsedPojo> = MutableLiveData()
        //Global_utility.showDialog(context)
        create().reportOfLastTimeUsedAppsApi(sessionId!!).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                // Global_utility.hideDialog(context)
                if (it.code() == 200) {
                    if (it.body() != null) {
                        livePersmisionResponse.value = it.body()
                    }
                }
                else if (it.code() == 403) {
                    Global_utility.showtost(context,"session expired")
                    CSPreferences.putString(context, "session_id", "")
                    val intent = Intent(context, Saplash::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(intent)
                    (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    (context as Activity).finish()
                }
                else if (it.code() == 500) {
                    Global_utility.showtost(context,"Internal Server Error")
                }else{
                    Global_utility.showtost(context,it.body()?.message.toString())
                }
            }, {

                it.printStackTrace()
                Global_utility.hideDialog(context);
            })

        return livePersmisionResponse
    }

    fun reportOfPersonalPotentialDataApi(sessionId: String?, context: FragmentActivity): MutableLiveData<ReportPotentiallyDataPojo> {
    Log.e(TAG,"reportOfPersonalPotentialDataApi")
        val livePersmisionResponse: MutableLiveData<ReportPotentiallyDataPojo> = MutableLiveData()
        //Global_utility.showDialog(context)
        create().reportOfPersonalPotentialDataApi(sessionId!!).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                // Global_utility.hideDialog(context)
                if (it.code() == 200) {
                    if (it.body() != null) {
                        livePersmisionResponse.value = it.body()
                    }
                }
                else if (it.code() == 403) {
                    Global_utility.showtost(context,/*it.body()?.message.toString()*/"session expired")
                    CSPreferences.putString(context, "session_id", "")
                    val intent = Intent(context, Saplash::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(intent)
                    (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    (context as Activity).finish()
                }
                else if (it.code() == 500) {
                    Global_utility.showtost(context,"Internal Server Error")
                }else{
                    Global_utility.showtost(context,it.body()?.message.toString())
                }
            }, {

                it.printStackTrace()
                Global_utility.hideDialog(context);
            })

        return livePersmisionResponse
    }


    fun getPiiData(sessionId: String?, context: FragmentActivity): MutableLiveData<PiiSharedData> {
        val livePersmisionResponse: MutableLiveData<PiiSharedData> = MutableLiveData()
        //Global_utility.showDialog(context)
        create().get_PiiApiData(sessionId!!).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                // Global_utility.hideDialog(context)
                if (it.code() == 200) {
                    if (it.body() != null) {
                        livePersmisionResponse.value = it.body()
                    }
                }
                else if (it.code() == 403) {
                    Global_utility.showtost(context,/*it.body()?.message.toString()*/"session expired")
                    CSPreferences.putString(context, "session_id", "")
                    val intent = Intent(context, Saplash::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(intent)
                    (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    (context as Activity).finish()
                }
                else if (it.code() == 500) {
                    Global_utility.showtost(context,"Internal Server Error")
                }else{
                    Global_utility.showtost(context,it.body()?.getMessage().toString())
                }
            }, {

                it.printStackTrace()
                Global_utility.hideDialog(context);
            })

        return livePersmisionResponse
    }

    fun getTotalAppsPiiData(sessionId: String?, context: FragmentActivity): MutableLiveData<TotalAppsPiiSharedData> {
        val liveTotalAppsResponse: MutableLiveData<TotalAppsPiiSharedData> = MutableLiveData()
        //Global_utility.showDialog(context)
        create().get_TotalAppsPiiApiData(sessionId!!).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)
                if (it.code() == 200) {
                    if (it.body() != null) {
                        liveTotalAppsResponse.value = it.body()
                    }
                }  else if (it.code() == 403) {
                    Global_utility.showtost(context,"session expired")

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
            })
        return liveTotalAppsResponse
    }

    fun getPiiTotalAppsData(sessionId: String?, context: FragmentActivity): MutableLiveData<PiiTotalAppsResponse> {
        val livePiiTotalAppsResponse: MutableLiveData<PiiTotalAppsResponse> = MutableLiveData()
        //Global_utility.showDialog(context)
        create().get_PiiTotalAppsData(sessionId!!).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                 Global_utility.hideDialog(context)
                if (it.code() == 200) {
                    if (it.body() != null) {
                        livePiiTotalAppsResponse.value = it.body()
                    }
                }  else if (it.code() == 403) {
                    Global_utility.showtost(context,"session expired")
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
            })

        return livePiiTotalAppsResponse
    }



    fun getDsarQuestApi(context: Context,sessionid : String) : MutableLiveData<AllDsarQuestionPojo> {

        val liveDsarQuestionResp: MutableLiveData<AllDsarQuestionPojo> = MutableLiveData()
        Global_utility.showDialog(context)
        create().getDsarQuestApi(sessionid).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)

                if (it.isSuccessful) {
                  //  Global_utility.showtost(context,it.body()!!.message.toString())
                    liveDsarQuestionResp.value = it.body()
                    if (it.body() != null) {
                        liveDsarQuestionResp.value = it.body()
                    }else{
 Global_utility.showtost(context,it.body()!!.message.toString())
                     }
                } else if (it.code() == 403) {
                    Global_utility.showtost(context,/*it.body()?.message.toString()*/"session expired")
                    CSPreferences.putString(context, "session_id", "")
                    val intent = Intent(context, Saplash::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(intent)
                    (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    (context as Activity).finish()
                }else if (it.code() == 500) {

                    Global_utility.showtost(context,"Internal Server Error")
                }else{
                    //  liveForgotPasswordResponse.value = it.body()
                    Global_utility.showtost(context,/*it.message() +*/"Email doesn't exists".toString())
                }
            }, {
                // Global_utility.showtost(context,it.body()?.getMessage().toString())
                it.printStackTrace()
                 Global_utility.hideDialog(context);
            })

        return liveDsarQuestionResp
    }

    /*sessionId ,jsonQuesAnsArray,appid*/
    fun getEmailTemplateApi(context: Context,sessionid : String,jsonQuesAnsArray : String,appid : String) : MutableLiveData<EmailTemplatePojo> {

        val liveDsarQuestionResp: MutableLiveData<EmailTemplatePojo> = MutableLiveData()
        Global_utility.showDialog(context)
        create().getEmailTemplate(sessionid,jsonQuesAnsArray,appid).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)

                if (it.isSuccessful) {
                    //  Global_utility.showtost(context,it.body()!!.message.toString())
                    liveDsarQuestionResp.value = it.body()
                    if (it.body() != null) {
                        liveDsarQuestionResp.value = it.body()
                    }else{
                        Global_utility.showtost(context,it.body()!!.message.toString())
                    }
                } else if (it.code() == 403) {
                    Global_utility.showtost(context,/*it.body()?.message.toString()*/"session expired")
                    CSPreferences.putString(context, "session_id", "")
                    val intent = Intent(context, Saplash::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(intent)
                    (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    (context as Activity).finish()
                }else if (it.code() == 500) {

                    Global_utility.showtost(context,"Internal Server Error")
                }else{
                    //  liveForgotPasswordResponse.value = it.body()
                    Global_utility.showtost(context,/*it.message() +*/"Email doesn't exists".toString())
                }
            }, {
                // Global_utility.showtost(context,it.body()?.getMessage().toString())

                it.printStackTrace()
                Global_utility.hideDialog(context);
            })

        return liveDsarQuestionResp
    }


}//https://docs.google.com/spreadsheets/d/1v2errCW6bS1So5YXKkfeIpSpOF4xVOSNbwQfsOqOBvs/edit?usp=sharing