package com.privexec.fragmnet

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lee_da_hang_pte_ltd.utills.CSPreferences
import com.lee_da_hang_pte_ltd.utills.Global_utility
import com.privexec.Api_Call.WebAPicall

import com.privexec.R
import com.privexec.activity.Home_Activity
import com.privexec.activity.Saplash
import com.privexec.activity.WebViewDataActivity
import com.privexec.viewmodel.LoginVM
import com.privexec.viewmodel.MyAppVM
import kotlinx.android.synthetic.main.fragment_preferences_2.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_tutroial.*
import kotlinx.android.synthetic.main.header.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Tutroial.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Tutroial.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class Tutroial : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null


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
        return inflater.inflate(R.layout.fragment_tutroial, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fl_fragTutorial.setOnClickListener(this)
        tv_headerTitle.setText("Tutorial")
        img_headerback.setOnClickListener(this)
        btn_howItWork.setOnClickListener(this)
        btn_faq.setOnClickListener(this)
        btn_resources.setOnClickListener(this)
        btn_about.setOnClickListener(this)
        btn_contact.setOnClickListener(this)
        getVideoApi();


    }

    private fun getVideoApi() {

        //   http://privexec.net/api/getTutorial

        val myappViewmodel = ViewModelProviders.of(this).get(MyAppVM::class.java)
        myappViewmodel.tutorialVideoResponse(activity!!)!!.observe(this, Observer {
            if (!it.status!!.equals("200")) {
                println(
                    "getVideoApi...  =" +it.video_url)
                setVideo(""+it.video_url)
            }
            else   if (it.status!!.equals("403")) {
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
            }
            else {
                it.message?.let { it1 -> Global_utility.showtost(activity!!, it1) }
            }
        })


    }

    private fun setVideo(vdoUri: String) {
        iv_tutorial.visibility=View.VISIBLE

Handler().postDelayed(
    {
        if (Global_utility.isNetworkConnected(activity!!)) {
            iv_tutorial.visibility=View.GONE
            webVw_videoVw.visibility=View.VISIBLE
            webVw_videoVw.webViewClient = WebViewClient()
            webVw_videoVw.settings.javaScriptEnabled = true
            webVw_videoVw.settings.javaScriptCanOpenWindowsAutomatically = true
            webVw_videoVw.settings.pluginState = WebSettings.PluginState.ON
            webVw_videoVw.settings.mediaPlaybackRequiresUserGesture = false
            webVw_videoVw.setWebChromeClient(WebChromeClient())

            webVw_videoVw.getSettings().setDomStorageEnabled(true);
            webVw_videoVw.loadUrl(/*WebAPicall.BASE_URL_IP+"/public/videos/tutorialVideo.mp4"*/vdoUri )
         }
        else{
            iv_tutorial.visibility=View.VISIBLE
            webVw_videoVw.visibility=View.GONE
        }
    },3000
)

      /*  var videiourl =  "http://167.172.209.57/privexec/public/videos/1280.mp4"
            .replace("[", "")  //remove the right bracket
            .replace("]", "")  //remove the left bracket
            .trim()

        println("printvideiourl.." + videiourl)
        val uriPath = Uri.parse(videiourl)
         videoVw.setVideoURI(uriPath)
        videoVw.start()
        videoVw?.setOnPreparedListener(object :
            MediaPlayer.OnPreparedListener {
            override fun onPrepared(mp: MediaPlayer) {
            }
        })*/

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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Tutroial.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Tutroial().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.img_headerback ->
            {
                Global_utility.hideKeyboardView(fl_fragTutorial, activity!!)
                val manager: FragmentManager = activity!!.supportFragmentManager
                manager.popBackStack()
            }

            R.id.fl_fragTutorial ->
            {
                Global_utility.hideKeyboardView(fl_fragTutorial, activity!!)
            }
            R.id.btn_howItWork ->
            {
                val intent = Intent(activity!!, WebViewDataActivity::class.java)
                var bundle = Bundle()
                bundle.putString("title","How It Works")
                bundle.putString("url", WebAPicall.BASE_URL_WithApi+"how_it_works")
                intent.putExtras(bundle)
                startActivity(intent)
                activity!!.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            R.id.btn_faq ->
            {
                val intent = Intent(activity!!, WebViewDataActivity::class.java)
                var bundle = Bundle()
                bundle.putString("title","FAQ")
                bundle.putString("url",WebAPicall.BASE_URL_WithApi+"faq")
                intent.putExtras(bundle)
                startActivity(intent)
                activity!!.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            R.id.btn_resources ->
            {
                val intent = Intent(activity!!, WebViewDataActivity::class.java)
                var bundle = Bundle()
                bundle.putString("title","Resources")
                bundle.putString("url",WebAPicall.BASE_URL_WithApi+"resources")
                intent.putExtras(bundle)
                startActivity(intent)
                activity!!.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            R.id.btn_about ->
            {
                val intent = Intent(activity!!, WebViewDataActivity::class.java)
                var bundle = Bundle()
                bundle.putString("title","About")
                bundle.putString("url",WebAPicall.BASE_URL_WithApi+"about")
                intent.putExtras(bundle)
                startActivity(intent)
                activity!!.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            R.id.btn_contact ->
            {
                val intent = Intent(activity!!, WebViewDataActivity::class.java)
                var bundle = Bundle()
                bundle.putString("title","Contact Us")
                bundle.putString("url",WebAPicall.BASE_URL_WithApi+"contact_us")
                intent.putExtras(bundle)
                startActivity(intent)
                activity!!.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
    }
}
