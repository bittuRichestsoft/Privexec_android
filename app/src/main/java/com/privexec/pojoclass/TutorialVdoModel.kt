package com.privexec.pojoclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class TutorialVdoModel {

    @SerializedName("message")
    @Expose
      var message: String? = null
    @SerializedName("status")
    @Expose
      var status: Int? = null

    @SerializedName("type")
    @Expose
      var type: String? = null



    @SerializedName("video_url")
    @Expose
    var video_url: String? = null

    /*fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String) {
        this.message = message
    }*/

    /*
@SerializedName("type")
@Expose
private String type;
@SerializedName("video_url")
@Expose
private String videoUrl;
@SerializedName("message")
@Expose
private String message;*/

}