package com.privexec.fragmnet

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lee_da_hang_pte_ltd.utills.CSPreferences
import com.lee_da_hang_pte_ltd.utills.Global_utility
import com.privexec.R
import com.privexec.activity.Saplash
import com.privexec.databinding.FragmentPreferences2Binding
import com.privexec.pojoclass.App_dataPojo
import com.privexec.utills.DownloadTask
import com.privexec.viewmodel.MyAppVM
import com.privexec.viewmodel.PrefrencesVM
import kotlinx.android.synthetic.main.dialog_alert.*
import kotlinx.android.synthetic.main.fragment_my__app.*
import kotlinx.android.synthetic.main.fragment_preferences_2.*
import kotlinx.android.synthetic.main.header.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Preferences.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Preferences.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class Preferences : Fragment(), View.OnClickListener {

    private var listener: OnFragmentInteractionListener? = null

    //    private var viewBinding: FragmentPreferencesBinding? = null
    private var viewBinding: FragmentPreferences2Binding? = null
    private var views: View? = null
    private var countrylist = ArrayList<String>()
    private var country_Str: String? = null
    private var monthStr: String? = null
    private var changePassword: String? = null
    private var statusStr: String? = null
    private var statusDsarStr: String? = null
    private var sessionId: String? = null
    var prefrencesVM: PrefrencesVM? = null
    var checkForCountry = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_preferences_2, container, false)
        views = viewBinding!!.root
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        //list = ArrayList<CountryListModelClas>()
        sessionId = CSPreferences.readString(activity!!, "session_id")
        countrylist.clear()

        viewBinding!!.spinnerCountry.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if(++checkForCountry > 1)
                    {   country_Str = viewBinding!!.spinnerCountry.getItemAtPosition(p2).toString()
                        //    viewBinding!!.spinnerCountry.setSelection(p2).toString()
                        println("country_Str.." + country_Str)
                        if (Global_utility.isNetworkConnected(activity!!)) {
                            updateProfileCountry(country_Str!!)
                        } else {
                            Global_utility.showtost(
                                activity!!,
                                resources.getString(R.string.internet_connection)
                            )
                        }
                    }
                }
            }

        viewBinding!!.spinnerMonths.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                    monthStr = viewBinding!!.spinnerMonths.getItemAtPosition(p2).toString()
                    if (!CSPreferences.readString(activity!!, "notifyNotUsed").equals(monthStr)) {
                        if (Global_utility.isNetworkConnected(activity!!)) {
                            //  appdata(CSPreferences.readString(activity!!,"session_id")!!,"[{\"image\":\"android.graphics.drawable.AdaptiveIconDrawable@8c88663\",\"name\":\"YouTube\",\"package_name\":\"com.google.android.youtube\",\"version_name\":\"13.15.61\",\"targetversion\":\"28\",\"firstupdate\":\"04/11/2019 04:06:53\",\"lastupdate\":\"04/11/2019 04:06:53\"}]",activity!!,this)
                            notifyNotUsed(monthStr!!.replace("Month", ""), sessionId, activity!!)
                        } else {
                            Global_utility.showtost(
                                activity!!,
                                resources.getString(R.string.internet_connection)
                            )
                        }
                    }

