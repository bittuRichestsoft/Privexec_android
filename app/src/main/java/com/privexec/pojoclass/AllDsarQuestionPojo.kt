package com.privexec.pojoclass

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




public class AllDsarQuestionPojo {


    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("rating")
    @Expose
    var rating: List<AllDsarQuestionModel>? = null

    public class AllDsarQuestionModel {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("questions")
        @Expose
        var questions: String? = null

        @SerializedName("optional")
        @Expose
        var optional: Int? = null


        @SerializedName("answer")
        @Expose
        var answer: String =""

    }

    }