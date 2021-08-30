package com.privexec.fragmnet

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import android.app.NotificationManager
import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lee_da_hang_pte_ltd.utills.CSPreferences
import com.lee_da_hang_pte_ltd.utills.Global_utility
import com.privexec.R
import com.privexec.activity.Home_Activity
import com.privexec.activity.Saplash
import com.privexec.activity.fingersample.MainActivity
import com.privexec.databinding.FragmentEmailTemplateBinding
import com.privexec.utills.DownloadTask
import com.privexec.viewmodel.DsarVM
import com.privexec.viewmodel.MyAppVM
import io.github.lucasfsc.html2pdf.Html2Pdf
import kotlinx.android.synthetic.main.fragment_email_template.*
import kotlinx.android.synthetic.main.header.*
import java.io.File
import java.io.FileOutputStream

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * create an instance of this fragment.
 */
class EmailTemplateFrag : Fragment(), View.OnClickListener, Html2Pdf.OnCompleteConversion  {
    // TODO: Rename and change types of parameters
    private var holdSupportEmail: String? = null
    private var jsonQusetAnswerArray: String? = null
    var emailTemplateBinding: FragmentEmailTemplateBinding? = null

    private var downloadManager: DownloadManager? = null
    private var WRITE_EXST = 190
    private var Download_Uri: Uri? = null
    private var views: View? = null
    var TAG = "TAG Dsar_fragmnet "
var chkIfReportSubmit=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            holdSupportEmail = it.getString(ARG_PARAM1)
            jsonQusetAnswerArray = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        emailTemplateBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_email_template, container, false)
        views = emailTemplateBinding!!.root


        Log.e(TAG,""+jsonQusetAnswerArray)
        if (Global_utility.isNetworkConnected(activity!!)) {
            getEmailTemplateTxt(
                  CSPreferences.readString(activity!!, "session_id")!!,
                jsonQusetAnswerArray.toString(),
                CSPreferences.readString(activity!!, "appid")!!, activity!!
            )
        } else {
            Global_utility.showtost(
                activity!!,
                resources.getString(R.string.internet_connection)
            )
        }
        val result = Global_utility.Utility.checkStoragePermission(activity!!)
