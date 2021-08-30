package com.privexec.pojoclass

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class UpdateProfileRes {
        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("success")
        @Expose
        var success: Boolean? = null

        @SerializedName("status")
        @Expose
     private   var status: Int? = null

        @SerializedName("user_detail")
        @Expose
        var userDetail: UserDetail? = null

    fun getStatus(): Int? {
        return status
    }

    fun setStatus(status: Int?) {
        this.status = status
    }


    class UserDetail {


        @SerializedName("user_id")
        @Expose
        private var userId: Int? = null

        @SerializedName("user_name")
        @Expose
        private var userName: String? = null

        @SerializedName("email")
        @Expose
        private var email: String? = null

        @SerializedName("phone")
        @Expose
        private var phone: String? = null

        @SerializedName("country")
        @Expose
        private var country: String? = null

        @SerializedName("date_of_birth")
        @Expose
        private var dateOfBirth: String? = null

        @SerializedName("user_type")
        @Expose
        private var userType: String? = null

        @SerializedName("session_id")
        @Expose
        private var sessionId: String? = null

        @SerializedName("touch_id")
        @Expose
        private var touchId: String? = null

        @SerializedName("notify_time")
        @Expose
        private var notifyTime: String? = null

        @SerializedName("dsar_status")
        @Expose
        private var dsarStatus: String? = null

        @SerializedName("password_time")
        @Expose
        private var passwordTime: String? = null

        @SerializedName("profile_image")
        @Expose
        private var profileImage: String? = null

        fun getUserId(): Int? {
            return userId
        }

        fun setUserId(userId: Int?) {
            this.userId = userId
        }

        fun getUserName(): String? {
            return userName
        }

        fun setUserName(userName: String?) {
            this.userName = userName
        }

        fun getEmail(): String? {
            return email
        }

        fun setEmail(email: String?) {
            this.email = email
        }

        fun getPhone(): String? {
            return phone
        }

        fun setPhone(phone: String?) {
            this.phone = phone
        }

        fun getCountry(): String? {
            return country
        }

        fun setCountry(country: String?) {
            this.country = country
        }

        fun getDateOfBirth(): String? {
            return dateOfBirth
        }

        fun setDateOfBirth(dateOfBirth: String?) {
            this.dateOfBirth = dateOfBirth
        }

        fun getUserType(): String? {
            return userType
        }

        fun setUserType(userType: String?) {
            this.userType = userType
        }

        fun getSessionId(): String? {
            return sessionId
        }

        fun setSessionId(sessionId: String?) {
            this.sessionId = sessionId
        }

        fun getTouchId(): String? {
            return touchId
        }

        fun setTouchId(touchId: String?) {
            this.touchId = touchId
        }

        fun getNotifyTime(): String? {
            return notifyTime
        }

        fun setNotifyTime(notifyTime: String?) {
            this.notifyTime = notifyTime
        }

        fun getDsarStatus(): String? {
            return dsarStatus
        }

        fun setDsarStatus(dsarStatus: String?) {
            this.dsarStatus = dsarStatus
        }

        fun getPasswordTime(): String? {
            return passwordTime
        }

        fun setPasswordTime(passwordTime: String?) {
            this.passwordTime = passwordTime
        }

        fun getProfileImage(): String? {
            return profileImage
        }

        fun setProfileImage(profileImage: String?) {
            this.profileImage = profileImage
        }
    }
}