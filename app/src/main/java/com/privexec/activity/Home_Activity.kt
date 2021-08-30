package com.privexec.activity

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
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.Settings
import android.text.format.DateFormat
import android.util.Log
import android.view.MenuItem
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.lee_da_hang_pte_ltd.utills.CSPreferences
import com.lee_da_hang_pte_ltd.utills.Global_utility
import com.privexec.Api_Call.WebAPicall
import com.privexec.R
import com.privexec.databinding.ActivityHomeBinding
import com.privexec.fragmnet.*
import com.privexec.otherutility.Global_Class
import com.privexec.utills.UsageStateUtils
import com.privexec.utills.Utils
import com.privexec.viewmodel.MyAppVM
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.dialog_alert.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class Home_Activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var homeBinding: ActivityHomeBinding? = null
    internal var packageManagers: PackageManager? = null
    var TAG = "Home_Activity "
    var collectAllPackagesChk = true


    companion object {
        public var toolbar: Toolbar? = null
        public var tvTitle: TextView? = null
        var packageList2 = ArrayList<PackageInfo>()
        var staticPackageLst = ArrayList<String>()
        var resolvePkgLst = ArrayList<String>()

        var jsonarray = JsonArray()
        var toolbarTitle = ""
        var videoToolbarTitle = ""
        private var compositeDisposable: CompositeDisposable?  = CompositeDisposable()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        packageManagers = packageManager
        toolbar = findViewById(R.id.toolbar)
        tvTitle = findViewById(R.id.tvTitle)
        setSupportActionBar(toolbar)
        toolbar!!.setNavigationIcon(R.drawable.ic_hamberger_black)
//        toolbar!!.setTitle(resources.getString(R.string.menu))
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val header = navView.getHeaderView(0)
        var tv_loggedName = header.findViewById<TextView>(R.id.tv_profileName)
        var iv_loggedProfileImg = header.findViewById<ImageView>(R.id.iv_loggedProfileImg)
        CSPreferences.putString(this@Home_Activity, "push_active_app_status","0")

        tv_loggedName.text = "" + (CSPreferences.readString(this, "user_name"))
        Glide.with(this@Home_Activity).load(CSPreferences.readString(this, "user_img"))
            .placeholder(R.drawable.logo_icon)
            .into(iv_loggedProfileImg);


        homeBinding!!.myapp.setOnClickListener {
            toolbarTitle = resources.getString(R.string.myapps)
            homeBinding!!.myapp.isClickable=false
            Handler().postDelayed( Runnable {
                homeBinding!!.myapp.isClickable=true
            },5000        )

            //  homeBinding!!.relTitleLayout.tvTitle.setText(resources.getString(R.string.myapps))
            Global_Class.fragment(this, My_App(), true)
        }

        homeBinding!!.rating.setOnClickListener {
            toolbarTitle = resources.getString(R.string.privacyrating)
            homeBinding!!.rating.isClickable=false
            Handler().postDelayed( Runnable {
                homeBinding!!.rating.isClickable=true
            },5000        )

            //   homeBinding!!.relTitleLayout.tvTitle.setText(resources.getString(R.string.privacyrating))
            Global_Class.fragment(this, PrivacyRating(), true)
        }

        homeBinding!!.preference.setOnClickListener {
            //     homeBinding!!.relTitleLayout.tvTitle.setText(resources.getString(R.string.prefrence))
            homeBinding!!.preference.isClickable=false
            Handler().postDelayed( Runnable {
                homeBinding!!.preference.isClickable=true
            },5000        )

            Global_Class.fragment(this, Preferences(), true)
        }
        homeBinding!!.reports.setOnClickListener {
        homeBinding!!.reports.isClickable=false
            Handler().postDelayed( Runnable {
                homeBinding!!.reports.isClickable=true
            },5000        )


            //    Handler
            //     homeBinding!!.relTitleLayout.tvTitle.setText(resources.getString(R.string.reports))
            Global_Class.fragment(this, ReportsFragment(), true)
        }
        homeBinding!!.tutroial.setOnClickListener {
            //    homeBinding!!.relTitleLayout.tvTitle.setText(resources.getString(R.string.tutorial))
            Global_Class.fragment(this, Tutroial(), true)
        }

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        Global_utility.hideDialog(this)


        if (UsageStateUtils.getUsageStatsList(this).isEmpty()/* isAccessGranted()*/) {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            startActivity(intent)
        }
     //   showDialogForUsageTime()

        var strfrom ="";
            try {
  strfrom = intent.extras!!.get("from").toString()
            if (strfrom!!.equals("rating")) {
                homeBinding!!.rating.callOnClick()
                collectAllPackagesChk = false
            } else if (strfrom!!.equals("myAppDetails")) {
                homeBinding!!.myapp.callOnClick()
                collectAllPackagesChk = false
            }
 else if (strfrom!!.equals("profile")) {
                collectAllPackagesChk = false
            }
        } catch (excep: Exception) {
        }

        Log.e(TAG,"strfrom="+strfrom)

        if (collectAllPackagesChk)
            someTask().execute()
   }
    
    fun GetAllInstalledApkInfo(): ArrayList<String>  {
        val ApkPackageName  =
            ArrayList<String>()
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        val resolveInfoList: List<ResolveInfo> =
            applicationContext.getPackageManager().queryIntentActivities(intent, 0)
        for (resolveInfo in resolveInfoList) {
            val activityInfo = resolveInfo.activityInfo
            if(!isSystemPackage(resolveInfo)){
                ApkPackageName.add(activityInfo.applicationInfo.packageName)
        Log.e(TAG,"GetAllInstalledApkInfo  ApkPackageName="+activityInfo.applicationInfo.packageName + "  <<>>  appName="+ Utils.getAppNameFromPkgName(this,activityInfo.applicationInfo.packageName))
            }
        }


        return ApkPackageName
    }
    fun isSystemPackage(resolveInfo: ResolveInfo): Boolean {
        return resolveInfo.activityInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }


    // jsonarray.clear
    // val packageList23 = ArrayList<PackageInfo>()

    //Info dialog.............................................
    inner class someTask() : AsyncTask<Void, Void, String>() {
        var progressDialog: ProgressDialog? = null

        @SuppressLint("WrongThread")
        override fun doInBackground(vararg params: Void?): String? {
            resolvePkgLst=GetAllInstalledApkInfo()
            Log.d("sadsdad", CSPreferences.readString(this@Home_Activity, "session_id")  +"     resolvePkgLst="+resolvePkgLst.size )
     jsonarray = JsonArray()
            staticPackageLst.clear()
            staticPackageLst.addAll(getResources().getStringArray(R.array.static_packages))
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
                  if (!isSystemPackage(packageList2[position])  && (! staticPackageLst.contains( packageList2[position].applicationInfo.packageName))   && resolvePkgLst.contains(packageList2[position].applicationInfo.packageName)) {
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
                          this@Home_Activity,
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

                /* if(packageList2[position].applicationInfo.name != null)
Log.e(TAG+" applicationInfo","name="+packageList2[position].applicationInfo.name +"  <<>>  firstInstallTime= "+(packageList2[position].lastUpdateTime).toString())
*/
                /*       val appNameAndPermissions = StringBuffer()
                val packageInfo = packageManagers!!.getPackageInfo(packageList2[position].applicationInfo.packageName, PackageManager.GET_PERMISSIONS)
                       appNameAndPermissions.append(packageInfo.packageName + "*******:\n")
                       //Get Permissions
                       val requestedPermissions = packageInfo.requestedPermissions
                        if (requestedPermissions != null  && (!isSystemPackage(packageList2[position]))) {
                            for (i in requestedPermissions.indices) {
                                var strPermission = requestedPermissions[i]
                                if (strPermission.contains("android.permission.")) {
                                    strPermission =
                                        strPermission.replace("android.permission.".toRegex(), "")
                                }
                                appNameAndPermissions.append(strPermission + "\n")
                            }
                            appNameAndPermissions.append("\n")
                            jsonobject.addProperty("package_Permissions", "" + appNameAndPermissions)
                            jsonarray.add(jsonobject)
                        }*/
                //  }
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
       for (i in 0..(staticPackageLst.size-1))
       {
           var jsonobject = JsonObject()
           var strAppTitle=""+GetAppName(staticPackageLst[i])
         if( (!strAppTitle.equals("")) && (!strAppTitle.equals(null)) &&  (! strAppTitle.equals("null")) ) {
             val firstupdate_ = DateFormat.format(
                 "MM/dd/yyyy hh:mm:ss",
                 Date(packageList2[i].firstInstallTime)
             ).toString()

             jsonobject.addProperty(
                 "package_name",
                 "" + staticPackageLst[i]
             )
             jsonobject.addProperty(
                 "app_name",
                 "" + strAppTitle
             )
             jsonobject.addProperty("firstupdate", firstupdate_)


             var lastLaunchedTm = "0"
             var strLastTmLaunched = UsageStateUtils.getLastLaunchedTime(
                 this@Home_Activity,
                 staticPackageLst[i]
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
                 "setStaticPkgInLst pkg=" + staticPackageLst[i] + " <<>> tm=" + strLastTmLaunched
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
            val packageManager: PackageManager = applicationContext.getPackageManager()
            try {
                applicationInfo = packageManager.getApplicationInfo(ApkPackageName!!, 0)
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
                applicationContext.getPackageManager().getApplicationIcon(ApkTempPackageName!!)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                ContextCompat.getDrawable(applicationContext, R.mipmap.ic_launcher)
            }
            return drawable
        }

        override fun onPreExecute() {
            //super.onPreExecute()
            progressDialog = ProgressDialog(this@Home_Activity)
            progressDialog!!.setMessage("Please wait...")
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()
               }

        override fun onPostExecute(result: String?) {
            progressDialog!!.dismiss()
            appdata(
                CSPreferences.readString(
                    this@Home_Activity,
                    "session_id"
                )!!,jsonarray.toString() , this@Home_Activity, "1"
            )
        }


        private fun appdata(
            sessionid: String,
            jsonArray: String,
            context: Context,
            paginateChk: String
        ) {
            val myappViewmodel = ViewModelProviders.of(this@Home_Activity).get(MyAppVM::class.java)
            Log.d(TAG + " appdata_jsonArray ", jsonArray)
            Global_utility.hideDialog(this@Home_Activity)
            myappViewmodel.appdata(sessionid, jsonArray, context, compositeDisposable,false)!!
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


        private fun isSystemPackage(pkgInfo: PackageInfo): Boolean {
            return pkgInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM !== 0


        }
        fun getBase64Icon(packageName: String?): Any? {
            var drawable=getAppIconByPackageName(packageName)
            var bitmap = drawable!!.toBitmap();
      return   encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);


        }
        @SuppressLint("NewApi")
        public  fun encodeToBase64(image:Bitmap, compressFormat:  Bitmap.CompressFormat, quality: Int):String
        {
            var byteArrayOS =  ByteArrayOutputStream();
            image.compress(compressFormat, quality, byteArrayOS);
             return Base64.getEncoder().encodeToString(byteArrayOS!!.toByteArray() );
        }
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        when (item.itemId) {
            R.id.nav_home -> {
                val intent = Intent(this@Home_Activity, Home_Activity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
            R.id.myProfile -> {
                Global_Class.fragment(this, ProfileFrag(), true)

            }
            R.id.changePassword -> {
                 Global_Class.fragment(this, ChangePasswordFrag(), true)

               // shareFileWithEmail()
            }

            R.id.privacyPolicy -> {
                val intent = Intent(this@Home_Activity, WebViewDataActivity::class.java)
                var bundle = Bundle()
                bundle.putString("title", "Privacy Policy")
                bundle.putString("url", WebAPicall.BASE_URL_WithApi+"privacy-policy")
                intent.putExtras(bundle)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

            }

            R.id.settings -> {

            }
            R.id.logout -> {

           /*     showLogoutDialog()*/
                if (Global_utility.isNetworkConnected(this)) {
                    logoutApi(this, CSPreferences.readString(this, "session_id").toString())
                } else {
                    Global_utility.showtost(this, resources.getString(R.string.internet_connection))
                }
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

/*    private fun shareFileWithEmail() {
        val path =   Environment.getExternalStorageDirectory().toString() + "/" + "Privexec"
        val dir = File(path)
        if (!dir.exists()) dir.mkdirs()
        val file = File(dir, "savedData.pdf")

        if (!file.exists() || !file.canRead()) {
            Log.e(TAG,"not existing ")
            return
        }
        else{
            Log.e(TAG,"existing")
        }
   try {
       val shareIntent = Intent(Intent.ACTION_SENDTO)
         shareIntent.setData(Uri.parse("mailto:bittu@gmail.com" ))
       shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "test Subject")
       shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "test Extra")
       Log.e(TAG,"URI is= "+ Uri.parse(
           "file://" + Environment.getExternalStorageDirectory()
               .toString() + "/Privexec/savedData.pdf"))

        shareIntent.putExtra(
            Intent.EXTRA_STREAM, Uri.parse(
               "file://" + Environment.getExternalStorageDirectory()
                   .toString() + "/Privexec/savedData.pdf"
           )
       )
    //   startActivity(shareIntent)
       startActivity(Intent.createChooser(shareIntent, "Send mail..."));


   }
   catch (exce:java.lang.Exception)
   {
       Log.e(TAG,"exce="+exce.toString())
   }
    }*/

    private fun showLogoutDialog() {
        val dialog = Dialog(this@Home_Activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_alert)
        dialog.tvMsg.text = applicationContext.getString(R.string.want_to_logout)
        dialog.btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.btnYes.setOnClickListener {
            dialog.dismiss()
            if (Global_utility.isNetworkConnected(this)) {
                logoutApi(this, CSPreferences.readString(this, "session_id").toString())
            } else {
                Global_utility.showtost(this, resources.getString(R.string.internet_connection))
            }
        }
        dialog.show()
    }

    private fun logoutApi(context: Home_Activity, sessionid: String) {
        val myappViewmodel = ViewModelProviders.of(this).get(MyAppVM::class.java)
        myappViewmodel.logoutApi(sessionid, context)!!.observe(this, Observer {

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (toolbarTitle.equals(resources.getString(R.string.myapps))) {
            //   homeBinding!!.relTitleLayout.tvTitle.setText(resources.getString(R.string.myapps))
            if (videoToolbarTitle.equals("videoTitle")) {
                //     homeBinding!!.relTitleLayout.tvTitle.setText(resources.getString(R.string.dsar))
                videoToolbarTitle = " "
            }
        }
        else if(toolbarTitle.equals("searchAppForRAteFrag"))
        {
            homeBinding!!.rating.callOnClick()
        }
    }
}
