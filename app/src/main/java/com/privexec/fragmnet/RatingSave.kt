package com.privexec.fragmnet

import android.app.Activity
import android.app.Dialog
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
import android.view.Window
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.lee_da_hang_pte_ltd.utills.CSPreferences
import com.lee_da_hang_pte_ltd.utills.Global_utility
import com.privexec.Api_Call.Rating_interface

import com.privexec.R
import com.privexec.activity.Home_Activity
import com.privexec.activity.Saplash
import com.privexec.databinding.FragmentSendRatingBinding
import com.privexec.otherutility.Global_Class
import com.privexec.pojoclass.All_ratingPojo
import com.privexec.viewmodel.RatingDetailVM
import com.willy.ratingbar.BaseRatingBar
import kotlinx.android.synthetic.main.dialog_alert.*
import kotlinx.android.synthetic.main.fragment_rating_detail.*
import kotlinx.android.synthetic.main.fragment_send_rating.*
import kotlinx.android.synthetic.main.header.*
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RatingSave : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var TAG="TAG SendRating "
var sendRating : FragmentSendRatingBinding?=null
    private var views : View?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }
    var ratingpoint1 : String?=""
    var ratingpoint2 : String?=""
    var ratingpoint3 : String?=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sendRating = DataBindingUtil.inflate(inflater,R.layout.fragment_send_rating, container, false)
        views = sendRating!!.getRoot()


        try {

            var strAppPkgName = "" +ratingDetail!!.getAndroidPackageName()
            strAppPkgName = strAppPkgName!!.replace("\"", "")
            strAppPkgName = strAppPkgName!!.replace("\"", "")

            val icon =
                /*getAppIconByPackageName(strAppPkgName, activity!!)*/
  (activity as Activity)!!.applicationContext.getPackageManager()
                .getApplicationIcon(strAppPkgName)
            sendRating!!.appimage!!.setImageDrawable(icon)

        } catch (excep: Exception) {
            Log.e(
                ""+TAG,
                "getAppIconByPackageName()  excep=" + excep.toString()
            )
            Glide.with(activity!!).load(ratingDetail!!.getImage())
                .into(sendRating!!.appimage)   }
        sendRating!!.appName!!.text = ratingDetail!!.getTitle()
        if(!(ratingDetail!!.getappFullTitle().equals(""))  &&  !(ratingDetail!!.getappFullTitle().equals(null)))
        {
            sendRating!!.appName!!.text = ratingDetail!!.getappFullTitle()
        }

