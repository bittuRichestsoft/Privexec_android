package com.privexec.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.lee_da_hang_pte_ltd.utills.CSPreferences
import com.lee_da_hang_pte_ltd.utills.Global_utility
import com.privexec.R
import com.privexec.activity.Saplash
import com.privexec.fragmnet.Myapp_deatal
import com.privexec.otherutility.Global_Class
import com.privexec.pojoclass.App_dataPojo
import com.privexec.utills.Utils
import com.privexec.viewmodel.MyAppVM
import io.reactivex.disposables.CompositeDisposable
import java.util.*


class Myapp_adapter(
    var activity: FragmentActivity,
    var newRelease: ArrayList<PackageInfo>,
    var packageManager: PackageManager,
    var getarray: List<App_dataPojo.Result>
) : RecyclerView.Adapter<Myapp_adapter.MyViewHolder>() {
    var TAG = "Myapp_adapter "
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemLayoutView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_myapp_row, null)
        val viewHolder = MyViewHolder(itemLayoutView)
        return viewHolder;
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

//        var data = newRelease[position].applicationInfo
        // var appnameData = packageManager.getApplicationLabel(newRelease[position].applicationInfo)
        // holder.appname!!.text = packageManager.getApplicationLabel(newRelease[position].applicationInfo)
        holder.bindWith(holder, position, getarray, activity!!)

