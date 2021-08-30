package com.privexec.fragmnet

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.lee_da_hang_pte_ltd.utills.CSPreferences
import com.lee_da_hang_pte_ltd.utills.Global_utility
import com.privexec.R
import com.privexec.activity.Home_Activity
import com.privexec.databinding.FragmentMyappDeatalBinding
import com.privexec.otherutility.Global_Class
import com.privexec.pojoclass.App_dataPojo
import com.privexec.utills.UsageStateUtils
import com.privexec.viewmodel.MyAppVM
import kotlinx.android.synthetic.main.dialog_alert.*
import kotlinx.android.synthetic.main.fragment_myapp_deatal.*
import kotlinx.android.synthetic.main.header.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Myapp_deatal.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Myapp_deatal.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class Myapp_deatal : Fragment() ,View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    internal var packageManagers: PackageManager? = null
    private var myappdeatalbinding: FragmentMyappDeatalBinding? = null
    private var views: View? = null
    var addpojo_rsult: App_dataPojo.Result? = null
    var choosedPosition: Int? = null
    var allAppsLst:  List<App_dataPojo.Result>? = null
    var TAG="Myapp_deatal "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        myappdeatalbinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_myapp_deatal, container, false)
        views = myappdeatalbinding!!.getRoot()
        packageManagers = activity!!.packageManager
       // var jsonarray  = JsonArray()
//        for (position in 0..Home_Activity.packageList2.size - 1) {
//            var jsonobject = JsonObject()
////            jsonobject.addProperty("image", "" + addpojo_rsult!!.getImage())
////            jsonobject.addProperty("name", "" + addpojo_rsult!!.getName())
////            jsonobject.addProperty("package_name", "" + addpojo_rsult!!.getPackageName())
////            jsonobject.addProperty("version_name", addpojo_rsult!!.getVersionName())
////            jsonobject.addProperty("targetversion", addpojo_rsult!!.getTargetversion())
////            jsonobject.addProperty("firstupdate", addpojo_rsult!!.getFirstupdate())
////            jsonobject.addProperty("lastupdate", addpojo_rsult!!.getLastupdate())
//
//
//            jsonarray.add(jsonobject)
//
//        }
//        var strd = Home_Activity.jsonarray.toString()
       // if (Global_utility.isNetworkConnected(activity!!)) {
            //  appdata(CSPreferences.readString(activity!!,"session_id")!!,"[{\"image\":\"android.graphics.drawable.AdaptiveIconDrawable@8c88663\",\"name\":\"YouTube\",\"package_name\":\"com.google.android.youtube\",\"version_name\":\"13.15.61\",\"targetversion\":\"28\",\"firstupdate\":\"04/11/2019 04:06:53\",\"lastupdate\":\"04/11/2019 04:06:53\"}]",activity!!,this)

//            oneAppData(
//                CSPreferences.readString(activity!!, "session_id")!!,
//                jsonarray.toString(), activity!!
//            )
//        } else {
//            Global_utility.showtost(activity!!, resources.getString(R.string.internet_connection))
//        }

        myappdeatalbinding!!.dsarbutton.setOnClickListener {
       //    Log.e(TAG,"addpojo_rsult ="+Gson().toJson(addpojo_rsult!! ))

            Global_Class.fragment(activity!!, Dsar_fragmnet.newInstance("", addpojo_rsult!!), true)
        }
        myappdeatalbinding!!.tvPrivacyPolicy.setOnClickListener{
            val url = ""+addpojo_rsult!!.privacyPolicy
            val i = Intent(Intent.ACTION_VIEW)
            i.setData(Uri.parse(url))
            startActivity(i)
        }



        try {

            var strAppPkgName = "" +addpojo_rsult!!.androidPackageName
            strAppPkgName = strAppPkgName!!.replace("\"", "")
            strAppPkgName = strAppPkgName!!.replace("\"", "")

            val icon =
              //  getAppIconByPackageName(strAppPkgName, activity!!)
                (activity as Activity)!!.applicationContext.getPackageManager()
                    .getApplicationIcon(strAppPkgName)
            myappdeatalbinding!!.appImage.setImageDrawable(icon)

        }
        catch (excep: Exception) {
            Log.e(
                TAG+"",
                "getAppIconByPackageName()  excep=" + excep.toString()
            )
            Glide.with(activity!!).load(addpojo_rsult!!.image + "")
                .into(myappdeatalbinding!!.appImage) }



        myappdeatalbinding!!.category.text = addpojo_rsult!!.categories.toString()
            .replace("[", "")  //remove the right bracket
            .replace("]", "")  //remove the left bracket
            .trim();
