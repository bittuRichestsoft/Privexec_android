package com.privexec.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.privexec.Api_Call.WebAPicall
import com.privexec.fragmnet.ChangePasswordFrag
import com.privexec.pojoclass.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

class LoginVM : ViewModel(){

    private val mService  =  WebAPicall()
    fun login(hashMap: HashMap<String,String>,context : Context) : MutableLiveData<Login_registerPojo>? {

        return mService.loginApi(hashMap,context)
    }
    fun forgotPasswordApi(sessionid : String ,context : Context) : MutableLiveData<ForgotPassword>? {

        return mService.forgotPasswordData(context,sessionid)
    }
/*

    fun UpdateProfileApi(hashMap: HashMap<String,String>,context : Context) : MutableLiveData<UpdateProfileRes>? {

        return mService.updateProfile(hashMap,context)
    }
*/

    fun UpdateProfileApi(session_id : RequestBody, user_name : RequestBody, email  : RequestBody, phone  : RequestBody, dob : RequestBody,country : RequestBody, imagebody: MultipartBody.Part?, context : Context) : MutableLiveData<UpdateProfileRes>? {

        return mService.updateProfile(session_id,user_name,email,phone,dob,country,imagebody,context)
    }


    fun UpdatePasswordApi(hashMap: HashMap<String,String>,context : Context) : MutableLiveData<UpdatePasswordRes> {

        return mService.UpdatePassword(hashMap,context)
    }

}