package com.privexec.pojoclass

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class AppOnStoreOrNotPojo {
        @SerializedName("message")
        @Expose
        private var message: String? = null
        @SerializedName("status")
        @Expose
        private var status: Int? = null
        @SerializedName("result")
        @Expose
        private var result: List<Result>? = null

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

        fun getResult(): List<Result>? {
                return result
        }

        fun setResult(result: List<Result>) {
                this.result = result
        }
        class Result {
                @SerializedName("package_name")
                @Expose
                var packageName: String? = null

                @SerializedName("firstupdate")
                @Expose
                var firstupdate: String? = null

                @SerializedName("lastupdate")
                @Expose
                var lastupdate: String? = null

                @SerializedName("package_Permissions")
                @Expose
                var packagePermissions: String? = null

                @SerializedName("app_name")
                @Expose
                var app_name: String? = null


                @SerializedName("app_img")
                @Expose
                var app_img: String? = null

        }
}