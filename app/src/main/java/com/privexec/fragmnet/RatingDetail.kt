package com.privexec.fragmnet

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.github.mikephil.charting.utils.EntryXComparator
import com.lee_da_hang_pte_ltd.utills.CSPreferences
import com.lee_da_hang_pte_ltd.utills.Global_utility
import com.privexec.Api_Call.Rating_interface

import com.privexec.R
import com.privexec.activity.Home_Activity
import com.privexec.activity.Saplash
import com.privexec.adapter.Rating_Adapter
import com.privexec.databinding.FragmentRatingDetailBinding
import com.privexec.otherutility.Global_Class
import com.privexec.pojoclass.All_ratingPojo
import com.privexec.viewmodel.MyAppVM
import com.privexec.viewmodel.RatingDetailVM
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_rating_detail.*
import kotlinx.android.synthetic.main.header.*
import okhttp3.RequestBody
import java.lang.Exception
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RatingDetail.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RatingDetail.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class RatingDetail : Fragment(), Rating_interface, View.OnClickListener {
    override fun getrating(list: List<All_ratingPojo.Rating>) {


    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var TAG = "RatingDetail "
    private var ratingDetailBinding: FragmentRatingDetailBinding? = null
    private var views: View? = null
    private var ratingAdapter: Rating_Adapter? = null
    var ratingDetail: All_ratingPojo.Rating? = null
    var strComeFrom:String =""
    private var ratinglist = arrayListOf<All_ratingPojo.Rating.AllReview>()


    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(
            detailrating: All_ratingPojo.Rating,
            arrayList: ArrayList<All_ratingPojo.Rating.AllReview>
        ,from:String) =
            RatingDetail().apply {
                arguments = Bundle().apply {
                    ratingDetail = detailrating
                    ratinglist = arrayList!!
                    strComeFrom=from
                }
            }
    }

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

        ratingDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_rating_detail, container, false)
        views = ratingDetailBinding!!.getRoot()


        ratingDetailBinding!!.reviwelist.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        ratingDetailBinding!!.reviwelist.setHasFixedSize(true)
        ratingDetailBinding!!.reviwelist.setItemAnimator(DefaultItemAnimator())

        ratingAdapter = Rating_Adapter(activity!!, ratinglist)
        ratingDetailBinding!!.reviwelist.adapter = ratingAdapter
        if(ratingAdapter!!.itemCount>0)
        {
            ratingDetailBinding!!.tvNoReview.visibility=View.GONE
        }
         if ((ratingDetail!!.getDetail()!!.get5StarsCount() != null)  ) {
        try {
            var seekValue =
                ((ratingDetail!!.getDetail()!!.get5StarsCount()!!) / (ratingDetail!!.getDetail()!!
                    .getReviewCount()!!) * 100)
            ratingDetailBinding!!.seekbar5.progress = seekValue
        }
        catch (excep :Exception)
        {

        }
        }

        if (ratingDetail!!.getDetail()!!.get4StarsCount() != null) {
        try{    var seekValue =
                ((ratingDetail!!.getDetail()!!.get4StarsCount()!!) / (ratingDetail!!.getDetail()!!
                    .getReviewCount()!!) * 100)
            ratingDetailBinding!!.seekbar4.progress = seekValue
        }
        catch (excep :Exception)
        {

        }
        }
        if (ratingDetail!!.getDetail()!!.get3StarsCount() != null) {
        try{    var seekValue =
                ((ratingDetail!!.getDetail()!!.get3StarsCount()!!) / (ratingDetail!!.getDetail()!!
                    .getReviewCount()!!) * 100)
            ratingDetailBinding!!.seekbar3.progress = seekValue
        }
        catch (excep :Exception)
        {

        } }
        if (ratingDetail!!.getDetail()!!.get2StarsCount() != null) {
       try{     var seekValue =
                ((ratingDetail!!.getDetail()!!.get2StarsCount()!!) / (ratingDetail!!.getDetail()!!
                    .getReviewCount()!!) * 100)
            ratingDetailBinding!!.seekbar2.progress = seekValue
       }
       catch (excep :Exception)
       {

       }
        }
        if (ratingDetail!!.getDetail()!!.get1StarsCount() != null) {
     try{       var seekValue =
                ((ratingDetail!!.getDetail()!!.get1StarsCount()!!) / (ratingDetail!!.getDetail()!!
                    .getReviewCount()!!) * 100)
            ratingDetailBinding!!.seekbar1.progress = seekValue
     }
     catch (excep :Exception)
     {

     }
        }


        if (ratingDetail!!.getDetail()!!.getReviewCount() != null) {
            ratingDetailBinding!!.totalrating.text =
                "" + ratingDetail!!.getDetail()!!.getReviewCount()!! + " Rating"
        }
        if (ratingDetail!!.getAvgRating() != null) {
            try {
                ratingDetailBinding!!.totalavarag.text = "" + ratingDetail!!.getAvgRating()!!
                ratingDetailBinding!!.ratingbar!!.rating = ratingDetail!!.getAvgRating()!!.toFloat()
            } catch (except: Exception) {
                Log.d(TAG, "except=   " + except.toString())
            }
        }

        if (!ratingDetail!!.getTitle().isNullOrEmpty()) {

            ratingDetailBinding!!.textApp!!.text = ratingDetail!!.getTitle()
if(!(ratingDetail!!.getappFullTitle().equals(""))  &&  !(ratingDetail!!.getappFullTitle().equals(null)))
{
    ratingDetailBinding!!.textApp!!.text = ratingDetail!!.getappFullTitle()

}
            try {
                var strAppPkgName = "" +ratingDetail!!.getAndroidPackageName()
                strAppPkgName = strAppPkgName!!.replace("\"", "")
                strAppPkgName = strAppPkgName!!.replace("\"", "")
                val icon =
                   /* getAppIconByPackageName(strAppPkgName, activity!!)*/
                  (activity as Activity)!!.applicationContext.getPackageManager()
                    .getApplicationIcon(strAppPkgName)
                ratingDetailBinding!!.imageApp!!.setImageDrawable(icon)

            } catch (excep: Exception) {
                Glide.with(activity!!).load(ratingDetail!!.getImage())
                    .into(ratingDetailBinding!!.imageApp)

                Log.e(
                    ""+TAG,
                    "getAppIconByPackageName()  excep=" + excep.toString()
                )
            }
        }

