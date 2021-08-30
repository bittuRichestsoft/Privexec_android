package com.privexec.fragmnet

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.DownloadManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.lee_da_hang_pte_ltd.utills.CSPreferences
import com.lee_da_hang_pte_ltd.utills.Global_utility
import com.privexec.Api_Call.DsarQuestion_interface
import com.privexec.R
import com.privexec.activity.Home_Activity
import com.privexec.activity.Home_Activity.Companion.videoToolbarTitle
import com.privexec.adapter.DsarQuestAdapter
import com.privexec.adapter.PrivacyRating_Adapter
import com.privexec.databinding.FragmentDsarFragmnetBinding
import com.privexec.otherutility.Global_Class
import com.privexec.pojoclass.AllDsarQuestionPojo
import com.privexec.pojoclass.All_ratingPojo
import com.privexec.pojoclass.App_dataPojo
import com.privexec.viewmodel.DsarVM
import com.privexec.viewmodel.MyAppVM
import com.privexec.viewmodel.RatingDetailVM
import kotlinx.android.synthetic.main.fragment_dsar_fragmnet.*
import kotlinx.android.synthetic.main.fragment_tutroial.*
import kotlinx.android.synthetic.main.header.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Dsar_fragmnet.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Dsar_fragmnet.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class Dsar_fragmnet : Fragment(), View.OnClickListener, DsarQuestion_interface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var drawable: Drawable? = null
    var dsar_banding: FragmentDsarFragmnetBinding? = null
    private var dsarQuestAdapter: DsarQuestAdapter? = null

    private var views: View? = null
    var TAG = "TAG Dsar_fragmnet "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)

        }
    }

    var addpojo_rsult: App_dataPojo.Result? = null


    private var downloadManager: DownloadManager? = null
    private var refid: Long = 0
    private var bitmap: Bitmap? = null
    private var dsarQuestList = arrayListOf<AllDsarQuestionPojo.AllDsarQuestionModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dsar_banding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_dsar_fragmnet, container, false)
        views = dsar_banding!!.root
        //   Home_Activity.tvTitle!!.text = resources.getString(R.string.dsar)
        downloadManager = activity!!.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        activity!!.registerReceiver(
            onComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
       // Download_Uri = Uri.parse("http://167.172.209.57/privexec/public/pdf/dsar-7dc392c5.pdf")
        dsar_banding!!.videoBtnLayout.visibility = View.GONE


        dsar_banding!!.swichButton.isChecked = false
        dsar_banding!!.tital.text = addpojo_rsult!!.title
        dsar_banding!!.discraption.text = addpojo_rsult!!.appealDesc.toString()
            .replace("[", "")  //remove the right bracket
            .replace("]", "")  //remove the left bracket
            .trim();

        dsar_banding!!.swichButton.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(
                buttonView: CompoundButton,
                isChecked: Boolean
            ) {
                if (isChecked) {
                    dsar_banding!!.dsarbutton.visibility = View.VISIBLE
                      dsar_banding!!.videoBtnLayout.visibility = View.GONE
                    dsar_banding!!.swichButton.isChecked = false
                    Global_Class.fragment(
                        activity!!,
                        VideoFragment.newInstance(""+addpojo_rsult!!.email, addpojo_rsult!!),
                        true
                    )

                } else {
                    dsar_banding!!.dsarbutton.visibility = View.VISIBLE
                      dsar_banding!!.videoBtnLayout.visibility = View.GONE
                }
            }
        })

       /* dsar_banding!!.qtext1.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {

                val DRAWABLE_RIGHT = 2

                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= dsar_banding!!.qtext1.getRight() - dsar_banding!!.qtext1.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds()
                            .width()
                    ) {
                        // your action here

                        showDialog()

                        return true
                    }
                }
                return false
            }
        })



        dsar_banding!!.date.setOnClickListener {
            val mcurrentDate = Calendar.getInstance()
            val mYear = mcurrentDate.get(Calendar.YEAR)
            val mMonth = mcurrentDate.get(Calendar.MONTH)
            val mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH)

            val mDatePicker: DatePickerDialog
            mDatePicker = DatePickerDialog(
                activity!!, R.style.DialogTheme,
                DatePickerDialog.OnDateSetListener { datepicker, selectedyear, selectedmonth, selectedday ->
                    var selectedmonth = selectedmonth
                    dsar_banding!!.date.text =
                        " " + +selectedday + "/" + ++selectedmonth + "/" + selectedyear + ""

                }, mYear, mMonth, mDay
            )
            mDatePicker.show()

        }*/

        dsar_banding!!.dsarbutton.setOnClickListener {
            Global_utility.hideKeyboardView(fl_fragDsar, activity!!)
            var questChk = false
            jsonQuesArray  = JsonArray()
            for (i in 0..(dsarQuestList.size - 1)) {
                var jsonobject  = JsonObject()

                Log.e(
                    TAG,
                    "ques $i <> optional ${dsarQuestList[i].optional} <> answ=" + dsarQuestList[i].answer
                )
                if(dsarQuestList[i].optional==0 && dsarQuestList[i].answer.replace(" ".toRegex(),"")=="")
                 {
                     questChk=true
                 }
                else{
                     Log.e(TAG,"ques ${dsarQuestList[i].id} <> optional ${dsarQuestList[i].optional} <> answ="+dsarQuestList[i].answer )
                 }

                jsonobject.addProperty("id", ""+(dsarQuestList[i].id))
                jsonobject.addProperty("answer",  ""+dsarQuestList[i].answer )
                jsonobject.addProperty("questions",  ""+dsarQuestList[i].questions)
 jsonQuesArray.add(jsonobject)

            }
           if(questChk)
           {
               Toast.makeText(context,"Please Fill All Required Fields",Toast.LENGTH_LONG).show()
            }
else           if(addpojo_rsult!!.sponsored==0)
           {
            /*   dsar_banding!!.dsarbutton.visibility = View.VISIBLE
               videoToolbarTitle = "videoTitle"
               dsar_banding!!.videoBtnLayout.visibility = View.GONE
               dsar_banding!!.swichButton.isChecked = false*/
               Global_Class.fragment(
                   activity!!,
                   EmailTemplateFrag.newInstance(""+addpojo_rsult!!.email, jsonQuesArray.toString()),
                   true
               )
           }
           else {
               // dsar_banding!!.qua6.visibility = View.GONE
                dsar_banding!!.dsarbutton.visibility = View.GONE
                videoToolbarTitle = "videoTitle"
                dsar_banding!!.videoBtnLayout.visibility =View.VISIBLE
            }
        }

        dsar_banding!!.rvDsarQuestions.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        dsar_banding!!.rvDsarQuestions.setHasFixedSize(true)
        dsar_banding!!.rvDsarQuestions.setItemAnimator(DefaultItemAnimator())

        dsarQuestAdapter = DsarQuestAdapter(activity!!, dsarQuestList)
        dsar_banding!!.rvDsarQuestions.adapter = dsarQuestAdapter

        if (Global_utility.isNetworkConnected(activity!!)) {
            getDsarQuestions(this)
        } else {
            Global_utility.showtost(activity!!, resources.getString(R.string.internet_connection))
        }

        return views
    }

    private fun getDsarQuestions(dsarquestionInterface: DsarQuestion_interface) {

        val dsarVM = ViewModelProviders.of(this).get(DsarVM::class.java)

        dsarVM.getDsarQuestApi(CSPreferences.readString(activity!!, "session_id"), activity!!)!!
            .observe(this, Observer {
                Log.d(TAG, "getDsarQuestApi ${it.status!!}")

                if ((it.status!!) == 200) {

                    dsarquestionInterface.getQuestions(it.rating!!)
                 /*   it.message?.let { it1 -> Global_utility.showtost(activity!!, it1) }*/
                } else {

                    it.message?.let { it1 -> Global_utility.showtost(activity!!, it1) }
                }
            })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fl_fragDsar.setOnClickListener(this)
        tv_headerTitle.setText(resources.getString(R.string.dsar))
        img_headerback.setOnClickListener(this)
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
        fun newInstance(param1: String, param2: App_dataPojo.Result) =
            Dsar_fragmnet().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    addpojo_rsult = param2
                }
            }

        /*var strNameOfPerson = ""
        var strAddressPerson = ""
        var strEmailAddress = ""
        var strTeliphonenumber = ""
        var strDate = ""
        var strSubjectAccess = ""
        var strWellbemade = ""*/
        var jsonQuesArray  = JsonArray()


        fun loadBitmapFromView(pdfLayout: LinearLayout, width: Int, height: Int): Bitmap {
            var bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            var c = Canvas(bitmap)
            pdfLayout.draw(c)
            return bitmap
        }
    }


    private fun appdata(sessionid: String, jsonArray: String, appid: String, context: Context) {
        val myappViewmodel = ViewModelProviders.of(this).get(MyAppVM::class.java)
        myappViewmodel.savedsaranswers(sessionid, jsonArray, appid,  context )!!.observe(
            this,
            Observer {
                if (it.getStatus()!!.equals("200")) {
                    it.getMessage()?.let { it1 ->
                        Global_utility.showtost(context, it1)
                        //  premsiion_dailog(it.getPdf()!!)
                    }
//                    bitmap = loadBitmapFromView(dsar_banding!!.pdfLayout, dsar_banding!!.pdfLayout.getWidth(), dsar_banding!!.pdfLayout.getHeight())
//                    createPdf()
                } else {

                    it.getMessage()?.let { it1 -> Global_utility.showtost(context, it1) }
                    premsiion_dailog(it.getPdf()!!)

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

        //performing positive actionu
        builder.setPositiveButton("Yes") { dialogInterface, which ->

            // FileDownloader.downloadFile(fileUrl, pdfFile);
            var browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(pdfurl))

            // browserIntent.setDataAndType(Uri.parse( pdfurl), "text/html");
            startActivity(browserIntent)
        }
        //performing cancel action
        /*builder.setNeutralButton("Cancel"){dialogInterface , which ->
            Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_LONG).show()
        }*/
        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, which ->


        }

        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()

    }


    var onComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctxt: Context, intent: Intent) {
            val referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            Log.e("IN", "" + referenceId)

            Log.e("INSIDE", "" + referenceId)
            val mBuilder = NotificationCompat.Builder(activity)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("GadgetSaint")
                .setContentText("All Download completed")
            val notificationManager =
                activity!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(455, mBuilder.build())

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


    private fun showDialog() {
        var dialog: Dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.info_dailog)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.getWindow()!!.getAttributes())
        lp.gravity = Gravity.RIGHT
        dialog.getWindow()!!.setAttributes(lp)
        dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }



    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_headerback -> {
                Global_utility.hideKeyboardView(fl_fragDsar, activity!!)
                val manager: FragmentManager = activity!!.supportFragmentManager
                manager.popBackStack()
            }

            R.id.fl_fragDsar -> {
                Global_utility.hideKeyboardView(fl_fragDsar, activity!!)
            }
        }
    }

    override fun getQuestions(list: List<AllDsarQuestionPojo.AllDsarQuestionModel>) {
        dsarQuestList.clear()
        dsarQuestList.addAll(list)
        dsar_banding!!.rvDsarQuestions.adapter!!.notifyDataSetChanged()
        Log.e(TAG, "Quest list=" + list.size)
    }
}
