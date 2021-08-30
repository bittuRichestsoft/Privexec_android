package com.privexec.fragmnet

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lee_da_hang_pte_ltd.utills.CSPreferences
import com.lee_da_hang_pte_ltd.utills.Global_utility
import com.privexec.R
import com.privexec.activity.Home_Activity
import com.privexec.activity.Saplash
import com.privexec.pojoclass.All_ratingPojo
import com.privexec.viewmodel.RatingDetailVM
import kotlinx.android.synthetic.main.activity_privacy_policies.*
import kotlinx.android.synthetic.main.fragment_rating_submit.*
import kotlinx.android.synthetic.main.header.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"
private const val ARG_PARAM4 = "param4"


class RatingSubmitFrag : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var strHoldAppId: String? = null
    private var strHoldRating1: String? = null
    private var strHoldRating2: String? = null
    private var strHoldRating3: String? = null
    private var strHoldReview: String? = null
    var TAG = "RatingSubmitFrag "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            strHoldAppId = "" + ratingPojoData!!.getId()
            strHoldRating1 = it.getString(ARG_PARAM1)
            strHoldRating2 = it.getString(ARG_PARAM2)
            strHoldRating3 = it.getString(ARG_PARAM3)
            strHoldReview = it.getString(ARG_PARAM4)
        }
    }

    private fun callSubmitRatingApi(hashMap: HashMap<String, String>, context: Context) {
        val reating = ViewModelProviders.of(this).get(RatingDetailVM::class.java)

        reating.sendrating(hashMap, context)!!.observe(this, Observer {
            if (it.getStatus()!!.equals(200)) {
                Log.d(TAG, "" + TAG)
                val intent = Intent(activity!!, Home_Activity::class.java)
           //     intent.addFlags(   Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.putExtra("from","rating")
                startActivity(intent)
                activity!!.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                activity!!.finish()


            } else
                if (it.getStatus()!!.equals(403)) {
                    it.getMessage()?.let { it1 ->
                        Global_utility.showtost(context, it1)
                    }

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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rating_submit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_sendRatingData.setOnClickListener {
            if (Global_utility.isNetworkConnected(activity!!)) {
                val hashMap: HashMap<String, String> =
                    HashMap<String, String>() //define empty hashmap            hashMap!!.put("name","dsasdsa")
                hashMap.put("session_id", CSPreferences.readString(activity!!, "session_id")!!)
                hashMap.put("app_id", strHoldAppId!!)
                hashMap.put("rate_1", strHoldRating1!!)
                hashMap.put("rate_2", strHoldRating2!!)
                hashMap.put("rate_3", strHoldRating3!!)
                hashMap.put("review", strHoldReview!!)

                callSubmitRatingApi(hashMap, activity!!)
            } else {
                Global_utility.showtost(
                    activity!!,
                    resources.getString(R.string.internet_connection)
                )

            }


        }

        tv_videoBottomTitleTxt.text = "" + ratingPojoData!!.getCompanyName()

        tv_headerTitle.setText("" + ratingPojoData!!.getTitle())
        img_headerback.setOnClickListener(this)
        fl_fragRatingSubmit.setOnClickListener(this)

        getVideo_Here()

        if (ratingPojoData!!.getPrivacyPolicy() != null) {
            discraption.setText("" + ratingPojoData!!.getPrivacyPolicy())
            discraption.visibility=View.GONE
            webVw_ratingPrivacy?.loadUrl(""+ ratingPojoData!!.getPrivacyPolicy())
            webVw_ratingPrivacy?.webViewClient = MyWebViewClient(activity!!)

        }
    }
    inner class MyWebViewClient internal constructor(private val activity: Activity) : WebViewClient() {

        @SuppressLint("NewApi")
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            val url: String = request?.url.toString();
            Global_utility.showDialog(activity)
            progressBar?.visibility = View.VISIBLE
            view?.loadUrl(url)
            return true
        }

        override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
            Global_utility.showDialog(activity)

            webView.loadUrl(url)
            return true
        }

        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
            Toast.makeText(activity, "Got Error! $error", Toast.LENGTH_SHORT).show()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            try {
                Global_utility.hideDialog(activity)
            }
            catch (exp :Exception)
            {

            }
            progressBar?.visibility = View.GONE

        }

    }

    private fun getVideo_Here() {

        var videiourl = ratingPojoData!!.getVideoUrl().toString()
            .replace("[", "")  //remove the right bracket
            .replace("]", "")  //remove the left bracket
            .trim();

        println("printvideiourl.." + videiourl)
        val uriPath = Uri.parse(videiourl)
        vdoVw_submitRate.setVideoURI(uriPath);

        vdoVw_submitRate.start();
//                    dsar_banding!!.video.pause()
//                    dsar_banding!!.video.canPause()
//                    dsar_banding!!.video.canSeekForward()
//                    dsar_banding!!.video.canSeekBackward();
        vdoVw_submitRate?.setOnPreparedListener(object :
            MediaPlayer.OnPreparedListener {
            override fun onPrepared(mp: MediaPlayer) {
                //progressbar?.setVisibility(View.GONE);
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RatingSubmitFrag.
         */
        var ratingPojoData: All_ratingPojo.Rating? = null

        @JvmStatic
        fun newInstance(
            detailRatingPojoData: All_ratingPojo.Rating,
            ratingOne: String,
            ratingTwo: String,
            ratingThree: String,
            review: String
        ) =
            RatingSubmitFrag().apply {
                arguments = Bundle().apply {

                    putString(ARG_PARAM1, ratingOne)
                    putString(ARG_PARAM2, ratingTwo)
                    putString(ARG_PARAM3, ratingThree)
                    putString(ARG_PARAM4, review)
                    ratingPojoData = detailRatingPojoData

                }
            }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_headerback -> {
                Global_utility.hideKeyboardView(fl_fragRatingSubmit, activity!!)
                val manager: FragmentManager = activity!!.supportFragmentManager
                manager.popBackStack()
            }

            R.id.fl_fragRatingSubmit -> {
                Global_utility.hideKeyboardView(fl_fragRatingSubmit, activity!!)
            }
        }
    }
}