package com.privexec.pojoclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Login_registerPojo {

    @SerializedName("status")
    @Expose
    private var status: String? = null
    @SerializedName("success")
    @Expose
    private var success: Boolean? = null
    @SerializedName("message")
    @Expose
    private var message: String? = null
    @SerializedName("profile")
    @Expose
    private var profile: Profile? = null

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }

    fun getSuccess(): Boolean? {
        return success
    }

    fun setSuccess(success: Boolean?) {
        this.success = success
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun getProfile(): Profile? {
        return profile
    }

    fun setProfile(profile: Profile) {
        this.profile = profile
    }
    class Profile{
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
        @SerializedName("user_type")
        @Expose
        private var userType: String? = null
        @SerializedName("session_id")
        @Expose
        private var sessionId: String? = null

        @SerializedName("notify_time")
        @Expose
        private var notifyTime: String? = null

        @SerializedName("dsar_status")
        @Expose
        private var dsarStatus: String? = null
        @SerializedName("password_time")
        @Expose
        private var passwordTime: String? = null


        @SerializedName("country")
        @Expose
        private var country: String? = null

        @SerializedName("date_of_birth")
        @Expose
        private var dateOfBirth: String? = null

        @SerializedName("touch_id")
        @Expose
        private var touchId: String? = null

        @SerializedName("profile_image")
        @Expose
        private var profileImage: String? = null



              fun getNotifyTime(): String? {  return notifyTime }
        fun setNotifyTime(notifyTime: String?) { this.notifyTime = notifyTime }

          fun getDsarStatus(): String? {  return dsarStatus }
        fun setDsarStatus(dsarStatus: String?) { this.dsarStatus = dsarStatus }

        fun getPasswordTime(): String? {  return passwordTime }
        fun setPasswordTime(passwordTime: String?) { this.passwordTime = passwordTime }


        fun getUserId(): Int? {
            return userId
        }

        fun setUserId(userId: Int?) {
            this.userId = userId
        }

        fun getUserName(): String? {
            return userName
        }

        fun setUserName(userName: String) {
            this.userName = userName
        }

        fun getEmail(): String? {
            return email
        }

        fun setEmail(email: String) {
            this.email = email
        }

        fun getPhone(): String? {
            return phone
        }

        fun setPhone(phone: String) {
            this.phone = phone
        }

        fun getUserType(): String? {
            return userType
        }

        fun setUserType(userType: String) {
            this.userType = userType
        }

        fun getSessionId(): String? {
            return sessionId
        }

        fun setSessionId(sessionId: String) {
            this.sessionId = sessionId
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

        fun getTouchId(): String? {
            return touchId
        }

        fun setTouchId(touchId: String?) {
            this.touchId = touchId
        }

        fun getProfileImage(): String? {
            return profileImage
        }

        fun setProfileImage(profileImage: String?) {
            this.profileImage = profileImage
        }
    }
}