package com.privexec.pojoclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class All_ratingPojo {

    @SerializedName("message")
    @Expose
    private var message: String? = null
    @SerializedName("status")
    @Expose
    private var status: Int? = null
    @SerializedName("rating")
    @Expose
    private var rating: List<Rating>? = null

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

    fun getRating(): List<Rating>? {
        return rating
    }

    fun setRating(rating: List<Rating>) {
        this.rating = rating
    }
    class Rating{

        @SerializedName("id")
        @Expose
        private var id: Int? = null

        @SerializedName("title")
        @Expose
        private var title: String? = null


        @SerializedName("app_full_title")
        @Expose
        private var appFullTitle: String? = null



        @SerializedName("android_package_name")
        @Expose
        private var androidPackageName: String? = null

        @SerializedName("image")
        @Expose
        private var image: String? = null

        @SerializedName("avg_rating")
        @Expose
        private var avgRating: String? = null

        @SerializedName("detail")
        @Expose
        private var detail: Detail? = null


        @SerializedName("privacy_policy")
        @Expose
        private var privacyPolicy: String? = null

        @SerializedName("sponsored")
        @Expose
        private var sponsored: Int? = null

        @SerializedName("company_name")
        @Expose
        private var companyName: String? = null

        @SerializedName("video_url")
        @Expose
        private var videoUrl: String? = null


        fun getPrivacyPolicy(): String? {
            return privacyPolicy
        }

        fun setPrivacyPolicy(privacyPolicy: String?) {
            this.privacyPolicy = privacyPolicy
        }

        fun getSponsored(): Int? {
            return sponsored
        }

        fun setSponsored(sponsored: Int?) {
            this.sponsored = sponsored
        }

        fun getCompanyName(): String? {
            return companyName
        }

        fun setCompanyName(companyName: String?) {
            this.companyName = companyName
        }

        fun getVideoUrl(): String? {
            return videoUrl
        }

        fun setVideoUrl(videoUrl: String?) {
            this.videoUrl = videoUrl
        }

        fun getId(): Int? {
            return id
        }

        fun setId(id: Int?) {
            this.id = id
        }

        fun getTitle(): String? {
            return title
        }

        fun setTitle(title: String?) {
            this.title = title
        }

        fun getappFullTitle(): String? {
            return appFullTitle
        }

        fun setAppFullTitle(appFullTitle: String?) {
            this.appFullTitle= appFullTitle
        }


        fun getImage(): String? {
            return image
        }

        fun setImage(image: String?) {
            this.image = image
        }

        fun getAvgRating(): String? {
            return avgRating
        }

        fun setAvgRating(avgRating: String?) {
            this.avgRating = avgRating
        }

        fun getDetail(): Detail? {
            return detail
        }

        fun setDetail(detail: Detail?) {
            this.detail = detail
        }

        fun getAndroidPackageName(): String? {
            return androidPackageName
        }

        fun setAndroidPackageName(androidPackageName: String?) {
            this.androidPackageName = androidPackageName
        }

class Detail{
    @SerializedName("review_count")
    @Expose
    private var reviewCount: Int? = null
    @SerializedName("5_stars_count")
    @Expose
    private var _5StarsCount: Int? = null
    @SerializedName("4_stars_count")
    @Expose
    private var _4StarsCount: Int? = null
    @SerializedName("3_stars_count")
    @Expose
    private var _3StarsCount: Int? = null
    @SerializedName("2_stars_count")
    @Expose
    private var _2StarsCount: Int? = null
    @SerializedName("1_stars_count")
    @Expose
    private var _1StarsCount: Int? = null
    @SerializedName("all_reviews")
    @Expose
    private var allReviews: List<AllReview>? = null

    fun getReviewCount(): Int? {
        return reviewCount
    }

    fun setReviewCount(reviewCount: Int?) {
        this.reviewCount = reviewCount
    }

    fun get5StarsCount(): Int? {
        return _5StarsCount
    }

    fun set5StarsCount(_5StarsCount: Int?) {
        this._5StarsCount = _5StarsCount
    }

    fun get4StarsCount(): Int? {
        return _4StarsCount
    }

    fun set4StarsCount(_4StarsCount: Int?) {
        this._4StarsCount = _4StarsCount
    }

    fun get3StarsCount(): Int? {
        return _3StarsCount
    }

    fun set3StarsCount(_3StarsCount: Int?) {
        this._3StarsCount = _3StarsCount
    }

    fun get2StarsCount(): Int? {
        return _2StarsCount
    }

    fun set2StarsCount(_2StarsCount: Int?) {
        this._2StarsCount = _2StarsCount
    }

    fun get1StarsCount(): Int? {
        return _1StarsCount
    }

    fun set1StarsCount(_1StarsCount: Int?) {
        this._1StarsCount = _1StarsCount
    }

    fun getAllReviews(): List<AllReview>? {
        return allReviews
    }

    fun setAllReviews(allReviews: List<AllReview>) {
        this.allReviews = allReviews
    }
}
        class AllReview{
            @SerializedName("name")
            @Expose
            private var name: String? = null
            @SerializedName("avg_rating")
            @Expose
            private var avgRating: Float? = null
            @SerializedName("review")
            @Expose
            private var review: String? = null
            @SerializedName("created_at")
            @Expose
            private var createdAt: Any? = null

            fun getName(): String? {
                return name
            }

            fun setName(name: String) {
                this.name = name
            }

            fun getAvgRating(): Float? {
                return avgRating
            }

            fun setAvgRating(avgRating: Float?) {
                this.avgRating = avgRating
            }

            fun getReview(): String? {
                return review
            }

            fun setReview(review: String) {
                this.review = review
            }

            fun getCreatedAt(): Any? {
                return createdAt
            }

            fun setCreatedAt(createdAt: Any) {
                this.createdAt = createdAt
            }
        }
    }
}