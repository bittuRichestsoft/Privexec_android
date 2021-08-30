package com.privexec.pojoclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


public class DeleteAppPojo {

    @SerializedName ("message")
    @Expose
    private var message: String? = null

    @SerializedName("status")
    @Expose
    private var status: Int? = null


    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getStatus(): Int? {
        return status
    }

    fun setStatus(status: Int?) {
        this.status = status
    }
}