if(strComeFrom.equals("searchAppForRate"))
{
    ratingDetailBinding!!.ratapp.visibility=View.GONE
}
        ratingDetailBinding!!.ratapp.setOnClickListener {
            Global_Class.fragment(
                activity!!,
                RatingSave.newInstance(ratingDetail!!.getId().toString(), ratingDetail!!),
                true
            )
        }
        ratingDetailBinding!!.seekbar5.isEnabled = false
        ratingDetailBinding!!.seekbar4.isEnabled = false
        ratingDetailBinding!!.seekbar3.isEnabled = false
        ratingDetailBinding!!.seekbar2.isEnabled = false
        ratingDetailBinding!!.seekbar1.isEnabled = false
        return views
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        img_headerback.setOnClickListener(this)
        tv_headerTitle.setText("Privacy Ratings")
        fl_fragRatingDetails.setOnClickListener(this)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
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
            return null/*            ContextCompat.getDrawable(
                (activity as Activity)!!.applicationContext,
                R.mipmap.ic_launcher
            )*/

        }
        if (!excep.equals(""))
            Log.e("Myapp_adapter", "getAppIconByPackageName()  excep=" + excep.toString())


        return drawable
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        /*single_rating*/
        Log.e(TAG,"onAttach")
//  callSingleAppRating()

    }
/*
    private fun callSingleAppRating() {
*//*RatingDetail.newInstance(ratinglist.get(position),
                ratinglist[position].getDetail()!!.getAllReviews() as ArrayList<All_ratingPojo.Rating.AllReview>*//*

             val reating = ViewModelProviders.of(this).get(RatingDetailVM::class.java)

            reating.getSingleAppRating(CSPreferences.readString(activity!!, "session_id")!!,""+ratingDetail!!.getId(), activity!!)!!.observe(this, Observer {
                if (it.getStatus()!!.equals(200)) {
              //      rating_interface.getrating(it.getRating()!!)
                    val intent = Intent(activity!!, Home_Activity::class.java)
                    intent.addFlags(   Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                  intent.putExtra("from","rating")
                    startActivity(intent)
                    activity!!.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    activity!!.finish()

                } else
                    if (it.getStatus()!!.equals(403))
                    {
                        it.getMessage()?.let { it1 -> Global_utility.showtost(activity!!, it1) }
                        CSPreferences.putString(activity!!, "session_id","")
                        val intent = Intent(activity!!, Saplash::class.java)
                        intent.addFlags(   Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        activity!!.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        activity!!.finish()
                    } else {


                        it.getMessage()?.let { it1 -> Global_utility.showtost(activity!!, it1) }
                    }
            })



    }*/


    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fl_fragRatingDetails -> {

            }
            R.id.img_headerback -> {
                Global_utility.hideKeyboardView(fl_fragRatingDetails, activity!!)
                val manager: FragmentManager = activity!!.supportFragmentManager
                manager.popBackStack()
            }

        }
    }


}

