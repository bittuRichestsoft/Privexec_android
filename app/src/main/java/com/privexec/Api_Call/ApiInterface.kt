package com.privexec.Api_Call

import com.privexec.pojoclass.*
import com.privexec.pojoclass.countryList.CountryListModelClas
import com.privexec.pojoclass.piiShared.PiiSharedData
import com.privexec.pojoclass.piiTotalApps.PiiTotalAppsResponse
import com.privexec.pojoclass.reportAppByCategory.ReportAppByCategoryPojo
import com.privexec.pojoclass.reportAppByCategory.ReportLastTimeUsedPojo
import com.privexec.pojoclass.reportAppByCategory.ReportPotentiallyDataPojo
import com.privexec.pojoclass.totoalAppsPii.TotalAppsPiiSharedData
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {
    // Login  Api..............
    @FormUrlEncoded
    @POST("loginUser")
    fun getlogin(@FieldMap hashMap: HashMap<String, String>): Observable<Response<Login_registerPojo>>

    // Register  Api..............
    @FormUrlEncoded
    @POST("registerUser")
    fun getregister(@FieldMap hashMap: HashMap<String, String>): Observable<Response<Login_registerPojo>>

    // forgotPassword  Api..............
   /* @FormUrlEncoded
    @POST("forgotPassword")
    fun forgotPassword(@Field("email") email: String): Observable<Response<Login_registerPojo>>
*/
    // changePassword  Api..............
    @FormUrlEncoded
    @POST("changePassword")
    fun changePassword(@FieldMap hashMap: HashMap<String, String>): Observable<Response<UpdatePasswordRes>>


    @FormUrlEncoded
    @POST("ratings")
    fun ratings(@Field("session_id") sessionid: String): Observable<Response<All_ratingPojo>>


    @FormUrlEncoded
    @POST("get_single_rating")
    fun ratingsWithPkgName(@Field("session_id") sessionid: String,@Field("package_name") strPkgName: String): Observable<Response<All_ratingPojo>>

    @FormUrlEncoded
    @POST("rate_app")
    fun rate_app(@FieldMap hashMap: HashMap<String, String>): Observable<Response<SendRating_pojo>>


    @FormUrlEncoded
  //  @POST("apps-data")
     @POST("apps-data-clone")
  @Headers("Accept: application/json")
    //fun apps_data(@Query(value = "session_id", encoded = true)  sessionid: String, @Query(value = "list", encoded = true) title: String): Observable<Response<App_dataPojo>>
    fun apps_data(@Field("session_id")  sessionid: String, @Field("list") title: String): Observable<Response<App_dataPojo>>


    @FormUrlEncoded
     @POST("detail-for-single-app")
    @Headers("Accept: application/json")
     fun apps_dataForSingle(@Field("session_id")  sessionid: String, @Field("list") title: String): Observable<Response<App_dataPojo>>



    @FormUrlEncoded
    @POST("if-present-on-store")
    @Headers("Accept: application/json")
    fun checkLiveOrNotApp(@Field("session_id")  sessionid: String, @Field("list") title: String): Observable<Response<AppOnStoreOrNotPojo>>



    @FormUrlEncoded
     @POST("get_apps_clone")
    @Headers("Accept: application/json")
     fun getAllAppsData(@Field("session_id")  sessionid: String): Observable<Response<App_dataPojo>>

    /*Query*/
    @FormUrlEncoded
    @POST("logout")
    @Headers("Accept: application/json")
    fun logoutapi(@Field("session_id") sessionid: String): Observable<Response<App_dataPojo>>

    @FormUrlEncoded
    @POST("forgotPassword")
    @Headers("Accept: application/json")
    fun forgotPasswordApi(@Field("email") sessionid: String): Observable<Response<ForgotPassword>>

    @FormUrlEncoded
    @POST("one-app-data")
    @Headers("Accept: application/json")
    fun one_apps_data(@Field("session_id") sessionid: String, @Field("list") title: String): Observable<Response<App_dataPojo>>


    @FormUrlEncoded
    @POST("save-dsar-answers")
    @Headers("Accept: application/json")
    fun savedsaranswers(
        @Field("session_id") sessionid: String,   @Field(
            "app_id"
        ) app_id: String,  @Field(
            "email_template"
        ) email_template: String
    ): Observable<Response<SavedsaranswersPojo>>

    @GET("all/")  // it returning all countries list for overall world
    fun country_data(): Observable<Response<List<CountryListModelClas>>>


    @FormUrlEncoded
    @POST("touchid_status")
    @Headers("Accept: application/json")
    fun touchid_StatusData(@Field("session_id") sessionid: String, @Field("status") status: String): Observable<Response<App_dataPojo>>

    @FormUrlEncoded
    @POST("notify-not-used")
    @Headers("Accept: application/json")
    fun notifyNotUsed(@Field("session_id") sessionid: String, @Field("time") time: String):
            Observable<Response<App_dataPojo>>

    @FormUrlEncoded
    @POST("password-time")
    @Headers("Accept: application/json")
    fun notifyChangePassword(@Field("session_id") sessionid: String, @Field("time") time: String):
            Observable<Response<App_dataPojo>>

    @FormUrlEncoded
    @POST("autosave-dsar")
    @Headers("Accept: application/json")
    fun autosave_Dsar(@Field("session_id") sessionid: String, @Field("status") time: String):
            Observable<Response<App_dataPojo>>

    @FormUrlEncoded
    @POST("permissions")
    @Headers("Accept: application/json")
    fun get_PiiApiData(@Field("session_id") sessionid: String):
            Observable<Response<PiiSharedData>>


   // @FormUrlEncoded
    @POST("getTutorial")
    @Headers("Accept: application/json")
    fun getTutorialVideo():
            Observable<Response<TutorialVdoModel>>


    @FormUrlEncoded
    @POST("apps-pii-shared")
    @Headers("Accept: application/json")
    fun get_TotalAppsPiiApiData(@Field("session_id") sessionid: String):
            Observable<Response<TotalAppsPiiSharedData>>

@FormUrlEncoded
    @POST("pii-total-apps")
    @Headers("Accept: application/json")
    fun get_PiiTotalAppsData(@Field("session_id") sessionid: String):
            Observable<Response<PiiTotalAppsResponse>>

/*
    //new integrated
    @FormUrlEncoded
    @POST("Update_profile")
    fun getUpdateProfile(@FieldMap hashMap: HashMap<String, String>): Observable<Response<UpdateProfileRes>>*/

    @Multipart
    @POST("Update_profile")
    fun getUpdateProfile(@Part("session_id") session_id: RequestBody?,
                    @Part("name") user_name: RequestBody?,
                         @Part("email") email: RequestBody?,
                   /* @Part("phone") phone: RequestBody?,*/
                         @Part("date_of_birth") date_of_birth: RequestBody?,
                    @Part("country") country: RequestBody?,
                    @Part file : MultipartBody.Part?):Observable<Response<UpdateProfileRes>>


    @FormUrlEncoded
    @POST("Update_profile")
    fun getUpdateProfileCountry(@Field("session_id") sessionid: String, @Field("country") country: String): Observable<Response<UpdateProfileRes>>


    @FormUrlEncoded
    @POST("dsar-questions")
    fun getDsarQuestApi(@Field("session_id") sessionid: String): Observable<Response<AllDsarQuestionPojo>>


    @FormUrlEncoded
    @POST("get_email_template")
    fun getEmailTemplate(@Field("session_id") sessionid: String,@Field("response") response: String, @Field(
        "app_id"
    ) app_id: String ): Observable<Response<EmailTemplatePojo>>
/*jsonQuesAnsArray,appid*/


    @GET("country_list") //app api based country
    fun getNewAllCountries(): Observable<Response<NewAllCountryPojo>>


    @FormUrlEncoded
    @POST("single_rating")
    fun getSingleAppRating(@Field("session_id") sessionid: String,@Field("app_id") app_id: String): Observable<Response<SingleAppRatingPojo>>

    @FormUrlEncoded
    @POST("delete_app")
    fun deleteAppApi(@Field("session_id") sessionid: String,@Field("app_id") app_id: String): Observable<Response<DeleteAppPojo>>

    @FormUrlEncoded
    @POST("search_rating")
    fun searchAppForRate(@Field("session_id") sessionid: String, @Field("string") string: String): Observable<Response<All_ratingPojo>>

    @FormUrlEncoded
    @POST("appsByCategoryReport")
    fun reportOfAppCategoryApi(@Field("session_id") sessionid: String): Observable<Response<ReportAppByCategoryPojo>>

    @FormUrlEncoded
    @POST("lastTimeUsedAppsnew")  //lastTimeUsedAppsnew
    fun reportOfLastTimeUsedAppsApi(@Field("session_id") sessionid: String): Observable<Response<ReportLastTimeUsedPojo>>


    @FormUrlEncoded
    @POST("personalDataTypeCollectedReportNew")//personalDataTypeCollectedReportNew       /*old personalDataTypeCollectedReport*/
    fun reportOfPersonalPotentialDataApi(@Field("session_id") sessionid: String): Observable<Response<ReportPotentiallyDataPojo>>

}