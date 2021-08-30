package com.privexec.pojoclass

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class SearchedAppsPojo {
    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("result")
    @Expose
    var result: List<Result>? = null

    class Result {
        @SerializedName("package_name")
        @Expose
        var packageName: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("image")
        @Expose
        var image: String? = null



        var isSelectedChk: Boolean ?=null

    }

}