//     sendRating!!.ratingbar.rating = ratingDetail!!.getAvgRating()!!.toFloat()

        sendRating!!.rating1.setOnRatingChangeListener(object: BaseRatingBar.OnRatingChangeListener {
            override fun onRatingChange(ratingBar: BaseRatingBar?, rating: Float, fromUser: Boolean) {
                ratingpoint1 = rating.toString();
        averageOfThreeRating()
            }
         })
        sendRating!!.rating2.setOnRatingChangeListener(object: BaseRatingBar.OnRatingChangeListener {
            override fun onRatingChange(ratingBar: BaseRatingBar?, rating: Float, fromUser: Boolean) {
                ratingpoint2 = rating.toString();
                averageOfThreeRating()    }
        })
        sendRating!!.rating3.setOnRatingChangeListener(object: BaseRatingBar.OnRatingChangeListener {
            override fun onRatingChange(ratingBar: BaseRatingBar?, rating: Float, fromUser: Boolean) {
                ratingpoint3 = rating.toString();
                averageOfThreeRating()     }
        })

        sendRating!!.videoBtnLayout.visibility = View.GONE
        sendRating!!.swichButton.isChecked = false

        sendRating!!.swichButton.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(
                buttonView: CompoundButton,
                isChecked: Boolean
            ) {
                if (isChecked) {
                    sendRating!!.tvContinueOnFilledRate.visibility = View.VISIBLE
                     sendRating!!.videoBtnLayout.visibility = View.GONE
                    sendRating!!.swichButton.isChecked = false
                    Global_Class.fragment(
                        activity!!,
                        RatingSubmitFrag.newInstance(
                            ratingDetail!!,
                            ratingpoint1!!,
                            ratingpoint2!!,
                            ratingpoint3!!,
                            sendRating!!.comment.text.toString()
                        ),
                        true
                    )
                } else {
                    sendRating!!.tvContinueOnFilledRate.visibility = View.VISIBLE
                     sendRating!!.videoBtnLayout.visibility = View.GONE
                }
            }
        })

        sendRating!!.tvContinueOnFilledRate.setOnClickListener {
            if (ratingpoint1.equals("")){
                Global_utility.showtost(activity!!,"Please select you rating")
            }else if (ratingpoint2.equals("")){
                Global_utility.showtost(activity!!,"Please select you rating")
            }else if (ratingpoint3.equals("")){
                Global_utility.showtost(activity!!,"Please select you rating")
            }else if (sendRating!!.comment.text.toString().isNullOrEmpty()){
                Global_utility.showtost(activity!!,"Please add your comment")
            } else{
                Global_utility.hideKeyboardView(fl_fragSendRating, activity!!)
                if (Global_utility.isNetworkConnected(activity!!)){
                val hashMap: HashMap<String, String> = HashMap<String, String>() //define empty hashmap            hashMap!!.put("name","dsasdsa")
                hashMap.put("session_id",CSPreferences.readString(activity!!,"session_id")!!)
                hashMap.put("app_id",param1!!)
                hashMap.put("rate_1",ratingpoint1!!)
                hashMap.put("rate_2",ratingpoint2!!)
                hashMap.put("rate_3",ratingpoint3!!)
                hashMap.put("review",sendRating!!.comment.text.toString())


                    if(ratingDetail!!.getSponsored()==1) {



                        showSwitchButton()
                    }
                    else{
                        Global_utility.hideKeyboardView(fl_fragSendRating, activity!!)


                        callSubmitRatingApi(hashMap,activity!!)

                    }


                }else{
                    Global_utility.showtost(activity!!,resources.getString(R.string.internet_connection))

                }
            }
        }

        // Inflate the layout for this fragment

        sendRating!!.comment.isSingleLine=false
        return views
    }

    private fun showSwitchButton() {
        sendRating!!.tvContinueOnFilledRate.visibility=View.GONE
        sendRating!!.videoBtnLayout.visibility=View.VISIBLE

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

    private fun callSubmitRatingApi(hashMap: HashMap<String, String>, context : Context) {
        val reating = ViewModelProviders.of(this).get(RatingDetailVM::class.java)

        reating.sendrating(hashMap, context)!!.observe(this, Observer {
            if (it.getStatus()!!.equals(200)) {
                Log.d(TAG ,"savedRating="+it.getRating()!!)
          if(it.getMessage()!!.equals("You can not rate to another app") )
          {
            showDialogPopup()
            //  Global_utility.showtost(context, ""+it.getMessage())
          }
          else {
              val intent = Intent(activity!!, Home_Activity::class.java)
              //     intent.addFlags(   Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
              intent.putExtra("from", "rating")
              startActivity(intent)
              activity!!.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
              activity!!.finish()
          }
            }
            else
                if (it.getStatus()!!.equals(403))
                {
                    it.getMessage()?.let { it1 ->
                        Global_utility.showtost(context, it1)
                    }

                    CSPreferences.putString(context, "session_id","")
                    val intent = Intent(activity!!, Saplash::class.java)
                    intent.addFlags(   Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    activity!!.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    activity!!.finish()
                }
                else {

                    it.getMessage()?.let { it1 -> Global_utility.showtost(context, it1) }
                }
        })


    }

    private fun showDialogPopup() {

        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_alert)
        dialog.tvMsg.text = "You Cannot Rate to Another User's App"

        dialog.btnNo.visibility=View.GONE
        dialog.btnYes.text="OK"
        dialog.btnYes.setOnClickListener {
            dialog.dismiss()
         }
        dialog.show()
    }


    private fun averageOfThreeRating() {
        ratingpoint1="0"+ratingpoint1
        ratingpoint2="0"+ratingpoint2
        ratingpoint3="0"+ratingpoint3

var     averageRateVal =    ((( ratingpoint1!!.toFloat())+( ratingpoint2!!.toFloat())+( ratingpoint3!!.toFloat())).toFloat() /3)
         sendRating!!.ratingbar.rating=averageRateVal

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        img_headerback.setOnClickListener(this)
        tv_headerTitle.setText("Privacy Ratings")
        fl_fragSendRating.setOnClickListener(this)

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
    var ratingDetail : All_ratingPojo.Rating?=null

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String,detailrating: All_ratingPojo.Rating) =
            RatingSave().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    ratingDetail =detailrating
                }
            }
    }


    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.fl_fragSendRating->
            {
                Global_utility.hideKeyboardView(fl_fragSendRating, activity!!)
            }
            R.id.img_headerback->
            {
                Global_utility.hideKeyboardView(fl_fragSendRating, activity!!)
                val manager: FragmentManager = activity!!.supportFragmentManager
                manager.popBackStack()
            }
        }
    }
}
