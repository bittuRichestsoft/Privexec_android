package com.privexec.fragmnet

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.ParseException
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.lee_da_hang_pte_ltd.utills.CSPreferences
import com.lee_da_hang_pte_ltd.utills.Global_utility
import com.privexec.Api_Call.MyApp_interface
import com.privexec.DBase.AllAppDB
import com.privexec.R
import com.privexec.activity.Home_Activity
import com.privexec.activity.Home_Activity.Companion.jsonarray
import com.privexec.activity.Home_Activity.Companion.packageList2
import com.privexec.activity.Saplash
import com.privexec.adapter.Myapp_adapter
import com.privexec.databinding.FragmentMyAppBinding
import com.privexec.pojoclass.App_dataPojo
import com.privexec.utills.UsageStateUtils
import com.privexec.utills.Utils
import com.privexec.viewmodel.MyAppVM
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.dialog_alert.btnYes
import kotlinx.android.synthetic.main.dialog_choose_filter.*
import kotlinx.android.synthetic.main.fragment_my__app.*
import kotlinx.android.synthetic.main.header.*
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [My_App.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [My_App.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class My_App : Fragment(), MyApp_interface, View.OnClickListener {

    override fun getmyapp(list: List<App_dataPojo.Result>) {
        ratinglist.clear()
        ratinglist.addAll(list)
        myappbinding!!.myappRecycle.adapter!!.notifyDataSetChanged()
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var myapp_adapter: /* MyAppAdapPaginate*/ Myapp_adapter? = null
    private var views: View? = null
    private var myappbinding: FragmentMyAppBinding? = null
    var TAG = "My_AppFrag "
    private var PAGE_START = 0
    private var isLoadingChk = false
    private var isLastPageChk = false
    var allAppDB: AllAppDB? = null;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private var TOTAL_PAGES = 0
    private var currentPage = PAGE_START
    var choosedFilterTmVal=""

    internal var packageManagers: PackageManager? = null
    internal var packageInfo: PackageInfo? = null
    private var compositeDisposable: CompositeDisposable? = null
    var staticPackageLst = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var ratinglist = arrayListOf<App_dataPojo.Result>()

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myappbinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_my__app, container, false)
        views = myappbinding!!.root
        compositeDisposable = CompositeDisposable()
        //Home_Activity.toolbar.relTitleLayout.tv  // = resources.getString(R.string.myapps)
        packageManagers = activity!!.packageManager
        Log.d(TAG + "", "jsonarray.size()=" + jsonarray.size())
        //var packageList = activity!!.packageManager.g   Log.d("kjkjkjkj_size","ssssss01pssssssss"+getarray.size)etInstalledPackages(PackageManager.GET_PERMISSIONS)
        //myapp_adapter = Myapp_adapter(activity!!)
        var layotManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        myappbinding!!.myappRecycle.setLayoutManager(layotManager)
        myappbinding!!.myappRecycle.setHasFixedSize(true)
        myappbinding!!.myappRecycle.setItemAnimator(DefaultItemAnimator())
        Global_utility.hideDialog(activity!!)
        //   myappbinding!!.myappRecycle.adapter=myapp_adapter
        /*  myappbinding!!.myappRecycle.addOnScrollListener(  object: PaginationListener(layotManager)
          {
              override fun   loadMoreItems()  {
                  callLoadMoreApps()
              }
              override fun isLastPage():Boolean {
                  return isLastPageChk;
              }
              override fun isLoading():Boolean {
                  return isLoadingChk;
              }
              override fun getTotalPageCount(): Int {
                  return TOTAL_PAGES
              }
          } );*/


        myappbinding!!.swpRefLyt.setOnRefreshListener{
            myappbinding!!.swpRefLyt.isRefreshing=false

            callAndCheckApi()
        }

        callAndCheckApi()

        // Inflate the layout for this fragment
        return views
    }


    private fun showFilterDialog( ) {
       // Log.e("preference","showFilterDialog " )
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_choose_filter)

        dialog.rdBtn_all.setOnClickListener {
            choosedFilterTmVal=""
//            dialog.rdBtn_all.isChecked=false

            dialog.rdBtn_used3mnths.isChecked=false
            dialog.rdBtn_notUsed3mnths.isChecked=false
            dialog.rdBtn_notUsed6mnths.isChecked=false
            dialog.rdBtn_notUsed12mnths.isChecked=false
        }

        dialog.rdBtn_used3mnths.setOnClickListener {
            choosedFilterTmVal="used_3_months"
            dialog.rdBtn_all.isChecked=false
          //  dialog.rdBtn_used3mnths.isChecked=false
            dialog.rdBtn_notUsed3mnths.isChecked=false
            dialog.rdBtn_notUsed6mnths.isChecked=false
            dialog.rdBtn_notUsed12mnths.isChecked=false
        }
        dialog.rdBtn_notUsed3mnths.setOnClickListener {
            choosedFilterTmVal="not_used_3_months"
            dialog.rdBtn_all.isChecked=false
            dialog.rdBtn_used3mnths.isChecked=false
           // dialog.rdBtn_notUsed3mnths.isChecked=false
            dialog.rdBtn_notUsed6mnths.isChecked=false
            dialog.rdBtn_notUsed12mnths.isChecked=false
        }

        dialog.rdBtn_notUsed6mnths.setOnClickListener {
            choosedFilterTmVal="not_used_6_months"
            dialog.rdBtn_all.isChecked=false
            dialog.rdBtn_used3mnths.isChecked=false
              dialog.rdBtn_notUsed3mnths.isChecked=false
           // dialog.rdBtn_notUsed6mnths.isChecked=false
            dialog.rdBtn_notUsed12mnths.isChecked=false
        }

        dialog.rdBtn_notUsed12mnths.setOnClickListener {
            choosedFilterTmVal="not_used_12_months"
            dialog.rdBtn_all.isChecked=false
            dialog.rdBtn_used3mnths.isChecked=false
              dialog.rdBtn_notUsed3mnths.isChecked=false
            dialog.rdBtn_notUsed6mnths.isChecked=false
            //dialog.rdBtn_notUsed12mnths.isChecked=false
        }

        dialog.btnYes.setOnClickListener {
           if(choosedFilterTmVal.equals(""))
           {

           }
            dialog.dismiss()
        }
          dialog.show()
    }


    fun getAppIconByPackageName(ApkTempPackageName: String?): Drawable? {
        val drawable: Drawable?
        drawable = try {
            activity!!.applicationContext.getPackageManager().getApplicationIcon(ApkTempPackageName)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            ContextCompat.getDrawable(activity!!.applicationContext, R.mipmap.ic_launcher)
        }
        return drawable
    }

    fun GetAppName(ApkPackageName: String?): String? {
        var Name = ""
        val applicationInfo: ApplicationInfo?
        val packageManager: PackageManager = activity!!.applicationContext.getPackageManager()
        try {
            applicationInfo = packageManager.getApplicationInfo(ApkPackageName, 0)
            if (applicationInfo != null) {
                Name = packageManager.getApplicationLabel(applicationInfo) as String
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        Log.e(TAG,"GetAppName   Name="+Name)
        return Name
    }

  /*  private fun callLoadMoreApps() {
        var strSplittedPaginateArr = JsonArray()

        if (jsonarray != null) {
            Log.d(TAG + " jsonarray.toString()", jsonarray.toString())
            Log.d(TAG + " jsonarray.size()", jsonarray.size().toString())
            // var strd = "[{\"image\":\"android.graphics.drawable.AdaptiveIconDrawable@eb51606\",\"name\":\"com.android.cts.priv.ctsshim\",\"package_name\":\"com.android.cts.priv.ctsshim\",\"version_name\":\"8.1.0-4396705\",\"targetversion\":\"24\",\"firstupdate\":\"04/11/2019 04:06:48\",\"lastupdate\":\"04/11/2019 04:06:48\"},{\"image\":\"android.graphics.drawable.AdaptiveIconDrawable@e7c8c92\",\"name\":\"YouTube\",\"package_name\":\"com.google.android.youtube\",\"version_name\":\"13.15.61\",\"targetversion\":\"28\",\"firstupdate\":\"04/11/2019 04:06:53\",\"lastupdate\":\"04/11/2019 04:06:53\"}]"

            PAGE_START = currentPage
            if (currentPage < TOTAL_PAGES)
                currentPage = currentPage + 20
            else {
                currentPage = PAGE_START + (TOTAL_PAGES - currentPage)

            }

            for (i in PAGE_START..currentPage) {
                try {
                    strSplittedPaginateArr.add(jsonarray.get(i))

                } catch (excep: Exception) {
                    Log.e(TAG, "excep loadMoreItems =" + excep.toString())
                }
            }


        }

        if (Global_utility.isNetworkConnected(activity!!)) {
              Log.d(
                TAG,
                "strSplittedPaginateArr size = ${strSplittedPaginateArr.size()
                    .toString()} strSplittedPaginateArr=" + strSplittedPaginateArr.toString()
            )
            if (strSplittedPaginateArr.size() > 0)
                appdata(
                    CSPreferences.readString(
                        activity!!,
                        "session_id"
                    )!!,*//*jsonarray.toString()*//*strSplittedPaginateArr.toString(), activity!!, "1"
                )
        } else {
            Global_utility.showtost(activity!!, resources.getString(R.string.internet_connection))
        }


    }*/

    private fun callAndCheckApi() {
        if (Global_utility.isNetworkConnected(activity!!)) {
            if (CSPreferences.readString(activity!!, "push_active_app_status") == "1") {

              setSimpleAppsInAdap()
            //  getAllInstalledApps()
            } else {
                getMyAddedAppsLst()
            }
        } else {
            Global_utility.showtost(activity!!, resources.getString(R.string.internet_connection))
        }

    }

    private fun getAllInstalledApps() {
        allAppDB = AllAppDB(context)
        var arrayLiAllPro = ArrayList<String>()

        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED

        val resolveInfoList: List<ResolveInfo> =
            context!!.getPackageManager().queryIntentActivities(intent, 0)
        if (allAppDB!!.allCartProductCount != resolveInfoList.size) {
            allAppDB!!.deleteAllRecord()
            var indx = 0
            for (resolveInfo in resolveInfoList) {
                val activityInfo = resolveInfo.activityInfo


var appName=""+GetAppName (activityInfo.applicationInfo.packageName)
                var appIconDr=getAppIconByPackageName(activityInfo.applicationInfo.packageName)
        Log.e(
                    TAG,
                    "appIndex ${indx++} =" + activityInfo.applicationInfo.packageName  +"    Name="+appName+"  icon="+appIconDr
                )
                if (!isSystemPackage2(resolveInfo )) {
                    arrayLiAllPro!!.add(activityInfo.applicationInfo.packageName)
                    allAppDB!!.addItemInDB(""+appName, activityInfo.applicationInfo.packageName)
                }
            }
        } else {
            arrayLiAllPro = allAppDB!!.getAllCartProductList()
        }



    }
    fun isSystemPackage2(resolveInfo: ResolveInfo): Boolean {
        return resolveInfo.activityInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }
    private fun setSimpleAppsInAdap() {
/*        if (Global_utility.isNetworkConnected(activity!!)) {
           checkAppLiveOrNot(
                CSPreferences.readString(
                    activity!!,
                    "session_id"
                )!!,jsonarray.toString()
               ,
                activity!!

            )
        } else {
            Global_utility.showtost(
                activity!!,
                resources.getString(R.string.internet_connection)
            )
        }*/
        ratinglist = arrayListOf<App_dataPojo.Result>()
        ratinglist.clear()
var packageLst=""

        for (i in 0..(jsonarray!!.size()  - 1)) {


            var jsObject= jsonarray.get(i)
            var package_name =   jsObject.asJsonObject.getAsJsonPrimitive("package_name")
            var app_name = ""  +jsObject.asJsonObject.getAsJsonPrimitive("app_name")
            var lastupdate = "" +jsObject.asJsonObject.getAsJsonPrimitive("lastupdate")
            var app_icon_img = ""// +it.getResult()!!.get(i).app_img
            var  firstupdate= ""  +jsObject.asJsonObject.getAsJsonPrimitive("firstupdate")

            var resultObj = App_dataPojo.Result()
            resultObj.typeApp = 1
            resultObj.androidPackageName = "" + package_name
            resultObj.firstupdate = "" + firstupdate

            resultObj.lastupdate = ""+lastupdate

//             resultObj.androidPackageName=""+package_name
            resultObj.title= "" +app_name
            resultObj.image= ""
            packageLst=packageLst+" <<>> "+package_name
            Log.e(
                TAG,
                "setSimpleAppsInAdap    title=${resultObj.title}    package_name=$package_name    firstupdate=$firstupdate    lastupdate=$lastupdate"
            )
          //  if(getLastDateCalculation(lastupdate) )
            ratinglist.add(resultObj)
        }
        Handler().postDelayed( Runnable {
            tv_headerTitle.text=("My Apps ("+(ratinglist!!.size )+")"  )
        },100        )

                 Collections.sort(ratinglist!!, object : Comparator<App_dataPojo.Result> {
                    override fun compare(
                        lhs: App_dataPojo.Result,
                        rhs: App_dataPojo.Result
                    ): Int {
                        return lhs.title.toString().toLowerCase().compareTo(rhs.title.toString().toLowerCase())
                    }
                })

        Log.e(
            TAG,
            "setSimpleAppsInAdap_packageLst=${packageLst} ")
        myapp_adapter = Myapp_adapter(activity!!, packageList23, packageManagers!!, ratinglist!!)
        myappbinding!!.myappRecycle.adapter = myapp_adapter
        myapp_adapter!!.notifyDataSetChanged()

                appdata(
                    CSPreferences.readString(
                        activity!!,
                        "session_id"
                    )!!,jsonarray.toString() , activity!!, "1"
                )


    }
/*fun   getLastDateCalculation( lastTmUsesDate:String ):Boolean
    {
//all   used_3_months  not_used_3_months not_used_6_months not_used_12_months
        var  boolChk=true

        if(   choosedFilterTmVal.equals("used_3_months") )
        {
            return boolChk
        }


        try {
            val format = SimpleDateFormat("MM/dd/yyyy hh:mm:ss")
            val date: Date = format.parse(lastTmUsesDate)
            System.out.println(date)
            val c = Calendar.getInstance()
            c.time = date
            c.add(Calendar.MONTH, -tmVal)
        } catch (e: ParseException) {
            e.printStackTrace()
        }





    }*/

    private fun getMyAddedAppsLst() {
        val myappViewmodel = ViewModelProviders.of(this).get(MyAppVM::class.java)
        // Global_utility.hideDialog(activity!!)
        myappbinding!!.nodatfound.visibility = View.VISIBLE
        myappViewmodel.getMyAddedApps(
            "" + CSPreferences.readString(activity!!, "session_id"),
            activity!!,
            compositeDisposable
        )!!.observe(this, Observer {
            //  Global_utility.hideDialog(activity!!)
            Log.d(TAG + "getMyAddedAppsLst()", "api getStatus : " + it.getStatus()!!)

            if (it.getStatus()!!.equals(200)) {
                // CSPreferences.putString(activity!!, "all_app_data",WebAPicall.gson.toJson(it.getResult()))
                Log.d(TAG + "getMyAddedAppsLst()", "api result : " + it.getResult()!!.size)
                Collections.sort(it.getResult()!!, object : Comparator<App_dataPojo.Result> {
                    override fun compare(lhs: App_dataPojo.Result, rhs: App_dataPojo.Result): Int {
                        return lhs.title.toString().toLowerCase().compareTo(rhs.title.toString().toLowerCase())
                    }
                })
                tv_headerTitle.text=("My Apps ("+(it.getResult()!!.size)+")"  )

                myapp_adapter =
                    Myapp_adapter(activity!!, packageList23, packageManagers!!, it.getResult()!!)
                //     myapp_adapter =MyAppAdapPaginate(it.getResult()!!,activity!! )
                myappbinding!!.myappRecycle.adapter = myapp_adapter
                myapp_adapter!!.notifyDataSetChanged()
                if (it.getResult()!!.size > 0) {
                    myappbinding!!.nodatfound.visibility = View.GONE

                }
                /* it.getMessage()?.let { it1 -> Global_utility.showtost(context, it1) }*/
            }
            else if (it.getStatus()!!.equals(403)) {
                    CSPreferences.putString(activity!!, "session_id", "")
                    val intent = Intent(activity!!, Saplash::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    activity!!.overridePendingTransition(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                    )
                    activity!!.finish()
                }
            else {
                    // myappInterface.getmyapp(it.getResult()!!)
                    it.getMessage()?.let { it1 -> Global_utility.showtost(activity!!, it1) }
                }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_headerTitle.setText("My Apps")
        img_headerback.setOnClickListener(this)
        iv_question.setOnClickListener(this)
        fl_fragMyapps.setOnClickListener(this)
        tv_sendEmail.visibility=View.VISIBLE
        tv_sendEmail.textSize=10.0f
        tv_sendEmail.text=("Filter")
        tv_sendEmail.setOnClickListener(this)
        tv_sendEmail.visibility=View.GONE
        iv_question.visibility=View.VISIBLE
        iv_question.setImageResource(R.drawable.refresh_img);
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            My_App().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun isSystemPackage(resolveInfo: ResolveInfo): Boolean {
        return resolveInfo.activityInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }

    private fun appdata(
        sessionid: String,
        jsonArray: String,
        context: Context,
        paginateChk: String
    ) {
        val myappViewmodel = ViewModelProviders.of(this).get(MyAppVM::class.java)
        Log.d(TAG + " appdata_jsonArray ", jsonArray)
        Global_utility.hideDialog(activity!!)
        myappViewmodel.appdata(sessionid, jsonArray, context, compositeDisposable,false)!!
            .observe(this, Observer {
                Global_utility.hideDialog(activity!!)
                Log.d(TAG + "appdata()", "api getStatus : " + it.getStatus()!!)

                if (it.getStatus()!!.equals(200)) {
                    callAndCheckApi()
                    /* Log.d(
                        TAG + "appdata()",
                        "api result : " + it.getResult()!!.size + "     paginateChk=" + paginateChk
                    )
                    Collections.sort(it.getResult()!!, object : Comparator<App_dataPojo.Result> {
                        override fun compare(
                            lhs: App_dataPojo.Result,
                            rhs: App_dataPojo.Result
                        ): Int {
                            return lhs.title.toString().toLowerCase().compareTo(rhs.title.toString().toLowerCase())
                        }
                    })*/
                   /* myapp_adapter = Myapp_adapter(
                        activity!!,
                        packageList23,
                        packageManagers!!,
                        it.getResult()!!
                    )
                    myappbinding!!.myappRecycle.adapter = myapp_adapter*/

                } else
                    if (it.getStatus()!!.equals(403)) {
                        CSPreferences.putString(context, "session_id", "")
                        val intent = Intent(activity!!, Saplash::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        activity!!.overridePendingTransition(
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                        )
                        activity!!.finish()
                    } else {
                        // myappInterface.getmyapp(it.getResult()!!)
                        it.getMessage()?.let { it1 -> Global_utility.showtost(context, it1) }
                    }
            })
    }


    /*private fun checkAppLiveOrNot(
        sessionid: String,
        jsonArray: String,
        context: Context
    ) {
        val myappViewmodel = ViewModelProviders.of(this).get(MyAppVM::class.java)
        Log.d(TAG + " appdata_jsonArray ", jsonArray)

        myappViewmodel.checkAppLiveOrNot(sessionid, jsonArray, context, compositeDisposable)!!
            .observe(this, Observer {
                Global_utility.hideDialog(activity!!)
                Log.d(TAG + "appdata()", "api getStatus : " + it.getStatus()!!)

                if (it.getStatus()!!.equals(200)) {
                    Log.d(
                        TAG + "appdata()",
                        "api result : " + it.getResult()!!.size
                    )

                    ratinglist = arrayListOf<App_dataPojo.Result>()
var packageLst=""
                    for (i in 0..(it.getResult()!!.size  - 1)) {


                        var resultObj = App_dataPojo.Result()
                        var package_name = "" + it.getResult()!!.get(i).packageName
                        var firstupdate = "" + it.getResult()!!.get(i).firstupdate
                        var lastupdate = "" +it.getResult()!!.get(i).lastupdate
                        var app_icon_img = "" +it.getResult()!!.get(i).app_img
                        var app_name = ""  +it.getResult()!!.get(i).app_name


                        resultObj.typeApp = 1
                        resultObj.androidPackageName = "" + package_name
                        resultObj.androidFirstInstalled = "" + firstupdate
                        resultObj.androidLastUpdated = "" + lastupdate
//             resultObj.androidPackageName=""+package_name
                        resultObj.title= "" +app_name
                        resultObj.image= "" +app_icon_img
                        packageLst=packageLst+" <<>> "+package_name
                        Log.e(
                            TAG,
                            "setSimpleAppsInAdap    title=${resultObj.title}    package_name=$package_name    firstupdate=$firstupdate    lastupdate=$lastupdate"
                        )

                        ratinglist.add(resultObj)

                    }
                    Log.e(
                        TAG,
                        "setSimpleAppsInAdap_packageLst=${packageLst} ")
                    myapp_adapter = Myapp_adapter(activity!!, packageList23, packageManagers!!, ratinglist!!)
                    myappbinding!!.myappRecycle.adapter = myapp_adapter
                    myapp_adapter!!.notifyDataSetChanged()

           *//*         Collections.sort(it.getResult()!!, object : Comparator<AppOnStoreOrNotPojo.Result> {
                        override fun compare(
                            lhs: AppOnStoreOrNotPojo.Result,
                            rhs: AppOnStoreOrNotPojo.Result
                        ): Int {
                            return lhs.title.toString().compareTo(rhs.title.toString())
                        }
                    })*//*


                } else
                    if (it.getStatus()!!.equals(403)) {
                        CSPreferences.putString(context, "session_id", "")
                        val intent = Intent(activity!!, Saplash::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        activity!!.overridePendingTransition(
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                        )
                        activity!!.finish()
                    } else {
                        // myappInterface.getmyapp(it.getResult()!!)
                        it.getMessage()?.let { it1 -> Global_utility.showtost(context, it1) }
                    }
            })
    }
 */
    val packageList23 = ArrayList<PackageInfo>()

    inner class someTask() : AsyncTask<Void, Void, String>() {
        var progressDialog: ProgressDialog? = null

        @SuppressLint("WrongThread")
        override fun doInBackground(vararg params: Void?): String? {
            Home_Activity.resolvePkgLst =GetAllInstalledApkInfo()
            Log.d("sadsdad", CSPreferences.readString(activity!! , "session_id")  +"     resolvePkgLst="+ Home_Activity.resolvePkgLst.size )
            jsonarray = JsonArray()
            Home_Activity.staticPackageLst.clear()
            Home_Activity.staticPackageLst.addAll(getResources().getStringArray(R.array.static_packages))
            packageList2 =
                packageManagers!!.getInstalledPackages(PackageManager.COMPONENT_ENABLED_STATE_ENABLED  /*and PackageManager.COMPONENT_ENABLED_STATE_DEFAULT */)
                        as ArrayList<PackageInfo>

            /*    var tempArrLSt = ArrayList<String>()
              tempArrLSt.clear()
                for (position in 0..staticPackageLst.size - 1) {

                    if(resolvePkgLst.contains(staticPackageLst.get(position)))
                    {
                        tempArrLSt.add(staticPackageLst.get(position))
                    }
                }

                staticPackageLst.clear()

                staticPackageLst.addAll(tempArrLSt)*/

            for (position in 0..packageList2.size - 1) {
                if (!isSystemPackage(packageList2[position])  && (! Home_Activity.staticPackageLst.contains( packageList2[position].applicationInfo.packageName))   && Home_Activity.resolvePkgLst.contains(packageList2[position].applicationInfo.packageName)) {
                    var jsonobject = JsonObject()
                    val firstupdate_ = DateFormat.format(
                        "MM/dd/yyyy hh:mm:ss",
                        Date(packageList2[position].firstInstallTime)
                    ).toString()
                    /*     val lastupdate_ = DateFormat.format(
                       "MM/dd/yyyy hh:mm:ss",
                       Date(packageList2[position].lastUpdateTime)
                   ).toString()*/
                    var strPkgName="" + packageList2[position].applicationInfo.packageName


                    jsonobject.addProperty(
                        "package_name",strPkgName

                    )
                    var strAppName="" + GetAppName(packageList2[position].applicationInfo.packageName)
                    jsonobject.addProperty(
                        "app_name", strAppName

                    )
                    jsonobject.addProperty("firstupdate", firstupdate_)
                    //  jsonobject.addProperty("app_img", "" + getBase64Icon(packageList2[position].applicationInfo.packageName))

                    /*  var checkForAppLive =
                  isAppLive("" + packageList2[position].applicationInfo.packageName)*/

                    /* if(checkForAppLive)
               {*/

                    var lastLaunchedTm = "0"
                    if (firstupdate_ != null && firstupdate_ != "") {
                        lastLaunchedTm = firstupdate_
                    }
                    var strLastTmLaunched = UsageStateUtils.getLastLaunchedTime(
                        activity!!,
                        packageList2[position].applicationInfo.packageName
                    )
                    Log.e(
                        TAG,
                        "" + packageList2[position].applicationInfo.packageName + "<<>> firstInstallTime="+packageList2[position].firstInstallTime +" <<>> strLastTmLaunched="+ strLastTmLaunched
                    )


                    if (strLastTmLaunched!=null &&  strLastTmLaunched.length<=4  && firstupdate_ != null && firstupdate_ != "")
                    {
                        strLastTmLaunched=firstupdate_
                    }
                    if (strLastTmLaunched != null) {
                        lastLaunchedTm = "" + strLastTmLaunched
                        Log.e(TAG,""+packageList2[position].applicationInfo.packageName+" inIf strLastTmLaunched="+strLastTmLaunched )
                    }
                    try {
                        if (lastLaunchedTm.toLong() < (packageList2[position].firstInstallTime) )
                            lastLaunchedTm=packageList2[position].firstInstallTime.toString()
                    }
                    catch (excep:java.lang.Exception)
                    {

                    }
                    try {
                        lastLaunchedTm = "" + DateFormat.format(
                            "MM/dd/yyyy hh:mm:ss",
                            Date(
                                lastLaunchedTm.toLong()
                            )
                        )
                        Log.e(TAG,""+packageList2[position].applicationInfo.packageName+" inTry strLastTmLaunched="+strLastTmLaunched )
                    } catch (excep: Exception) {
                        Log.e(TAG + "", "excep=" + excep.toString())
                    }
                    Log.e(
                        TAG,
                        "latest= " + packageList2[position].applicationInfo.packageName + "<<>> firstInstallTime="+packageList2[position].firstInstallTime +" <<>> strLastTmLaunched="+ strLastTmLaunched
                    )
                    jsonobject.addProperty("lastupdate", "" + lastLaunchedTm)
                    Log.e(
                        "" + TAG,
                        "pkg=" + packageList2[position].applicationInfo.packageName + " <<>> tm=" + strLastTmLaunched
                    )

                    val ai: ApplicationInfo = packageManagers!!.getApplicationInfo(
                        packageList2[position].applicationInfo.packageName,
                        0
                    )

                    Log.e("" + TAG, "flagChk =" + packageList2[position].applicationInfo.flags)
                    Log.e("" + TAG, "flagChk2 =" + (ai.flags and ApplicationInfo.FLAG_SYSTEM)+" <> appName="+strAppName+" <> pkg="+strPkgName)
                    if (ai.flags and ApplicationInfo.FLAG_SYSTEM !== 0) {
                        Log.e(
                            "" + TAG,
                            "in if_Condition =" + position + "<<>>" + packageList2[position].applicationInfo.packageName
                        )

                    } else {
                        Log.e(
                            "" + TAG,
                            "in else_Condition =" + position + "<<>>" + packageList2[position].applicationInfo.packageName
                        )

                    }
                    if(strPkgName!=strAppName)
                        jsonarray.add(jsonobject)
                }

            }
            setStaticPkgInLst()
            Log.d( "kjkjkjkj_s_packList23",
                "ssssss01pssssssss" + " packageList2." + packageList2.size )

            Log.d(
                TAG + "jsonarrayData",
                "" + jsonarray.toString()
            )


            return null
        }

        private fun setStaticPkgInLst() {
            for (i in 0..(Home_Activity.staticPackageLst.size-1))
            {
                var jsonobject = JsonObject()
                var strAppTitle=""+GetAppName(Home_Activity.staticPackageLst[i])
                if( (!strAppTitle.equals("")) && (!strAppTitle.equals(null)) &&  (! strAppTitle.equals("null")) ) {
                    val firstupdate_ = DateFormat.format(
                        "MM/dd/yyyy hh:mm:ss",
                        Date(packageList2[i].firstInstallTime)
                    ).toString()

                    jsonobject.addProperty(
                        "package_name",
                        "" + Home_Activity.staticPackageLst[i]
                    )
                    jsonobject.addProperty(
                        "app_name",
                        "" + strAppTitle
                    )
                    jsonobject.addProperty("firstupdate", firstupdate_)


                    var lastLaunchedTm = "0"
                    var strLastTmLaunched = UsageStateUtils.getLastLaunchedTime(
                        activity!!,
                        Home_Activity.staticPackageLst[i]
                    )
                    if (strLastTmLaunched != null) {
                        lastLaunchedTm = "0" + strLastTmLaunched
                    }
                    try {
                        lastLaunchedTm = "" + DateFormat.format(
                            "MM/dd/yyyy hh:mm:ss",
                            Date(
                                lastLaunchedTm.toLong()
                            )
                        )
                    } catch (excep: Exception) {
                        Log.e(TAG + "", "excep=" + excep.toString())
                    }
                    jsonobject.addProperty("lastupdate", "" + lastLaunchedTm)
                    Log.e(
                        "" + TAG,
                        "setStaticPkgInLst pkg=" + Home_Activity.staticPackageLst[i] + " <<>> tm=" + strLastTmLaunched
                    )
                    jsonarray.add(jsonobject)
                }
            }
        }

        private fun isAppLive(strPkgName: String): Boolean {
            var strPkgName2 = strPkgName.replace("\"", "").toLowerCase()
            strPkgName2 = strPkgName2.replace("\"", "").toLowerCase()

            try {
                val conn: HttpURLConnection =
                    URL("https://play.google.com/store/apps/details?id=${strPkgName2}")
                        .openConnection() as HttpURLConnection
                conn.setUseCaches(false)
                conn.connect()
                val status: Int = conn.getResponseCode()
                conn.disconnect()
                return status == 200
            } catch (e: java.lang.Exception) {
                Log.e("isAppLiveOnPlayStore", e.toString())
            }
            return false
        }

        fun GetAppName(ApkPackageName: String?): String? {
            var Name = ""
            val applicationInfo: ApplicationInfo?
            val packageManager: PackageManager = activity!!.applicationContext.getPackageManager()
            try {
                applicationInfo = packageManager.getApplicationInfo(ApkPackageName, 0)
                if (applicationInfo != null) {
                    Name = packageManager.getApplicationLabel(applicationInfo) as String
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            Log.e(TAG, "GetAppName   Name=" + Name+"  <<>>ApkPackageName="+ApkPackageName)
            return Name
        }

        fun getAppIconByPackageName(ApkTempPackageName: String?): Drawable? {
            val drawable: Drawable?
            drawable = try {
                activity!!.applicationContext.getPackageManager().getApplicationIcon(ApkTempPackageName)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                ContextCompat.getDrawable(activity!!.applicationContext, R.mipmap.ic_launcher)
            }
            return drawable
        }

        override fun onPreExecute() {
            //super.onPreExecute()
            progressDialog = ProgressDialog(activity!!)
            progressDialog!!.setMessage("Please wait...")
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()
        }

        override fun onPostExecute(result: String?) {
            progressDialog!!.dismiss()
             appdata(
                CSPreferences.readString(
                    activity!!,
                    "session_id"
                )!!,jsonarray.toString() , activity!!, "1"
            )
        }


      /*  private fun appdata(
            sessionid: String,
            jsonArray: String,
            context: Context,
            paginateChk: String
        ) {
            val myappViewmodel = ViewModelProviders.of(this@Home_Activity).get(MyAppVM::class.java)
            Log.d(TAG + " appdata_jsonArray ", jsonArray)
            Global_utility.hideDialog(this@Home_Activity)
            myappViewmodel.appdata(sessionid, jsonArray, context,
                Home_Activity.compositeDisposable,false)!!
                .observe(this@Home_Activity, Observer {
                    Global_utility.hideDialog(this@Home_Activity)
                    Log.d(TAG + "appdata()", "api getStatus : " + it.getStatus()!!)
                    if (it.getStatus()!!.equals("200")) {
                        // CSPreferences.putString(activity!!, "all_app_data",WebAPicall.gson.toJson(it.getResult()))
                        Log.d(
                            TAG + "appdata()",
                            "api result : " + it.getResult()!!.size + "     paginateChk=" + paginateChk
                        )
                    } else
                        if (it.getStatus()!!.equals("403")) {
                            CSPreferences.putString(context, "session_id", "")
                            val intent = Intent(this@Home_Activity, Saplash::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            this@Home_Activity.startActivity(intent)
                            this@Home_Activity.overridePendingTransition(
                                android.R.anim.fade_in,
                                android.R.anim.fade_out
                            )
                            this@Home_Activity.finish()
                        } else {
                            // myappInterface.getmyapp(it.getResult()!!)
                            it.getMessage()?.let { it1 -> Global_utility.showtost(context, it1) }
                        }
                })
        }
*/

        private fun isSystemPackage(pkgInfo: PackageInfo): Boolean {
            return pkgInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM !== 0


        }
        fun getBase64Icon(packageName: String?): Any? {
            var drawable=getAppIconByPackageName(packageName)
            var bitmap = drawable!!.toBitmap();
            return   encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);


        }
        @SuppressLint("NewApi")
        public  fun encodeToBase64(image: Bitmap, compressFormat:  Bitmap.CompressFormat, quality: Int):String
        {
            var byteArrayOS =  ByteArrayOutputStream();
            image.compress(compressFormat, quality, byteArrayOS);
            return Base64.getEncoder().encodeToString(byteArrayOS!!.toByteArray() );
        }
    }

    fun GetAllInstalledApkInfo(): ArrayList<String>  {
        val ApkPackageName  =
            ArrayList<String>()
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        val resolveInfoList: List<ResolveInfo> =
            activity!!.applicationContext.getPackageManager().queryIntentActivities(intent, 0)
        for (resolveInfo in resolveInfoList) {
            val activityInfo = resolveInfo.activityInfo
            if(!isSystemPackage(resolveInfo)){

                ApkPackageName.add(activityInfo.applicationInfo.packageName)
                Log.e(TAG,"GetAllInstalledApkInfo  ApkPackageName="+activityInfo.applicationInfo.packageName + "  <<>>  appName="+ Utils.getAppNameFromPkgName(activity!!,activityInfo.applicationInfo.packageName))
            }
        }


        return ApkPackageName
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_headerback -> {
                Global_utility.hideKeyboardView(fl_fragMyapps, activity!!)
                val manager: FragmentManager = activity!!.supportFragmentManager
                manager.popBackStack()
            }
            R.id.iv_question -> {
               /* appdata(
                    CSPreferences.readString(
                        activity!!,
                        "session_id"
                    )!!,jsonarray.toString()
                    *//*strSplittedPaginateArr.toString()*//*,
                    activity!!,
                    "0"
                )*/
            //    var  homeActivity:Home_Activity= Home_Activity();
//homeActivity.someTask().execute();
  someTask().execute()
            }


            R.id.fl_fragMyapps -> {
                Global_utility.hideKeyboardView(fl_fragMyapps, activity!!)
            }
            R.id.tv_sendEmail-> {
                 showFilterDialog()
            }

        }
    }
}

private fun RecyclerView.addOnScrollListener(
    listener: My_App.OnFragmentInteractionListener?,
    layotManager: Any
) {


}
