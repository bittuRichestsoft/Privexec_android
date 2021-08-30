package com.privexec.pojoclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class App_dataPojo {
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
      /*  @SerializedName("id")
        @Expose
        var id: Int? = null
        @SerializedName("title")
        @Expose
        var title: String? = null
        @SerializedName("android_package_name")
        @Expose
        var androidPackageName: String? = null
        @SerializedName("type_app")
        @Expose
        var typeApp: Int? = null
        @SerializedName("android_link")
        @Expose
        var androidLink: Any? = null
        @SerializedName("link")
        @Expose
        var link: Any? = null
        @SerializedName("author")
        @Expose
        var author: Any? = null
        @SerializedName("android_author_link")
        @Expose
        var androidAuthorLink: Any? = null
        @SerializedName("image")
        @Expose
        var image: String? = null
        @SerializedName("android_desc")
        @Expose
        var androidDesc: String? = null
        @SerializedName("description")
        @Expose
        var description: String? = null
        @SerializedName("categories")
        @Expose
        var categories: String? = null
        @SerializedName("android_price")
        @Expose
        var androidPrice: Int? = null
        @SerializedName("ios_price")
        @Expose
        var iosPrice: Int? = null
        @SerializedName("android_screenshots")
        @Expose
        var androidScreenshots: Any? = null
        @SerializedName("ios_screenshots")
        @Expose
        var iosScreenshots: Any? = null
        @SerializedName("android_rating")
        @Expose
        var androidRating: Any? = null
        @SerializedName("android_last_updated")
        @Expose
        var androidLastUpdated: Any? = null
        @SerializedName("android_downloads")
        @Expose
        var androidDownloads: Any? = null
        @SerializedName("content_rating")
        @Expose
        var contentRating: Any? = null
        @SerializedName("privacy_policy")
        @Expose
        var privacyPolicy: String? = null
        @SerializedName("android")
        @Expose
        var android: String? = null
        @SerializedName("ios")
        @Expose
        var ios: Int? = null
        @SerializedName("status")
        @Expose
        var status: Int? = null
        @SerializedName("appeal_video")
        @Expose
        var appealVideo: String? = null
        @SerializedName("appeal_desc")
        @Expose
        var appealDesc: String? = null
        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null
        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null*/


        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("title")
        @Expose
        var title: String? = null

        @SerializedName("android_package_name")
        @Expose
        var androidPackageName: String? = null

          @SerializedName("app_full_title")
          @Expose
          var app_full_title: String? = null

        @SerializedName("firstupdate")
        @Expose
        var firstupdate: String? = null

        @SerializedName("lastupdate")
        @Expose
        var lastupdate: String? = null

        @SerializedName("ios_package_name")
        @Expose
        var iosPackageName: Any? = null

        @SerializedName("ios_id")
        @Expose
        var iosId: Any? = null

        @SerializedName("type_app")
        @Expose
        var typeApp: Int? = null

        @SerializedName("android_link")
        @Expose
        var androidLink: String? = null

        @SerializedName("ios_link")
        @Expose
        var iosLink: Any? = null

        @SerializedName("link")
        @Expose
        var link: Any? = null

        @SerializedName("author")
        @Expose
        var author: String? = null

        @SerializedName("android_author_link")
        @Expose
        var androidAuthorLink: String? = null

        @SerializedName("ios_author_link")
        @Expose
        var iosAuthorLink: Any? = null

        @SerializedName("image")
        @Expose
        var image: String? = null

        @SerializedName("android_desc")
        @Expose
        var androidDesc: String? = null

        @SerializedName("description")
        @Expose
        var description: Any? = null

        @SerializedName("categories")
        @Expose
        var categories: List<String>? = null

        @SerializedName("android_price")
        @Expose
        var androidPrice: Any? = null

        @SerializedName("ios_price")
        @Expose
        var iosPrice: Int? = null

        @SerializedName("android_screenshots")
        @Expose
        var androidScreenshots: List<String>? = null

        @SerializedName("ios_screenshots")
        @Expose
        var iosScreenshots: Boolean? = null

        @SerializedName("android_rating")
        @Expose
        var androidRating: Float? = null

        @SerializedName("ios_rating")
        @Expose
        var iosRating: Any? = null

       /* @SerializedName("android_last_updated")
        @Expose
        var androidLastUpdated: String? = null


          @SerializedName("android_first_installed")
          @Expose
          var androidFirstInstalled: String? = null
*/
        @SerializedName("android_downloads")
        @Expose
        var androidDownloads: String? = null

        @SerializedName("content_rating")
        @Expose
        var contentRating: String? = null

        @SerializedName("privacy_policy")
        @Expose
        var privacyPolicy: String? = null

        @SerializedName("android")
        @Expose
        var android: Int? = null

        @SerializedName("ios")
        @Expose
        var ios: Int? = null

        @SerializedName("status")
        @Expose
        var status: Int? = null

        @SerializedName("appeal_video")
        @Expose
        var appealVideo: Any? = null

        @SerializedName("appeal_desc")
        @Expose
        var appealDesc: Any? = null

        @SerializedName("permissions")
        @Expose
        var permissions: List<String>? = null

        @SerializedName("pii_shared")
        @Expose
        var piiShared: Int? = null

          @SerializedName("email")
          @Expose
          var email: String? = null


        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null



        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null

        @SerializedName("sponsored")
        @Expose
        var sponsored: Int? = null

          @SerializedName("personalDataType")
          @Expose
          var personalDataType: List<PersonalDataType>? = null
      }
    class PersonalDataType {
        @SerializedName("appCatID")
        @Expose
        var appCatID: Int? = null

        @SerializedName("appCategory_id")
        @Expose
        var appCategoryId: String? = null

        @SerializedName("platform")
        @Expose
        var platform: String? = null

        @SerializedName("appCategoryDescription")
        @Expose
        var appCategoryDescription: String? = null

        @SerializedName("appCateexamples")
        @Expose
        var appCateexamples: String? = null

        @SerializedName("personalDatatypesID")
        @Expose
        var personalDatatypesID: Int? = null

        @SerializedName("personalDatatypesName")
        @Expose
        var personalDatatypesName: String? = null

        @SerializedName("personalDatatypesAbout")
        @Expose
        var personalDatatypesAbout: String? = null

        @SerializedName("personalDatatypesDetail")
        @Expose
        var personalDatatypesDetail: String? = null

        @SerializedName("groupID")
        @Expose
        var groupID: Int? = null

        @SerializedName("groupName")
        @Expose
        var groupName: String? = null
    }
    /*class Result{
        @SerializedName("id")
        @Expose
        private var id: Int? = null
        @SerializedName("user_id")
        @Expose
        private var userId: Int? = null
        @SerializedName("name")
        @Expose
        private var name: String? = null
        @SerializedName("version_name")
        @Expose
        private var versionName: String? = null
        @SerializedName("targetversion")
        @Expose
        private var targetversion: String? = null
        @SerializedName("firstupdate")
        @Expose
        private var firstupdate: String? = null
        @SerializedName("lastupdate")
        @Expose
        private var lastupdate: String? = null
        @SerializedName("image")
        @Expose
        private var image: Any? = null
        @SerializedName("package_name")
        @Expose
        private var packageName: String? = null
        @SerializedName("created_at")
        @Expose
        private var createdAt: String? = null
        @SerializedName("updated_at")
        @Expose
        private var updatedAt: String? = null
        @SerializedName("meta")
        @Expose
        private var meta: Meta? = null
        @SerializedName("dsar_appeal_video")
        @Expose
        private var dsarAppealVideo: List<String>? = null
        @SerializedName("dsar_appeal_desc")
        @Expose
        private var dsarAppealDesc: List<String>? = null

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

        fun getName(): String? {
            return name
        }

        fun setName(name: String) {
            this.name = name
        }

        fun getVersionName(): String? {
            return versionName
        }

        fun setVersionName(versionName: String) {
            this.versionName = versionName
        }

        fun getTargetversion(): String? {
            return targetversion
        }

        fun setTargetversion(targetversion: String) {
            this.targetversion = targetversion
        }

        fun getFirstupdate(): String? {
            return firstupdate
        }

        fun setFirstupdate(firstupdate: String) {
            this.firstupdate = firstupdate
        }

        fun getLastupdate(): String? {
            return lastupdate
        }

        fun setLastupdate(lastupdate: String) {
            this.lastupdate = lastupdate
        }

        fun getImage(): Any? {
            return image
        }

        fun setImage(image: Any) {
            this.image = image
        }

        fun getPackageName(): String? {
            return packageName
        }

        fun setPackageName(packageName: String) {
            this.packageName = packageName
        }

        fun getCreatedAt(): String? {
            return createdAt
        }

        fun setCreatedAt(createdAt: String) {
            this.createdAt = createdAt
        }

        fun getUpdatedAt(): String? {
            return updatedAt
        }

        fun setUpdatedAt(updatedAt: String) {
            this.updatedAt = updatedAt
        }

        fun getMeta(): Meta? {
            return meta
        }

        fun setMeta(meta: Meta) {
            this.meta = meta
        }

        fun getDsarAppealVideo(): List<String>? {
            return dsarAppealVideo
        }

        fun setDsarAppealVideo(dsarAppealVideo: List<String>) {
            this.dsarAppealVideo = dsarAppealVideo
        }

        fun getDsarAppealDesc(): List<String>? {
            return dsarAppealDesc
        }

        fun setDsarAppealDesc(dsarAppealDesc: List<String>) {
            this.dsarAppealDesc = dsarAppealDesc
        }

class Meta{
    @SerializedName("id")
    @Expose
    private var id: String? = null
    @SerializedName("url")
    @Expose
    private var url: String? = null

    @SerializedName("image")
    @Expose
    private var image: String? = null

    @SerializedName("title")
    @Expose
    private var title: String? = null
    @SerializedName("author")
    @Expose
    private var author: String? = null
    @SerializedName("author_link")
    @Expose
    private var authorLink: String? = null
    @SerializedName("categories")
    @Expose
    private var categories: List<String>? = null
    @SerializedName("price")
    @Expose
    private var price: Any? = null
    @SerializedName("screenshots")
    @Expose
    private var screenshots: List<String>? = null
    @SerializedName("description")
    @Expose
    private var description: String? = null
    @SerializedName("description_html")
    @Expose
    private var descriptionHtml: String? = null
    @SerializedName("rating")
    @Expose
    private var rating: Double? = null
    @SerializedName("votes")
    @Expose
    private var votes: Int? = null
    @SerializedName("last_updated")
    @Expose
    private var lastUpdated: String? = null
    @SerializedName("size")
    @Expose
    private var size: Any? = null
    @SerializedName("downloads")
    @Expose
    private var downloads: String? = null
    @SerializedName("version")
    @Expose
    private var version: Any? = null
    @SerializedName("supported_os")
    @Expose
    private var supportedOs: Any? = null
    @SerializedName("content_rating")
    @Expose
    private var contentRating: String? = null
    @SerializedName("whatsnew")
    @Expose
    private var whatsnew: String? = null
    @SerializedName("video_link")
    @Expose
    private var videoLink: Any? = null
    @SerializedName("video_image")
    @Expose
    private var videoImage: Any? = null

    fun getId(): String {
        return id!!
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getUrl(): String? {
        return url
    }

    fun setUrl(url: String) {
        this.url = url
    }

    fun getImage(): String {
        return this!!.image.toString()
    }

    fun setImage(image: String) {
        this.image = image
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getAuthor(): String? {
        return author
    }

    fun setAuthor(author: String) {
        this.author = author
    }

    fun getAuthorLink(): String? {
        return authorLink
    }

    fun setAuthorLink(authorLink: String) {
        this.authorLink = authorLink
    }

    fun getCategories(): List<String>? {
        return categories
    }

    fun setCategories(categories: List<String>) {
        this.categories = categories
    }

    fun getPrice(): Any? {
        return price
    }

    fun setPrice(price: Any) {
        this.price = price
    }

    fun getScreenshots(): List<String>? {
        return screenshots
    }

    fun setScreenshots(screenshots: List<String>) {
        this.screenshots = screenshots
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun getDescriptionHtml(): String? {
        return descriptionHtml
    }

    fun setDescriptionHtml(descriptionHtml: String) {
        this.descriptionHtml = descriptionHtml
    }

    fun getRating(): Double? {
        return rating
    }

    fun setRating(rating: Double?) {
        this.rating = rating
    }

    fun getVotes(): Int? {
        return votes
    }

    fun setVotes(votes: Int?) {
        this.votes = votes
    }

    fun getLastUpdated(): String? {
        return lastUpdated
    }

    fun setLastUpdated(lastUpdated: String) {
        this.lastUpdated = lastUpdated
    }

    fun getSize(): Any? {
        return size
    }

    fun setSize(size: Any) {
        this.size = size
    }

    fun getDownloads(): String? {
        return downloads
    }

    fun setDownloads(downloads: String) {
        this.downloads = downloads
    }

    fun getVersion(): Any? {
        return version
    }

    fun setVersion(version: Any) {
        this.version = version
    }

    fun getSupportedOs(): Any? {
        return supportedOs
    }

    fun setSupportedOs(supportedOs: Any) {
        this.supportedOs = supportedOs
    }

    fun getContentRating(): String? {
        return contentRating
    }

    fun setContentRating(contentRating: String) {
        this.contentRating = contentRating
    }

    fun getWhatsnew(): String? {
        return whatsnew
    }

    fun setWhatsnew(whatsnew: String) {
        this.whatsnew = whatsnew
    }

    fun getVideoLink(): Any? {
        return videoLink
    }

    fun setVideoLink(videoLink: Any) {
        this.videoLink = videoLink
    }

    fun getVideoImage(): Any? {
        return videoImage
    }

    fun setVideoImage(videoImage: Any) {
        this.videoImage = videoImage
    }
}
}*/
}