if(!(addpojo_rsult!!.categories!! .toString().equals("null")) &&  !(addpojo_rsult!!.categories!! .toString().equals("")) )
{
    myappdeatalbinding!!.llAppCategory.visibility=View.VISIBLE
}
        myappdeatalbinding!!.appName.text = addpojo_rsult!!.title.toString()
        if(!(addpojo_rsult!!.app_full_title.toString().equals("null")) &&  !(addpojo_rsult!!.app_full_title.toString().equals("")) )
     {
         myappdeatalbinding!!.appName.text = addpojo_rsult!!.app_full_title.toString()
     }

        myappdeatalbinding!!.installDate.text = addpojo_rsult!!.firstupdate.toString()
        myappdeatalbinding!!.lastDate.text = addpojo_rsult!!.lastupdate.toString()
///////////////////////////

        Log.e(TAG," tm_firstUpdate= "+addpojo_rsult!!.firstupdate.toString()  +"<<>  lastUpdate="+addpojo_rsult!!.lastupdate.toString())


        var lastLaunchedTm = "0"
        if(addpojo_rsult!!.firstupdate.toString()!=null &&  addpojo_rsult!!.firstupdate.toString()!="" )
        {
            lastLaunchedTm=addpojo_rsult!!.firstupdate.toString()
        }

   if(isPackageInstalled(""+addpojo_rsult!!.androidPackageName) ){
       var strLastTmLaunched = UsageStateUtils.getLastLaunchedTime(
           activity!!,
           addpojo_rsult!!.androidPackageName
       )
       if (strLastTmLaunched != null) {
           lastLaunchedTm = "" + strLastTmLaunched
       }
       if (strLastTmLaunched != null && strLastTmLaunched.length <= 5 && addpojo_rsult!!.firstupdate.toString() != null && addpojo_rsult!!.firstupdate.toString() != "") {
           lastLaunchedTm = addpojo_rsult!!.firstupdate.toString()
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
       Log.e(TAG + "", "fetched_lastupdate=" + lastLaunchedTm)

       if (!lastLaunchedTm.equals("") && lastLaunchedTm.contains(":")) {
           myappdeatalbinding!!.lastDate.text = lastLaunchedTm
       }
   }


        if(((addpojo_rsult!!.privacyPolicy) != null)   &&  (addpojo_rsult!!.privacyPolicy) != "" ) {
            myappdeatalbinding!!.tvPrivacyPolicy.setPaintFlags(myappdeatalbinding!!.tvPrivacyPolicy.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
            myappdeatalbinding!!.tvPrivacyPolicy.setText("Privacy Policy")
            myappdeatalbinding!!.tvPrivacyPolicy.visibility=View.VISIBLE
    }

        myappdeatalbinding!!.swichbuttion.visibility = View.GONE
        myappdeatalbinding!!.ivDeleteApp.setOnClickListener(View.OnClickListener {
            showDeleteAppPopUp()
        })

        myappdeatalbinding!!.tvPermissionInfo.visibility = View.VISIBLE
       if((addpojo_rsult!!.permissions)!!.size>0)
       {
var strAllPermissions=""
           for (i in 0..((addpojo_rsult!!.permissions)!!.size-1)) {
               strAllPermissions= strAllPermissions+" . "+addpojo_rsult!!.permissions!!.get(i)+"\n"
           }
                       myappdeatalbinding!!.tvPermissionInfo.setText(""+strAllPermissions )
       }

        if((addpojo_rsult!!.personalDataType)!!.size>0)
        {
            var strPersonalData=""
            for (i in 0..((addpojo_rsult!!.personalDataType)!!.size-1)) {
                strPersonalData= strPersonalData+"  - "+addpojo_rsult!!.personalDataType!!.get(i).personalDatatypesName+"\n"
            }
            myappdeatalbinding!!.tvPersonalDataShared.setText(""+strPersonalData )
        }

Log.d(TAG,"addpojo_rsult = "+        Gson().toJson(addpojo_rsult!!)  )
        myappdeatalbinding!!.swichbuttion.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(
                buttonView: CompoundButton,
                isChecked: Boolean
            ) {
                if (isChecked) {
                    myappdeatalbinding!!.tvPermissionInfo.visibility = View.VISIBLE
                } else {
                    myappdeatalbinding!!.tvPermissionInfo.visibility = View.GONE
                }
            }
        })

        myappdeatalbinding!!.ivPlusPermission.setOnClickListener(this)
        myappdeatalbinding!!.ivMinusPermission.setOnClickListener(this)
        myappdeatalbinding!!.ivPlusPersonalData.setOnClickListener(this)
        myappdeatalbinding!!.ivMinusPersonalData.setOnClickListener(this)

        // Inflate the layout for this fragment
        return views
    }

    private fun isPackageInstalled(
        packageName: String
    ): Boolean {
        return try {
            val pm = context!!.packageManager
            pm.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
    private fun showDeleteAppPopUp() {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_alert)
        dialog.tvMsg.text = activity!!.getString(R.string.want_to_delete_app)

        dialog.btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.btnYes.setOnClickListener {
            dialog.dismiss()
            if (Global_utility.isNetworkConnected(activity!!)) {
                deleteAppApi(activity!!, CSPreferences.readString(activity!!, "session_id").toString())
            } else {
                Global_utility.showtost(activity!!, resources.getString(R.string.internet_connection))
            }
        }
        dialog.show()
    }

    fun getAppIconByPackageName(
        ApkTempPackageName: String?,
        activity: FragmentActivity
    ): Drawable? {
        val drawable: Drawable?
        var excep = ""
        drawable = try {


            (activity as Activity)!!.applicationContext.getPackageManager()
                .getApplicationIcon(ApkTempPackageName)
        } catch (exc: PackageManager.NameNotFoundException) {
            excep = "" + exc
            exc.printStackTrace()
            ContextCompat.getDrawable(
                (activity as Activity)!!.applicationContext,
                R.mipmap.ic_launcher
            )

        }
        if (!excep.equals(""))
            Log.e("Myapp_adapter", "getAppIconByPackageName()  excep=" + excep.toString())


        return drawable
    }
    private fun deleteAppApi(context: Context, sessionid: String) {
        val myappViewmodel = ViewModelProviders.of(this).get(MyAppVM::class.java)
        myappViewmodel.deleteAppApi(sessionid,""+addpojo_rsult!!.id, context)!!.observe(this, Observer {
            Log.d(TAG, "getDsarQuestApi ${it.getStatus()!!}")
            if ((it.getStatus()!!) == 200) {
                    it.getMessage()?.let { it1 -> Global_utility.showtost(activity!!, it1) }
                val intent = Intent(activity!!, Home_Activity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.putExtra("from","myAppDetails")
                startActivity(intent)
                activity!!.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                activity!!.finish()
            } else {
                it.getMessage()?.let { it1 -> Global_utility.showtost(activity!!, it1) }
            }
        })
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_headerTitle.setText("My Apps")
        img_headerback.setOnClickListener(this)
        fl_fragMyappDetail.setOnClickListener(this)
    }
    private fun oneAppData(sessionid: String, jsonArray: String, context: Context) {
        val myappViewmodel = ViewModelProviders.of(this).get(MyAppVM::class.java)
        myappViewmodel.oneAppData(sessionid, jsonArray, context)!!.observe(this, Observer {
            if (it.getStatus()!!.equals("200")) {
                // CSPreferences.putString(activity!!, "all_app_data",WebAPicall.gson.toJson(it.getResult()))
                it.getMessage()?.let { it1 -> Global_utility.showtost(context, it1) }
            } else {
                // myappInterface.getmyapp(it.getResult()!!)
                it.getMessage()?.let { it1 -> Global_utility.showtost(context, it1) }
            }
        })
    }

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
        fun newInstance(param1: List<App_dataPojo.Result>,position:Int, param2: App_dataPojo.Result) =
            Myapp_deatal().apply {
                arguments = Bundle().apply {
                //    putString(ARG_PARAM1, param1)
                    allAppsLst=param1
                    choosedPosition= position
                    addpojo_rsult = param2
                }
            }
    }

    override fun onClick(v: View?) {

        when(v!!.id)
        {
            R.id.img_headerback ->
            {
                Global_utility.hideKeyboardView(fl_fragMyappDetail, activity!!)
                val manager: FragmentManager = activity!!.supportFragmentManager
                manager.popBackStack()
            }

            R.id.fl_fragMyappDetail ->
            {
                Global_utility.hideKeyboardView(fl_fragMyappDetail, activity!!)
            }

            R.id.iv_plusPermission ->
            {
                myappdeatalbinding!!.ivMinusPermission.visibility=View.VISIBLE
                myappdeatalbinding!!.ivPlusPermission.visibility=View.GONE
                tv_permissionInfo.visibility=View.VISIBLE
            }

            R.id.iv_minusPermission->
            {
                myappdeatalbinding!!.ivMinusPermission.visibility=View.GONE
                myappdeatalbinding!!.ivPlusPermission.visibility=View.VISIBLE
                tv_permissionInfo.visibility=View.GONE
            }

            R.id.iv_plusPersonalData ->
            {
                myappdeatalbinding!!.ivMinusPersonalData.visibility=View.VISIBLE
                myappdeatalbinding!!.ivPlusPersonalData.visibility=View.GONE
                tv_personalDataShared.visibility=View.VISIBLE
            }

            R.id.iv_minusPersonalData->
            {
                myappdeatalbinding!!.ivMinusPersonalData.visibility=View.GONE
                myappdeatalbinding!!.ivPlusPersonalData.visibility=View.VISIBLE
                tv_personalDataShared.visibility=View.GONE
            }

            /*ivPlusPermission.setOnClickListener(this)
        myappdeatalbinding!!.ivMinusPermission.setOnClickListener(this)
        myappdeatalbinding!!.ivPlusPersonalData.setOnClickListener(this)
        myappdeatalbinding!!.ivMinusPersonalData*/
        } }


}
