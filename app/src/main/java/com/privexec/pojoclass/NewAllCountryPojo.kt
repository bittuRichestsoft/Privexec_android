package com.privexec.pojoclass

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




public class NewAllCountryPojo {
    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("countries")
    @Expose
    var countries: List<NewAllCountryModel>? = null


    class NewAllCountryModel {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("sortname")
        @Expose
        var sortname: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("phonecode")
        @Expose
        var phonecode: Int? = null
    }
}