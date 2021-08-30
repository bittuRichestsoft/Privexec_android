package com.privexec.fragmnet

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.JsonObject
import com.lee_da_hang_pte_ltd.utills.CSPreferences
import com.lee_da_hang_pte_ltd.utills.Global_utility
import com.privexec.R
import com.privexec.activity.Home_Activity
import com.privexec.databinding.FragmentVideoBinding
/*import com.privexec.fragmnet.Dsar_fragmnet.Companion.strAddressPerson
import com.privexec.fragmnet.Dsar_fragmnet.Companion.strDate
import com.privexec.fragmnet.Dsar_fragmnet.Companion.strEmailAddress
import com.privexec.fragmnet.Dsar_fragmnet.Companion.strNameOfPerson
import com.privexec.fragmnet.Dsar_fragmnet.Companion.strSubjectAccess
import com.privexec.fragmnet.Dsar_fragmnet.Companion.strTeliphonenumber
import com.privexec.fragmnet.Dsar_fragmnet.Companion.strWellbemade*/
import com.privexec.fragmnet.Dsar_fragmnet.Companion.jsonQuesArray
import com.privexec.otherutility.Global_Class

import com.privexec.pojoclass.App_dataPojo
import com.privexec.viewmodel.MyAppVM
import kotlinx.android.synthetic.main.header.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VideoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VideoFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var dsar_banding: FragmentVideoBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    var addpojo_rsult: App_dataPojo.Result? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dsar_banding = DataBindingUtil.inflate(inflater, R.layout.fragment_video, container, false)

        val view = dsar_banding!!.root
     //   Home_Activity.tvTitle!!.text =   "Title"  // resources.getString(R.string.dsar)

        getVideo_Here()

if(addpojo_rsult!!.appealDesc!=null)
{
    dsar_banding!!.discraption.setText(""+addpojo_rsult!!.appealDesc)

}
        dsar_banding!!.dsarbutton.setOnClickListener {

            if (Global_utility.isNetworkConnected(activity!!)) {
                Log.d("jsonsss", jsonQuesArray.toString())
                Global_Class.fragment(
                    activity!!,
                    EmailTemplateFrag.newInstance(""+addpojo_rsult!!.email, jsonQuesArray.toString()),
                    true
                )
            } else {
                Global_utility.showtost(
                    activity!!,
                    resources.getString(R.string.internet_connection)
                )
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dsar_banding!!.flVideoFrag.setOnClickListener(this)
        tv_headerTitle.setText(""+addpojo_rsult!!.title)
        dsar_banding!!.tvVideoBottomTitleTxt.setText(""+addpojo_rsult!!.author)
     //   tv_sendEmail.visibility=View.VISIBLE
        img_headerback.setOnClickListener(this)

    }

    private fun appdata(sessionid: String, jsonArray: String, appid: String, context: Context) {
        val myappViewmodel = ViewModelProviders.of(this).get(MyAppVM::class.java)
        myappViewmodel.savedsaranswers(sessionid, jsonArray, appid,  context)!!.observe(
            this,
            Observer {
                if ((it.getStatus()!!)==200) {
                    it.getMessage()?.let { it1 ->
                        Global_utility.showtost(context, it1)
                         premsiion_dailog(it.getPdf()!!)
                    }
//                    bitmap = loadBitmapFromView(dsar_banding!!.pdfLayout, dsar_banding!!.pdfLayout.getWidth(), dsar_banding!!.pdfLayout.getHeight())
//                    createPdf()
                } else {
                    it.getMessage()?.let { it1 -> Global_utility.showtost(context, it1) }
//                    premsiion_dailog(it.getPdf()!!)
                }
            })
    }

    fun premsiion_dailog(pdfurl: String) {
        val builder = AlertDialog.Builder(activity!!)
        //set title for alert dialog
        builder.setTitle(R.string.app_name)
        //set message for alert dialog
        builder.setMessage(R.string.premission_messag)
        builder.setIcon(R.drawable.logo_p)

          builder.setPositiveButton("Yes") { dialogInterface, which ->
      var browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(pdfurl))
      startActivity(browserIntent)
        }
        //performing cancel action
        /*builder.setNeutralButton("Cancel"){dialogInterface , which ->
            Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_LONG).show()
        }*/
        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, which ->

        }
         val alertDialog: AlertDialog = builder.create()
         alertDialog.setCancelable(false)
        alertDialog.show()

}


    private fun getVideo_Here() {
        dsar_banding!!.videoDiscraption.visibility = View.VISIBLE
        // Toast.makeText(activity, "ON", Toast.LENGTH_SHORT).show()
        var videiourl = addpojo_rsult!!.appealVideo.toString()
            .replace("[", "")  //remove the right bracket
            .replace("]", "")  //remove the left bracket
            .trim()

        println("printvideiourl.." + videiourl)
        val uriPath = Uri.parse(videiourl)
        dsar_banding!!.video.setVideoURI(uriPath)
        dsar_banding!!.video.start()
//                    dsar_banding!!.video.pause()
//                    dsar_banding!!.video.canPause()
//                    dsar_banding!!.video.canSeekForward()
//                    dsar_banding!!.video.canSeekBackward();
        dsar_banding!!.video?.setOnPreparedListener(object :
            MediaPlayer.OnPreparedListener {
            override fun onPrepared(mp: MediaPlayer) {
              }
        })
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: App_dataPojo.Result) =
            VideoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)

                    addpojo_rsult = param2
                }
            }
    }

    override fun onStop() {
        super.onStop()
        dsar_banding!!.video.stopPlayback()
    }

    override fun onDestroy() {
        super.onDestroy()
        dsar_banding!!.video.stopPlayback()
        // unregisterForContextMenu(onComplete)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_headerback -> {
                Global_utility.hideKeyboardView(dsar_banding!!.flVideoFrag, activity!!)
                val manager: FragmentManager = activity!!.supportFragmentManager
                manager.popBackStack()
            }

            R.id.fl_videoFrag -> {
                Global_utility.hideKeyboardView(dsar_banding!!.flVideoFrag, activity!!)
            }


        }

    }

}
