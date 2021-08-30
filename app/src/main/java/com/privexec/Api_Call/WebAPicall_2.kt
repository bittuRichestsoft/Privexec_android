package com.privexec.Api_Call

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.lee_da_hang_pte_ltd.utills.Global_utility
import com.privexec.pojoclass.*
import com.privexec.pojoclass.countryList.CountryListModelClas
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONStringer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WebAPicall_2 {
    companion object Factory {
        var BASE_URL_sec = "https://restcountries.eu/rest/v2/"
        var gson = GsonBuilder().setLenient().create()
        fun create(): ApiInterface {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val okhttpClient = OkHttpClient.Builder()
            okhttpClient.addInterceptor(logging)
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_sec)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okhttpClient.build())
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }

    // country Api..................


    fun appCountrydata(context: Context) : MutableLiveData<List<CountryListModelClas>> {
        val liveregisterResponse: MutableLiveData<List<CountryListModelClas>> = MutableLiveData()
        Global_utility.showDialog(context)
        create().country_data().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Global_utility.hideDialog(context)
                if (it.code() == 200) {
                    if (it.body() != null) {
                        liveregisterResponse.value = it.body()
                    }
                }else if (it.code() == 500) {
                    Global_utility.showtost(context,"Internal Server Error")
                }
            }, {
                it.printStackTrace()
                //Global_utility.hideDialog(context);
            })

        return liveregisterResponse
    }
}//https://docs.google.com/spreadsheets/d/1v2errCW6bS1So5YXKkfeIpSpOF4xVOSNbwQfsOqOBvs/edit?usp=sharing