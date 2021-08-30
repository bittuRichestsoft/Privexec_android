package com.privexec.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.privexec.Api_Call.WebAPicall
import com.privexec.pojoclass.Login_registerPojo

class SignUpVM : ViewModel(){

    private val mService  =  WebAPicall()
    fun registerApi(hashMap: HashMap<String,String>,context : Context) : MutableLiveData<Login_registerPojo>? {

        return mService.registerApi(hashMap,context)
    }
}