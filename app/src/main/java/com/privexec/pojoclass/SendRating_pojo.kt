package com.privexec.pojoclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class SendRating_pojo {
    @SerializedName("message")
    @Expose
    private var message: String? = null
    @SerializedName("status")
    @Expose
    private var status: Int? = null
    @SerializedName("rating")
    @Expose
    private var rating: Double? = null

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun getStatus(): Int? {
        return status
    }

    fun setStatus(status: Int?) {
        this.status = status
    }

    fun getRating(): Double? {
        return rating
    }

    fun setRating(rating: Double?) {
        this.rating = rating
    }
}