package com.privexec.pojoclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


public class EmailTemplatePojo {


    @SerializedName("result")
    @Expose
    var result: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("status")
    @Expose
    var status: Int? = null
}