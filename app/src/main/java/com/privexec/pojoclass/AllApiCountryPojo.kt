package com.privexec.pojoclass

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class AllApiCountryPojo {
    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("countries")
    @Expose
    var countries: List<AllApiCountryModel>? = null

    public class AllApiCountryModel {

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