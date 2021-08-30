package com.privexec.pojoclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class SavedsaranswersPojo {
    @SerializedName("message")
    @Expose
    private var message: String? = null
    @SerializedName("status")
    @Expose
    private var status: Int? = null
    @SerializedName("result")
    @Expose
    private var result: List<Result>? = null
    @SerializedName("pdf")
    @Expose
    private var pdf: String? = null
    @SerializedName("mail_sent")
    @Expose
    private var mailSent: Any? = null
    fun getMessage(): String? {
        return message
    }
    fun getPdf(): String? {
        return pdf
    }

    fun setPdf(pdf: String) {
        this.pdf = pdf
    }

    fun getMailSent(): Any? {
        return mailSent
    }

    fun setMailSent(mailSent: Any) {
        this.mailSent = mailSent
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

    class Result{
        @SerializedName("id")
        @Expose
        private var id: Int? = null
        @SerializedName("user_id")
        @Expose
        private var userId: Int? = null
        @SerializedName("app_id")
        @Expose
        private var appId: Int? = null
        @SerializedName("question_id")
        @Expose
        private var questionId: Int? = null
        @SerializedName("answer")
        @Expose
        private var answer: String? = null
        @SerializedName("status")
        @Expose
        private var status: Int? = null
        @SerializedName("created_at")
        @Expose
        private var createdAt: Any? = null
        @SerializedName("updated_at")
        @Expose
        private var updatedAt: Any? = null

        fun getId(): Int? {
            return id
        }

        fun setId(id: Int?) {
            this.id = id
        }

        fun getUserId(): Int? {
            return userId
        }

        fun setUserId(userId: Int?) {
            this.userId = userId
        }

        fun getAppId(): Int? {
            return appId
        }

        fun setAppId(appId: Int?) {
            this.appId = appId
        }

        fun getQuestionId(): Int? {
            return questionId
        }

        fun setQuestionId(questionId: Int?) {
            this.questionId = questionId
        }

        fun getAnswer(): String? {
            return answer
        }

        fun setAnswer(answer: String) {
            this.answer = answer
        }

        fun getStatus(): Int? {
            return status
        }

        fun setStatus(status: Int?) {
            this.status = status
        }

        fun getCreatedAt(): Any? {
            return createdAt
        }

        fun setCreatedAt(createdAt: Any) {
            this.createdAt = createdAt
        }

        fun getUpdatedAt(): Any? {
            return updatedAt
        }

        fun setUpdatedAt(updatedAt: Any) {
            this.updatedAt = updatedAt
        }
    }
}