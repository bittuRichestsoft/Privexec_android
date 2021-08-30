package com.privexec.fragmnet

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.lee_da_hang_pte_ltd.utills.CSPreferences
import com.lee_da_hang_pte_ltd.utills.Global_utility
import com.privexec.Api_Call.Rating_interface
import com.privexec.R
import com.privexec.activity.Home_Activity
import com.privexec.activity.Saplash
import com.privexec.adapter.Myapp_adapter
import com.privexec.adapter.PrivacyRating_Adapter
import com.privexec.databinding.FragmentPrivacyRatingBinding
import com.privexec.otherutility.Global_Class
import com.privexec.pojoclass.All_ratingPojo
import com.privexec.pojoclass.App_dataPojo
import com.privexec.viewmodel.RatingDetailVM
import kotlinx.android.synthetic.main.fragment_privacy_rating.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.header.*
import kotlinx.android.synthetic.main.item_myapp_row.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PrivacyRating.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PrivacyRating.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PrivacyRating : Fragment(), Rating_interface, View.OnClickListener {
    override fun getrating(list: List<All_ratingPojo.Rating>) {
        ratinglist.clear()
        ratinglist.addAll(list)
        privacyRatingBinding!!.privacyrating.adapter!!.notifyDataSetChanged()
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var TAG = "PrivacyRating "

    private var privacyRatingBinding: FragmentPrivacyRatingBinding? = null
    private var views: View? = null
    private var privacyratingAdapter: PrivacyRating_Adapter? = null

    private var ratinglist = arrayListOf<All_ratingPojo.Rating>()
    var localAppList = arrayListOf<All_ratingPojo.Rating>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        privacyRatingBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_privacy_rating, container, false)
        views = privacyRatingBinding!!.getRoot()

        privacyRatingBinding!!.privacyrating.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        privacyRatingBinding!!.privacyrating.setHasFixedSize(true)
        privacyRatingBinding!!.privacyrating.setItemAnimator(DefaultItemAnimator())

        privacyratingAdapter = PrivacyRating_Adapter(activity!!, ratinglist,"privacyRating")
        privacyRatingBinding!!.privacyrating.adapter = privacyratingAdapter

        if (Global_utility.isNetworkConnected(activity!!)) {
            get_rating(CSPreferences.readString(activity!!, "session_id")!!, activity!!, this)
        } else {
            Global_utility.showtost(activity!!, resources.getString(R.string.internet_connection))
        }
        privacyRatingBinding!!.swpRefLyt.setOnRefreshListener {
            privacyRatingBinding!!.swpRefLyt.isRefreshing = false
            if (Global_utility.isNetworkConnected(activity!!)) {
                get_rating(CSPreferences.readString(activity!!, "session_id")!!, activity!!, this)
            } else {
                Global_utility.showtost(
                    activity!!,
                    resources.getString(R.string.internet_connection)
                )
            }
        }

        return views
    }

    fun callAndCheckApiForRate() {
        if (Global_utility.isNetworkConnected(activity!!)) {
            get_rating(CSPreferences.readString(activity!!, "session_id")!!, activity!!, this)
        } else {
            Global_utility.showtost(
                activity!!,
                resources.getString(R.string.internet_connection)
            )
        }


    }

    private fun setSimpleAppsInAdap() {

        localAppList = arrayListOf<All_ratingPojo.Rating>()
        localAppList.clear()
        var packageLst = ""
        for (localSvdIndx in 0..(Home_Activity.jsonarray!!.size() - 1)) {


            var jsObject = Home_Activity.jsonarray.get(localSvdIndx)
            var package_name = jsObject.asJsonObject.getAsJsonPrimitive("package_name").toString()
            package_name = package_name!!.replace("\"", "")
            package_name = package_name!!.replace("\"", "")

            var app_name = "" + jsObject.asJsonObject.getAsJsonPrimitive("app_name")
            app_name = app_name!!.replace("\"", "")
            app_name = app_name!!.replace("\"", "")

       /*     var lastupdate = "" + jsObject.asJsonObject.getAsJsonPrimitive("lastupdate")
            var app_icon_img = ""// +it.getResult()!!.get(i).app_img
            var firstupdate = "" + jsObject.asJsonObject.getAsJsonPrimitive("firstupdate")
*/
            var resultObj = All_ratingPojo.Rating()
            resultObj.setAndroidPackageName(package_name)
            resultObj.setTitle("" + app_name)
            packageLst = packageLst + " <<>> " + package_name


            resultObj.setAvgRating("0")

            for (dbApps in 0..(ratinglist!!.size - 1)) {

                if (package_name.equals(ratinglist!!.get(dbApps).getAndroidPackageName())) {
                    resultObj=ratinglist!!.get(dbApps)
//                    resultObj.setAvgRating(ratinglist!!.get(dbApps).getAvgRating())

                }


            }
            Log.e(
                TAG,
                "addingDataForAdap    title=${resultObj.getTitle()}    package_name=$package_name     getAvgRating=${resultObj.getAvgRating()}"
            )
            localAppList.add(resultObj)

        }
Log.e(TAG,"localAppList.size="+localAppList.size)
        Collections.sort(localAppList!!, object : Comparator<All_ratingPojo.Rating> {
            override fun compare(
                lhs: All_ratingPojo.Rating,
                rhs: All_ratingPojo.Rating
            ): Int {
                return lhs.getTitle().toString().toLowerCase()
                    .compareTo(rhs.getTitle().toString().toLowerCase())

            }
        })

        Log.e(
            TAG,
            "setSimpleAppsInAdap_packageLst=${packageLst} "
        )
        privacyratingAdapter = PrivacyRating_Adapter(activity!!, localAppList,"privacyRating")
        privacyRatingBinding!!.privacyrating.adapter = privacyratingAdapter
        privacyratingAdapter!!.notifyDataSetChanged()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        img_headerback.setOnClickListener(this)
        tv_headerTitle.setText("Privacy Ratings")
        fl_fragPrivacyRating.setOnClickListener(this)
        iv_question.visibility = View.VISIBLE
        iv_question.setImageResource(R.drawable.ic_search)
        iv_question.setOnClickListener(this)
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
            PrivacyRating().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    // Api call get ratong
    private fun get_rating(
        sessionid: String,
        context: Context,
        rating_interface: Rating_interface
    ) {
        val reating = ViewModelProviders.of(this).get(RatingDetailVM::class.java)
        reating.get_rating(sessionid, context)!!.observe(this, Observer {
            if (it.getStatus()!!.equals(200)) {
                /*       ratinglist =  arrayListOf<All_ratingPojo.Rating>()
                       ratinglist.clear()
                       ratinglist.addAll(it.getRating()!!)
       */
                Log.e(TAG, "getRating Size=" + it.getRating()!!.size)
                Collections.sort(it.getRating()!!, object : Comparator<All_ratingPojo.Rating> {
                    override fun compare(
                        lhs: All_ratingPojo.Rating,
                        rhs: All_ratingPojo.Rating
                    ): Int {
                        return lhs.getTitle().toString().toLowerCase()
                            .compareTo(rhs.getTitle().toString().toLowerCase())
                    }
                })

                if (CSPreferences.readString(activity!!, "push_active_app_status") == "1") {
                    ratinglist.clear()
                    ratinglist.addAll(it.getRating()!!)
                    setSimpleAppsInAdap()
/*
                    for (localAppIndx in 0..(localAppList2!!.size   - 1)) {
                        var     localAppList = arrayListOf<All_ratingPojo.Rating>()
                        localAppList.clear()
                        var packageLst=""
//                        var jsObject= Home_Activity.jsonarray.get(i)
                        var strPkgName = "" + localAppList2.get(localAppIndx)
                        var resultObj = All_ratingPojo.Rating()
                        resultObj.setAndroidPackageName("" + strPkgName)
                        resultObj.setAvgRating("0"  )
                        for (savedAppIndx in 0..(it.getRating()!!.size   - 1)) {

                            if (it.getRating()!!.get(savedAppIndx)
                                    .getAndroidPackageName().toString().equals(strPkgName)
                            ) {

                                resultObj.setAvgRating("" + it.getRating()!!.get(savedAppIndx)
                                    .getAvgRating())
                                Log.e(
                                    TAG,
                                    "setSimpleAppsInAdap    title=${resultObj.getTitle()}    package_name=$strPkgName"
                                )

                                localAppList.add(resultObj)
                            }
                        }
                    }
*/
                } else {

                    rating_interface.getrating(it.getRating()!!)
                }

            } else
                if (it.getStatus()!!.equals(403)) {
                    it.getMessage()?.let { it1 -> Global_utility.showtost(context, it1) }
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


                    it.getMessage()?.let { it1 -> Global_utility.showtost(context, it1) }
                }
        })


    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fl_fragPrivacyRating -> {
                Global_utility.hideKeyboardView(fl_fragPrivacyRating, activity!!)
            }
            R.id.img_headerback -> {
                Global_utility.hideKeyboardView(fl_fragPrivacyRating, activity!!)
                val manager: FragmentManager = activity!!.supportFragmentManager
                manager.popBackStack()
            }

            R.id.iv_question -> {
                Global_Class.fragment(
                    activity!!,
                    SearchAppForRateFrag.newInstance("", ""),
                    true
                )
            }
        }
    }
}