if(result)
{

}
else{
    Toast.makeText(activity!!,"Please allow storage permission for save Pdf file",Toast.LENGTH_LONG).show()
}
        return  views
    }

    private fun getEmailTemplateTxt(sessionid: String, jsonArray: String, appid: String, context: Context) {
        val myappViewmodel = ViewModelProviders.of(this).get(DsarVM::class.java)
        myappViewmodel.emailTemplate( sessionid,jsonArray,appid,activity!!  )!!.observe(this, Observer {
            if ((it.status!!) == 200 ) {
               // CSPreferences.putString(activity!!, "statusStr",""+it.status)
             //    it.message?.let { it1 -> Global_utility.showtost(context, it1) }
          emailTemplateBinding!!.etEmailContent.setText(""+it.result)
            }
            else {
                it.message?.let { it1 -> Global_utility.showtost(context, it1) }
            }


        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fl_fragEmailTemplate.setOnClickListener(this)
        tv_headerTitle.setText("E-Mail")
        tv_sendEmail.visibility=View.VISIBLE
        img_headerback.setOnClickListener(this)

         tv_sendEmail.setOnClickListener(this)

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



    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EmailTemplateFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)


                }
            }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_headerback -> {
                Global_utility.hideKeyboardView(fl_fragEmailTemplate, activity!!)
                val manager: FragmentManager = activity!!.supportFragmentManager
                manager.popBackStack()
            }

            R.id.fl_fragEmailTemplate -> {
                Global_utility.hideKeyboardView(fl_fragEmailTemplate, activity!!)
            }

        R.id.tv_sendEmail -> {

            Log.d(TAG,"Ques Answer <<>>"+jsonQusetAnswerArray.toString())
            sendDsrQuestAnswer(
                  CSPreferences.readString(activity!!, "session_id")!!,
                  jsonQusetAnswerArray.toString(),
                  CSPreferences.readString(activity!!, "appid")!!,
                  activity!!
              )
        }
        }
    }

    private fun sendDsrQuestAnswer(sessionid: String, jsonArray: String, appid: String, context: Context) {
        val myappViewmodel = ViewModelProviders.of(this).get(MyAppVM::class.java)
        myappViewmodel.savedsaranswers(sessionid,  appid,et_emailContent.text.toString(), context)!!.observe(
            this,
            Observer {
                if ((it.getStatus()!!)==200) {
                    it.getMessage()?.let { it1 ->
                       /* if (CSPreferences.readString(activity!!, "dsarStatus") == "1") {
                            if(isReadStoragePermissionGranted()  && isWriteStoragePermissionGranted())
                            askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,WRITE_EXST,it.getPdf()!!)
                        else{
                                Toast.makeText(activity!!, "both permission is not granted.", Toast.LENGTH_SHORT) .show()
                            }
                        } else {
                            showReportDialog(it.getPdf()!!)
                        }*/
                   //      generatePdf(et_emailContent.text.toString())
                   simpleSendMail(et_emailContent.text.toString())

                    }
                }
                else if ((it.getStatus()!!)==403) {
                    Global_utility.showtost(context,"Session Expired")
                    CSPreferences.putString(activity!!, "session_id", "")
                    val intent = Intent(context, Saplash::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    activity!!.startActivity(intent)
                    (activity!! as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    (activity!! as Activity).finish()
                }
                else {
                    it.getMessage()?.let { it1 -> Global_utility.showtost(context, it1) }
             }
            })
    }

    private fun simpleSendMail(emailBodyData:String ) {

        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.setType("text/plain");
        if(holdSupportEmail.equals("")  || holdSupportEmail.equals(null))
        {
            holdSupportEmail ="contact@privexec.com"
         }
        Log.e("$TAG Email", "" +holdSupportEmail)

        val uriText = String.format("mailto:%s?subject=%s&body=%s",
           ""+holdSupportEmail, "Privexce Feedback",""+emailBodyData)
        emailIntent.data = Uri.parse(uriText)

       /*                emailIntent.data = Uri.parse("mailto:top@gmail.com"/* +holdSupportEmail*/)
emailIntent.putExtra(
            Intent.EXTRA_SUBJECT,
            "Privexce Feedback"
        )
        emailIntent.putExtra(
            Intent.EXTRA_TEXT,
            ""+emailBodyData
        )*/
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                activity!!,
                "There are no email clients installed.",
                Toast.LENGTH_SHORT
            ).show()
        }

     /*   val intentMail = Intent(Intent.ACTION_SENDTO)
        val uriText = String.format("mailto:%s?subject=%s&body=%s",
            "bittu@gmail.com", "Privexce Feedback",""+emailBodyData)
        intentMail.data = Uri.parse(uriText)

        intentMail.type="text/plain"
          startActivity(intentMail)
      */
    }


    fun generatePdf(text: String?) {

      val path =   Environment.getExternalStorageDirectory().toString() + "/" + "Privexec"
        val dir = File(path)
        if (!dir.exists()) dir.mkdirs()
        val file = File(dir, "savedData.pdf")
        val fOut = FileOutputStream(file)
        val converter = Html2Pdf.Companion.Builder()
            .context(activity!!)
            .html("<p> $text</p>")
            .file(file)
            .build()
        converter.convertToPdf(this)
        converter.convertToPdf()
        if (!file.exists() || !file.canRead()) {
            Log.e(TAG,"not existing ")
            return
        }
        else{
            Log.e(TAG,"existing")
        }
        try {
            val shareIntent = Intent(Intent.ACTION_SENDTO)
 if(holdSupportEmail.equals("")  || holdSupportEmail.equals(null))
 {
 holdSupportEmail ="contact@privexec.com"

 }
             shareIntent.setData(Uri.parse("mailto:"+holdSupportEmail ))
            shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "test Subject")
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "test Extra")
            Log.e(TAG,"URI is= "+ Uri.parse(
                "file://" + Environment.getExternalStorageDirectory()
                    .toString() + "/Privexec/savedData.pdf"))
            shareIntent.putExtra(
                Intent.EXTRA_STREAM, Uri.parse(
                    "file://" + Environment.getExternalStorageDirectory()
                        .toString() + "/Privexec/savedData.pdf"
                )
            )
             startActivity(Intent.createChooser(shareIntent, "Send mail..."))

        }
        catch (exce:java.lang.Exception)
        {
            Log.e(TAG,"exce="+exce.toString())
        }





    }


    fun isReadStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(activity!!,Manifest.permission.READ_EXTERNAL_STORAGE)
                === PackageManager.PERMISSION_GRANTED
            ) {
                Log.v(TAG, "Permission is granted1")
                true
            } else {
                Log.v(TAG, "Permission is revoked1")
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    3
                )
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted1")
            true
        }
    }

    fun isWriteStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(activity!!,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                === PackageManager.PERMISSION_GRANTED
            ) {
                Log.v(TAG, "Permission is granted2")
                true
            } else {
                Log.v(TAG, "Permission is revoked2")
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    2
                )
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted2")
            true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            2 -> {
                Log.d(TAG, "External storage2")
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(
                        TAG,
                        "if  Permission: " + permissions[0] + "was " + grantResults[0]
                    )

                } else {
                    Log.e(
                        TAG,
                        "else Permission: " + permissions[0] + "was " + grantResults[0]
                    )
                }
            }
            3 -> {
                Log.d(TAG, "External storage1")
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(
                        TAG,
                        "if Permission1 : " + permissions[0] + "was " + grantResults[0]
                    )
                 } else {
                    Log.e(
                        TAG,
                        "else Permission1: " + permissions[0] + "was " + grantResults[0]
                    )
                }
            }
        }
    }
    fun showReportDialog(pdfurl: String) {
        val builder = AlertDialog.Builder(activity!!)
         builder.setTitle(R.string.app_name)
         builder.setMessage("You disabled auto save dsar pdf, you can open only")
        builder.setIcon(R.drawable.logo_icon)

       builder.setPositiveButton("Open") { dialogInterface, which ->
     var browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(pdfurl))
     startActivity(browserIntent )
           chkIfReportSubmit=true

        }
        builder.setNegativeButton("Go Back") { dialogInterface, which ->
            val intent = Intent(activity!!, Home_Activity::class.java)
            intent.addFlags(   Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            activity!!.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            activity!!.finish()

        }
   val alertDialog: AlertDialog = builder.create()
         alertDialog.setCancelable(false)
        alertDialog.show()

     }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e(""+TAG,"onAttach")
    }

    override fun onResume() {
        super.onResume()
        Log.e(""+TAG,"onResume")
if(chkIfReportSubmit)
{
      val intent = Intent(activity!!, Home_Activity::class.java)
      intent.addFlags(   Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
      startActivity(intent)
      activity!!.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
      activity!!.finish()

}

    }

    private fun askForPermission(permission: String, requestCode: Int,strPdfUrl:String) {
        if (ContextCompat.checkSelfPermission(
                activity!!,
                permission
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
/*
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity!!,
                    permission
                )
            ) {
                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(permission),
                    requestCode
                )
            } else {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(permission),
                    requestCode
                )
            }
        */
            Toast.makeText(activity!!, "Storage permission is not granted.", Toast.LENGTH_SHORT)
                .show()
        } else {
            DownloadTask(activity!!,strPdfUrl)
        }
    }

    override fun onSuccess() {
        //do your thing
    }

    override fun onFailed() {
        //do your thing
    }

}