package com.privexec.pojoclass

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




public class UpdatePasswordRes{
    @SerializedName("message")
    @Expose
    var message: String? = null


    @SerializedName("error")
    @Expose
    var error: String? = ""


}