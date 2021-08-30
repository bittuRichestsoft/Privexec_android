package com.privexec.pojoclass

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import com.privexec.pojoclass.All_ratingPojo.Rating.AllReview







public class SingleAppRatingPojo {

    @SerializedName("message")
    @Expose
    private var message: String? = null

    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("rating")
    @Expose
    private var rating: List<Rating?>? = null

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getStatus(): Int? {
        return status
    }

    fun setStatus(status: Int?) {
        this.status = status
    }

    fun getRating(): List<Rating?>? {
        return rating
    }

    fun setRating(rating: List<Rating?>?) {
        this.rating = rating
    }

    public class  Rating{


        @SerializedName("id")
        @Expose
        private var id: Int? = null

        @SerializedName("title")
        @Expose
        private var title: String? = null

        @SerializedName("image")
        @Expose
        private var image: String? = null

        @SerializedName("avg_rating")
        @Expose
        private var avgRating: Float? = null

        @SerializedName("detail")
        @Expose
        private var detail: Detail? = null

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

        fun getImage(): String? {
            return image
        }

        fun setImage(image: String?) {
            this.image = image
        }

        fun getAvgRating(): Float? {
            return avgRating
        }

        fun setAvgRating(avgRating: Float?) {
            this.avgRating = avgRating
        }

        fun getDetail(): Detail? {
            return detail
        }

        fun setDetail(detail: Detail?) {
            this.detail = detail
        }

public class  Detail {

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
    private var allReviews: List<AllReview?>? = null

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

    fun getAllReviews(): List<AllReview?>? {
        return allReviews
    }

    fun setAllReviews(allReviews: List<AllReview?>?) {
        this.allReviews = allReviews
    }
}
    }

}