//        if (getarray[position].image.toString()!!.equals("null")){
//            var drawable = activity!!.getResources().getDrawable(R.drawable.globe)
//            drawable = DrawableCompat.wrap(drawable)
//            DrawableCompat.setTint(drawable.mutate(), activity!!.getResources().getColor(R.color.pink))
//            holder.app_icon!!.setImageDrawable(drawable)
//
//            //Log.d("kjkjkjkj","sssssss1sssssss"+ getarray[position].getMeta()!!.getImage())
//
//        }else {

        // }

        /* var appIcon = packageManager.getApplicationIcon(newRelease[position].applicationInfo)
         //  println("TAG11111111pack22222"+appnameData+" appIcon: "+appIcon)
         holder.app_icon!!.setImageDrawable(appIcon)
 */

    }

    override fun getItemCount(): Int {

        //return getarray.size
        return getarray.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindWith(
            holder: MyViewHolder,
            position: Int,
            getarray: List<App_dataPojo.Result>,
            activity: FragmentActivity
        ) {
            if (getarray[position].typeApp == 1) {
                Log.d(
                    "Myapp_adapter ",
                    "getarray.size= " + getarray.size + "   name=" + getarray[position].title
                )
                if (getarray[position].image.isNullOrEmpty()) {

                     try {
                        var strAppPkgName = "" + getarray[position].androidPackageName
                        strAppPkgName = strAppPkgName!!.replace("\"", "")
                        strAppPkgName = strAppPkgName!!.replace("\"", "")

                        val icon =
                            //getAppIconByPackageName(strAppPkgName, activity)
                            (activity as Activity)!!.applicationContext.getPackageManager()
                                .getApplicationIcon(strAppPkgName)
                        holder.app_icon!!.setImageDrawable(icon)

                    } catch (excep: Exception) {
                        Log.e(
                            "Myapp_adapter",
                            "getAppIconByPackageName()  excep=" + excep.toString()
                        )
                        Glide.with(activity).load(getarray[position].image + "").into(holder.app_icon!!)
                    }
                }



                if (!getarray[position].title.isNullOrEmpty()) {
                    var strAppName = getarray[position].title.toString()
                    strAppName = strAppName.replace("\"", "")
                    strAppName = strAppName.replace("\"", "")
                    holder.appname!!.text = strAppName
                }


            }
            try {
      var strAppPkgName = "" + getarray[position].androidPackageName
                strAppPkgName = strAppPkgName!!.replace("\"", "")
                strAppPkgName = strAppPkgName!!.replace("\"", "")

                val icon =
                   // getAppIconByPackageName(strAppPkgName, activity)
                    (activity as Activity)!!.applicationContext.getPackageManager()
                        .getApplicationIcon(strAppPkgName)
                holder.app_icon!!.setImageDrawable(icon)

            } catch (excep: Exception) {
                Log.e(
                    "Myapp_adapter",
                    "getAppIconByPackageName()  excep=" + excep.toString()
                )
                Glide.with(activity).load(getarray[position].image + "").into(holder.app_icon!!)
            }

            holder.itemView.setOnClickListener {
                Log.e("holder.itemView", "" + getarray[position].id.toString())
                  callApiForGetAppInfo(activity!!, getarray, position)

              /*  if (getarray[position].id.toString().equals("") || getarray[position].id.toString()
                        .toLowerCase().equals("null")
                ) {
                    callApiForGetAppInfo(activity!!, getarray, position)
                } else {
                    CSPreferences.putString(activity!!, "appid", getarray[position].id.toString())
                    Global_Class.fragment(
                        activity,
                        Myapp_deatal.newInstance(getarray, position, getarray[position]),
                        true
                    )
                }*/
            }

            Log.e(
                "Myapp_adapter",
                "   firstUpdate=" +   replaceExtraQuotes("" + getarray[position].firstupdate)+" <<>> lastUpdate="+replaceExtraQuotes("" + getarray[position].lastupdate)
            )

        }


        private fun callApiForGetAppInfo(
            activity: FragmentActivity,
            getarray: List<App_dataPojo.Result>
            , position: Int
        ) {
            var jsonobject = JsonObject()
            jsonobject.addProperty(
                "package_name",
                replaceExtraQuotes("" + getarray[position].androidPackageName)
            )
            jsonobject.addProperty(
                "firstupdate",
                replaceExtraQuotes("" + getarray[position].firstupdate)
            )
 if( getarray[position].lastupdate.equals("") ||  getarray[position].lastupdate.equals(null))
 {
     getarray[position].lastupdate=getarray[position].firstupdate
 }
            jsonobject.addProperty(
                "app_name",
                replaceExtraQuotes("" + getarray[position].title)
            )
            jsonobject.addProperty(
                "lastupdate",
                replaceExtraQuotes("" + getarray[position].lastupdate)
            )

            jsonobject.addProperty("package_Permissions", "")
            var jsonarraySingleApp = JsonArray()
            jsonarraySingleApp.add(jsonobject)
            Log.e("Myapp_adapter", "callApiForGetAppInfo   arry = " + jsonarraySingleApp.toString())
            val myappViewmodel = ViewModelProviders.of(activity).get(MyAppVM::class.java)
            var compositeDisposable = CompositeDisposable()

            myappViewmodel.apps_dataForSingle(
                CSPreferences.readString(activity!!, "session_id").toString(),
                "" + jsonarraySingleApp,
                activity,
                compositeDisposable,true
            )!!.observe(activity, Observer {
                Log.d("Myapp_adapter" , "apps_dataForSingle api getStatus : " + it.getStatus()!!)

                if ((it.getStatus()!!) == 200) {

                    Global_utility.hideDialog(activity!!)
                    Global_utility.hideDialog(activity!!)
                    if(it.getResult()!!.size == 0)
                    {
                        it.getMessage()?.let { it1 -> Global_utility.showtost(activity!!, "App not found on playstore") }
                    }

                    if (it.getResult()!!.size > 0) {
                        CSPreferences.putString(
                            activity!!,
                            "appid",
                            "" + it.getResult()!!.get(0).id
                        )
                        Global_Class.fragment(
                            activity,
                            Myapp_deatal.newInstance(it.getResult()!!, 0, it.getResult()!!.get(0)),
                            true
                        )
                    }
                } else
                    if (it.getStatus()!!.equals(403)) {
                        CSPreferences.putString(activity, "session_id", "")
                        val intent = Intent(activity!!, Saplash::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        activity!!.startActivity(intent)
                        activity!!.overridePendingTransition(
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                        )
                        activity!!.finish()
                    }  else {

                    it.getMessage()?.let { it1 -> Global_utility.showtost(activity!!, it1) }
                }
            })


        }

        private fun replaceExtraQuotes(str: String): String {
            var str1 = str.replace("\"", "")
            str1 = str1.replace("\"", "")
            return str1
        }

        /*   private fun callApiForGetAppInfo(activity: FragmentActivity,) {
           }
   */
        fun getAppIconByPackageName(
            ApkTempPackageName: String?,
            activity: FragmentActivity
        ): Drawable? {
            val drawable: Drawable?
            var excep = ""
            drawable = try {
                (activity as Activity)!!.applicationContext.getPackageManager()
                    .getApplicationIcon(ApkTempPackageName!!)
            } catch (exc: PackageManager.NameNotFoundException) {
                excep = "" + exc
                exc.printStackTrace()
                return null/*            ContextCompat.getDrawable(
                (activity as Activity)!!.applicationContext,
                R.mipmap.ic_launcher
            )*/
            }
            if (!excep.equals(""))
                Log.e("Myapp_adapter", "getAppIconByPackageName()  excep=" + excep.toString())


            return drawable
        }

        var appname: TextView? = null
        var app_icon: ImageView? = null

        init {
            appname = itemView.findViewById(R.id.appname)
            app_icon = itemView.findViewById(R.id.app_icon)
        }

    }

    fun dateforment(instal: Long, contex: Context) {
        val dateString = DateFormat
            .format("MM/dd/yyyy hh:mm:ss", Date(instal))
            .toString()

        Log.d("dfsdfs", dateString)
        val timeDifference = getTimeDifference(Date(instal))
        Log.d("dfsdfs", timeDifference)

        // Get the time difference between now and app first install time
        /*  val timeDifference =contex.getTimeDifference(Date(installedTime))

          // Display the app install date time on text view
          mTextView.setText("First Install Time\n$dateString")

          // Display app first install time ago
          mTextView.append("\n\n Time ago\n")
          mTextView.append(timeDifference)*/
    }

    protected fun getTimeDifference(then: Date): String {
        return getReadableTime(getSecondsDifference(then))
    }

    protected fun getReadableTime(seconds: Int): String {
        var readableTime = ""

        val hours = seconds / 3600
        var remainder = seconds - hours * 3600
        val mins = remainder / 60
        remainder = remainder - mins * 60
        val secs = remainder

        if (hours > 0) {
            readableTime = "$hours hour "
        }
        if (mins > 0) {
            readableTime += "$mins min "
        }
        if (secs > 0) {
            readableTime += "$secs sec"
        }
        return readableTime
    }

    protected fun getSecondsDifference(then: Date): Int {
        val now = Date(System.currentTimeMillis())
        val dateDiff = now.time - then.time
        return dateDiff.toInt() / 1000 // 1000 milliseconds = 1 second
    }

}
