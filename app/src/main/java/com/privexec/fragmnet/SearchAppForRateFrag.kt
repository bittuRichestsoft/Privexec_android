package com.privexec.fragmnet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
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
import com.privexec.adapter.PrivacyRating_Adapter
import com.privexec.adapter.SearchedAppForRate_Adapter
import com.privexec.databinding.FragmentSearchAppForRateBinding
import com.privexec.otherutility.Global_Class
import com.privexec.pojoclass.All_ratingPojo
import com.privexec.pojoclass.SearchedAppsPojo
import com.privexec.viewmodel.RatingDetailVM
import kotlinx.android.synthetic.main.fragment_privacy_rating.*
import kotlinx.android.synthetic.main.fragment_search_app_for_rate.*
import kotlinx.android.synthetic.main.header.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchAppForRateFrag : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var TAG = "SearchAppForRateFrag "
    private var listener: PrivacyRating.OnFragmentInteractionListener? = null
    private var searchAppBinding: FragmentSearchAppForRateBinding? = null
    private var views: View? = null
   /* private var searchAppAdapter: SearchedAppForRate_Adapter? = null
    private var searchAppList = arrayListOf<SearchedAppsPojo.Result>()*/
   private var searchAppAdapter: PrivacyRating_Adapter? = null

    private var searchAppList = arrayListOf<All_ratingPojo.Rating>()

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
        // Inflate the layout for this fragment
        searchAppBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_search_app_for_rate,
                container,
                false
            )
        views = searchAppBinding!!.getRoot()

        searchAppBinding!!.rvSearchResult.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        searchAppBinding!!.rvSearchResult.setHasFixedSize(true)
        searchAppBinding!!.rvSearchResult.setItemAnimator(DefaultItemAnimator())

      //  searchAppAdapter = SearchedAppForRate_Adapter(activity!!, searchAppList!!)

        searchAppAdapter = PrivacyRating_Adapter(activity!!, searchAppList!!,"searchAppForRate")

        searchAppBinding!!.rvSearchResult.adapter = searchAppAdapter


        searchAppBinding!!.swpRefLyt.setOnRefreshListener {
            searchAppBinding!!.swpRefLyt.isRefreshing = false
            if (Global_utility.isNetworkConnected(activity!!)) {
                iv_search.callOnClick()
            } else {
                Global_utility.showtost(
                    activity!!,
                    resources.getString(R.string.internet_connection)
                )
            }
        }
        return views
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        img_headerback.setOnClickListener(this)
        tv_headerTitle.setText("Search App")
        fl_fragSearchApp.setOnClickListener(this)
        iv_search.setOnClickListener(this)
/*
        tv_sendEmail.visibility = View.VISIBLE
        tv_sendEmail.setText("Done")
        tv_sendEmail.setOnClickListener(this)
*/

    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchAppForRateFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fl_fragSearchApp -> {
                Global_utility.hideKeyboardView(fl_fragSearchApp, activity!!)
            }
            R.id.img_headerback -> {
                Global_utility.hideKeyboardView(fl_fragSearchApp, activity!!)
                Home_Activity.toolbarTitle = "searchAppForRAteFrag"
                /*    val manager: FragmentManager = activity!!.supportFragmentManager
                    manager.popBackStack()*/
                activity!!.onBackPressed()
            }

            R.id.tv_sendEmail -> {
                img_headerback.callOnClick()
                /*var onlyCheckedApps = arrayListOf<SearchedAppsPojo.Result>()

if(searchAppList.size>0)
{
    for(i in 0..(searchAppList.size-1))
    {

        if(searchAppList.get(i).isSelectedChk==true)
        {
            onlyCheckedApps.add(searchAppList.get(i))
        }
    }

}
if(onlyCheckedApps.size>0)
{
    Toast.makeText(activity!!, "selected apps="+onlyCheckedApps.size, Toast.LENGTH_LONG).show()

}
                else
{
    Toast.makeText(activity!!, "Please select at least one app", Toast.LENGTH_LONG).show()

}
                Log.e(  TAG,"selected apps="+onlyCheckedApps.size )

*/

            }

            R.id.iv_search -> {
                if (et_search.text.toString().replace(" ", "").equals("")) {
                    Toast.makeText(
                        activity!!,
                        "Please enter app name then search",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    if (Global_utility.isNetworkConnected(activity!!)) {
                        callSeachApps(
                            et_search.text.toString(),
                            CSPreferences.readString(activity!!, "session_id").toString(),
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
    }

    private fun callSeachApps(
        strAppName: String, sessin_id: String,
        context: Context
    ) {

        val reating = ViewModelProviders.of(this).get(RatingDetailVM::class.java)

        reating.searchAppForRate(context, strAppName, sessin_id)!!.observe(this, Observer {
            if (it.getStatus()!!.equals(200)) {

                searchAppList!!.clear()
                searchAppList!!.addAll(it.getRating()!!)

                if(searchAppList.size>0)
                {
                    searchAppAdapter = PrivacyRating_Adapter(activity!!, searchAppList!!,"searchAppForRate")
                    searchAppBinding!!.rvSearchResult!!.adapter!!.notifyDataSetChanged()

                }
                else{
                    Global_utility.showtost(
                        activity!!,
                     ""+it.getMessage()
                    )
                }

            } else
                if (it.getStatus()!!.equals(403)) {
                    it.getStatus()?.let { it1 -> Global_utility.showtost(context, ""+it.getMessage()) }
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

}