//                prefrencesVM = PrefrencesVM(monthStr, sessionId )
//                viewBinding.prefrencesVM = prefrencesVM
                }
            }


        viewBinding!!.spinnerChangePassword.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {}

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                    changePassword =
                        viewBinding!!.spinnerChangePassword.getItemAtPosition(p2).toString()
                    if (!CSPreferences.readString(activity!!, "changePassword")
                            .equals(changePassword)
                    ) {
                        if (Global_utility.isNetworkConnected(activity!!)) {
                            //  appdata(CSPreferences.readString(activity!!,"session_id")!!,"[{\"image\":\"android.graphics.drawable.AdaptiveIconDrawable@8c88663\",\"name\":\"YouTube\",\"package_name\":\"com.google.android.youtube\",\"version_name\":\"13.15.61\",\"targetversion\":\"28\",\"firstupdate\":\"04/11/2019 04:06:53\",\"lastupdate\":\"04/11/2019 04:06:53\"}]",activity!!,this)
                            notifyChangePassword(
                                changePassword!!.replace("Month", ""),
                                sessionId,
                                activity!!
                            )
                        } else {
                            Global_utility.showtost(
                                activity!!,
                                resources.getString(R.string.internet_connection)
                            )
                        }
                    }
                }
            }

        val list = ArrayList<String>()
        list.add("1 Month")
        list.add("2 Month")
        list.add("3 Month")
        list.add("4 Month")
        list.add("5 Month")
        list.add("6 Month")
        list.add("7 Month")
        list.add("8 Month")
        list.add("9 Month")
        list.add("10 Month")
        list.add("11 Month")
        list.add("12 Month")
        val adapterMonth = ArrayAdapter<String>(activity!!, R.layout.item_spinner_item, list)
        adapterMonth.setDropDownViewResource(R.layout.item_spinner_item)
        viewBinding!!.spinnerMonths.adapter = adapterMonth

        val adapterMonthPassword =
            ArrayAdapter<String>(activity!!, R.layout.item_spinner_item, list)
        adapterMonthPassword.setDropDownViewResource(R.layout.item_spinner_item)
        viewBinding!!.spinnerChangePassword.adapter = adapterMonthPassword

        println("statusStr..--" + CSPreferences.readString(activity!!, "statusStr"))
        if (CSPreferences.readString(activity!!, "statusStr") == "1") {
            viewBinding!!.touchIdSwtch.isChecked = true
        } else {
            viewBinding!!.touchIdSwtch.isChecked = false
        }
        if (CSPreferences.readString(activity!!, "dsarStatus") == "1") {
            viewBinding!!.autoSaveDsar.isChecked = true
        } else {
            viewBinding!!.autoSaveDsar.isChecked = false
        }

        if (CSPreferences.readString(activity!!, "push_active_app_status") == "1") {
            viewBinding!!.swithPushActiveApps.isChecked = true
        } else {
            viewBinding!!.swithPushActiveApps.isChecked = false
        }

        for (i in 0 until list!!.size) {
            println("yaha_ayaa_0..." + CSPreferences.readString(activity!!, "notifyNotUsed"))
            if (CSPreferences.readString(activity!!, "notifyNotUsed")
                == list.get(i)
            ) {
                println("yaha_ayaa_..." + viewBinding!!.spinnerMonths.setSelection(i).toString())
                viewBinding!!.spinnerMonths.setSelection(i).toString()
            }
        }
        for (i in 0 until list!!.size) {
            println(
                "yaha_ayaa_0..changePassword." + CSPreferences.readString(
                    activity!!,
                    "changePassword"
                )
            )
            if (CSPreferences.readString(activity!!, "changePassword")
                == list.get(i)
            ) {
                println(
                    "yaha_ayaa_..." + viewBinding!!.spinnerChangePassword.setSelection(i).toString()
                )
                viewBinding!!.spinnerChangePassword.setSelection(i).toString()
            }
        }
        //if(CSPreferences.readString(activity!!, "notifyNotUsed").equals())
        viewBinding!!.touchIdSwtch.isChecked
        viewBinding!!.touchIdSwtch.setOnCheckedChangeListener { compoundButton, b ->
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //Fingerprint API only available on from Android 6.0 (M)
//                val fingerprintManager =
//                    activity!!.getSystemService(Context.FINGERPRINT_SERVICE) as? FingerprintManager
//                if (!fingerprintManager!!.isHardwareDetected) { // Device doesn't support fingerprint authentication
//                    Toast.makeText(activity!!, R.string.error_override_hw_unavailable, Toast.LENGTH_SHORT).show()
//                } else if (!fingerprintManager.hasEnrolledFingerprints()) { // User hasn't enrolled any fingerprints to authenticate with
//                    Toast.makeText(activity!!, R.string.error_override_hw_unavailable, Toast.LENGTH_SHORT).show()
//                } else { // Everything is ready for fingerprint authentication
//
//
//                }
//            }
            if (b) {
                statusStr = "1"
            } else {
                statusStr = "0"
            }
            if (Global_utility.isNetworkConnected(activity!!)) {
                touchid_StatusData(
                    CSPreferences.readString(activity!!, "session_id"),
                    activity!!,
                    statusStr!!
                )
                // appdata(activity!!)
            } else {
                Global_utility.showtost(
                    activity!!,
                    resources.getString(R.string.internet_connection)
                )
            }
        }
        viewBinding!!.autoSaveDsar.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                statusDsarStr = "1"
            } else {
                statusDsarStr = "0"
            }
            if (Global_utility.isNetworkConnected(activity!!)) {
                autosaveDsar(
                    CSPreferences.readString(activity!!, "session_id"),
                    activity!!,
                    statusDsarStr!!
                )
                // appdata(activity!!)
            } else {
                Global_utility.showtost(
                    activity!!,
                    resources.getString(R.string.internet_connection)
                )
            }
        }
        viewBinding!!.swithPushActiveApps.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                CSPreferences.putString(activity!!, "push_active_app_status","1")
             } else {
                CSPreferences.putString(activity!!, "push_active_app_status","0")
            }
        }


        if (Global_utility.isNetworkConnected(activity!!)) {
            getCountryResponseApi()
        } else {
            Global_utility.showtost(activity!!, resources.getString(R.string.internet_connection))
        }

      //  DownloadTask(activity!!, "http://167.172.209.57/privexec/public/pdf/dsar-7dc392c5.pdf")


        return views
    }

    private fun updateProfileCountry(countryStr: String) {
        val myappViewmodel = ViewModelProviders.of(this).get(MyAppVM::class.java)
        myappViewmodel.updateCountry(
            CSPreferences.readString(activity!!, "session_id"), "" + countryStr,
            activity!!
        )!!.observe(this, Observer {
            if (!it.getStatus()!!.equals("200")  && it.success==true) {
                CSPreferences.putString(
                    activity!!,
                    "user_country",
                    it.userDetail!!.getCountry().toString()
                )
showMessageDialog(""+it.message)
            } else
                if (it.getStatus()!!.equals(403)) {
                    it.message?.let { it1 -> Global_utility.showtost(activity!!, it1) }
                    CSPreferences.putString(activity!!, "session_id", "")
                    val intent = Intent(activity!!, Saplash::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    activity!!.overridePendingTransition(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                    )
                    activity!!.finish()
                } else {
                    it.message?.let { it1 -> Global_utility.showtost(activity!!, it1) }
                }
        })


    }

    private fun getCountryResponseApi() {
        val myappViewmodel = ViewModelProviders.of(this).get(MyAppVM::class.java)
        myappViewmodel.newCountryResponse(activity!!)!!.observe(this, Observer {
            if (!it.status!!.equals("200")) {
                for (i in 0 until it.countries!!.size) {
                    countrylist.add("" + it.countries!!.get(i).name)
                }

                Log.e("countrylist", "" + countrylist.size)
                if (countrylist.size > 0) {
                    val adapter =
                        ArrayAdapter<String>(activity!!, R.layout.item_spinner_item, countrylist)
                    adapter.setDropDownViewResource(R.layout.item_spinner_item)
                    viewBinding!!.spinnerCountry.adapter = adapter

                    for (i in 0 until countrylist.size) {
                        if (CSPreferences.readString(activity!!, "user_country")!!.toLowerCase()
                                .equals( countrylist.get(i).toLowerCase())
                        ) {
                            println(
                                "yaha_ayaa_... old Country="+CSPreferences.readString(activity!!, "user_country")+"   nowFetched=" +countrylist.get(i).toLowerCase()
                            )
                            viewBinding!!.spinnerCountry.setSelection(i).toString()
                        }
                    }

                }
            } else {
                it.message?.let { it1 -> Global_utility.showtost(activity!!, it1) }
            }
        })

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_headerTitle.setText("Preferences")
        img_headerback.setOnClickListener(this)
        fl_fragPreference2.setOnClickListener(this)
    }

    private fun notifyNotUsed(monthStr: String, sessionId: String?, activity: FragmentActivity) {
        val myappViewmodel = ViewModelProviders.of(this).get(MyAppVM::class.java)
        myappViewmodel.notifyNotUsed(sessionId, monthStr!!, activity)!!.observe(this, Observer {
            if (!it.getStatus()!!.equals("200")) {
                CSPreferences.putString(activity!!, "notifyNotUsed", monthStr!! + "Month")
            //    showMessageDialog("" + it.getMessage())
             //   Global_utility.showtost(activity,"Field updated")
                   it.getMessage()?.let { it1 ->}
            } else {
                it.getMessage()?.let { it1 -> Global_utility.showtost(activity, it1) }
            }
        })
    }

    private fun showMessageDialog(strMSg: String) {
    Log.e("preference","showMessageDialog= "+strMSg)
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_alert)
        dialog.tvMsg.text = "Field updated"//+strMSg

        dialog.btnNo.visibility = View.GONE
        dialog.btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.btnYes.setOnClickListener {
            dialog.dismiss()
        }
        dialog.btnYes.text = "OK"

        dialog.show()
    }

    private fun notifyChangePassword(
        changePassword: String,
        sessionId: String?,
        activity: FragmentActivity
    ) {
        val myappViewmodel = ViewModelProviders.of(this).get(MyAppVM::class.java)
        myappViewmodel.notifyChangePassword(sessionId, changePassword!!, activity)!!
            .observe(this, Observer {
                if (!it.getStatus()!!.equals("200")) {
                    CSPreferences.putString(
                        activity!!,
                        "changePassword",
                        changePassword!! + "Month"
                    )
                    showMessageDialog("" + it.getMessage())

/*    it.getMessage()?.let { it1 -> Global_utility.showtost(activity, it1) }*/
                } else {
                    it.getMessage()?.let { it1 -> Global_utility.showtost(activity, it1) }
                }
            })
    }

    private fun autosaveDsar(
        sessionId: String?,
        activity: FragmentActivity,
        statusDsarStr: String
    ) {
        val myappViewmodel = ViewModelProviders.of(this).get(MyAppVM::class.java)
        myappViewmodel.autosaveDsar(sessionId, statusDsarStr, activity)!!.observe(this, Observer {
            if (it.getStatus()!!.equals(200)) {
                CSPreferences.putString(activity!!, "dsarStatus", statusDsarStr!!)
                 showMessageDialog("" + it.getMessage())
      } else
                if (it.getStatus()!!.equals(403)) {
                    CSPreferences.putString(activity!!, "session_id", "")
                    val intent = Intent(activity!!, Saplash::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    activity!!.overridePendingTransition(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                    )
                    activity!!.finish()
                } else {
                    it.getMessage()?.let { it1 -> Global_utility.showtost(activity, it1) }
                }
        })
    }

    //http://167.172.209.57/privexec/api/touchid_status
    private fun touchid_StatusData(sessionId: String?, context: Context, statusStr: String) {
        val myappViewmodel = ViewModelProviders.of(this).get(MyAppVM::class.java)
        myappViewmodel.appTouchdata(context, sessionId, statusStr)!!.observe(this, Observer {
            if (it.getStatus()!!.equals(200)) {
                CSPreferences.putString(activity!!, "statusStr", statusStr)
                println(
                    "statusStr..0--" + statusStr + " ..2.." + CSPreferences.readString(
                        activity!!,
                        "statusStr"
                    )
                )
              /*  it.getMessage()?.let { it1 -> Global_utility.showtost(context, it1) }*/
              //  Global_utility.showtost(context,"Field updated")
             //   showMessageDialog(it.getMessage()!!)
            } else {
                it.getMessage()?.let { it1 -> Global_utility.showtost(context, it1) }
            }

        })
    }


    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }


    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun setData(body: App_dataPojo) {
        CSPreferences.putString(activity!!, "notifyNotUsed", monthStr!!)
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_headerback -> {
                Global_utility.hideKeyboardView(fl_fragPreference2, activity!!)
                val manager: FragmentManager = activity!!.supportFragmentManager
                manager.popBackStack()
            }

            R.id.fl_fragPreference2 -> {
                Global_utility.hideKeyboardView(fl_fragPreference2, activity!!)
            }
        }
    }


}
