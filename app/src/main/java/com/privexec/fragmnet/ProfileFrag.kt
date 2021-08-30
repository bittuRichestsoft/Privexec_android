package com.privexec.fragmnet

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.CursorLoader
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bruce.pickerview.popwindow.DatePickerPopWin
import com.bumptech.glide.Glide
import com.lee_da_hang_pte_ltd.utills.CSPreferences
import com.lee_da_hang_pte_ltd.utills.Global_utility
import com.privexec.R
import com.privexec.activity.Home_Activity
import com.privexec.activity.Saplash
import com.privexec.viewmodel.LoginVM
import com.privexec.viewmodel.MyAppVM
import kotlinx.android.synthetic.main.dialog_alert.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.header.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileDescriptor
import java.io.IOException
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFrag : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var GALLERY_REQUEST = 20
    private var CAMERA_REQUEST = 5001
    private var uri: Uri? = null
    var file: File? = null
    private var imageUri: String? = null
    private var userChoosenTask: String? = null
    var strChoosedDOB = ""
    var strChoosedCountry = ""
    private var countryList = ArrayList<String>()

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
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        img_headerback.setOnClickListener(this)
        tv_headerTitle.setText("My Profile")
        ll_fragEdtProfile.setOnClickListener(this)
        etUserEmail.setText(CSPreferences.readString(activity!!, "email"))
        etPhoneNo.setText(CSPreferences.readString(activity!!, "phone_number"))
        etUserName.setText(CSPreferences.readString(activity!!, "user_name"))
        etCountry.setText(CSPreferences.readString(activity!!, "user_country"))
        etCountry.isEnabled =false
    //    tv_editableCountry.setText(CSPreferences.readString(activity!!, "user_country"))
        tv_Dob.setText(CSPreferences.readString(activity!!, "user_dob"))
        strChoosedCountry=""+CSPreferences.readString(activity!!, "user_country")
        strChoosedDOB=""+CSPreferences.readString(activity!!, "user_dob")
        Glide.with(activity!!).load(CSPreferences.readString(activity!!, "user_img"))
            .placeholder(R.drawable.logo_icon)
            .into(iv_userimage);


        btnsubmit.setOnClickListener(this)
        iv_enablePencil.setOnClickListener(this)
        iv_uploadImg.setOnClickListener(this)
        tv_Dob.setOnClickListener(this)




        if (Global_utility.isNetworkConnected(activity!!)) {
            getCountryResponseApi()
        } else {
            Global_utility.showtost(activity!!, resources.getString(R.string.internet_connection))
   }
        sp_country.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
          strChoosedCountry=countryList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getCountryResponseApi() {
        val myappViewmodel = ViewModelProviders.of(this).get(MyAppVM::class.java)
        myappViewmodel.newCountryResponse(activity!!)!!.observe(this, Observer {
            if (!it.status!!.equals("200")) {
                for (i in 0 until it.countries!!.size) {
                    countryList.add("" + it.countries!!.get(i).name)
                }
                    val adapter = ArrayAdapter(activity!!,
                        android.R.layout.simple_spinner_item, countryList)
                    sp_country.adapter = adapter
                println(
                    "getCountryResponseApi...  sharedPref country=" +CSPreferences.readString(activity!!, "user_country")!!.toLowerCase().toString())
                for (i in 0 until countryList.size) {
                    if (CSPreferences.readString(activity!!, "user_country")!!.toLowerCase()
                            .equals( countryList.get(i).toLowerCase())
                    ) {
                        println(
                            "yaha_ayaa_... old Country="+CSPreferences.readString(activity!!, "user_country")+"   nowFetched=" +countryList.get(i).toLowerCase()
                        )
                        sp_country.setSelection(i).toString()
                    }
                }
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


    private fun takePhotoFromCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST)
    }

    fun showpicture() {
        val items = arrayOf<CharSequence>("Take Photo", "Choose from Library", "Cancel")
        val builder = android.app.AlertDialog.Builder(context)
        builder.setTitle("Add Photo!")
        //  View view=getLayoutInflater().inflate(R.layout.dailog_title,null);builder.setCustomTitle(view);
        builder.setItems(items) { dialog, item ->
            val result = Global_utility.Utility.checkStoragePermission(activity!!)
            if (items[item] == "Take Photo") {
                userChoosenTask = "Take Photo"
                if (result)
                    takePhotoFromCamera()
                dialog.dismiss()
            } else if (items[item] == "Choose from Library") {
                userChoosenTask = "Choose from Library"
                if (result)
                    showFileChooser()
                dialog.dismiss()
            } else if (items[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.setCancelable(false)
        builder.show()
    }

    private fun showFileChooser() {

        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY_REQUEST)

    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    private fun getPath(contentUri: Uri): String {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(activity, contentUri, proj, null, null, null)
        val cursor = loader.loadInBackground()
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val result = cursor.getString(column_index)
        cursor.close()
        return result
    }

    fun getRealPathFromURI(uri: Uri): String {
        val cursor = activity!!.getContentResolver().query(uri, null, null, null, null)
        cursor?.moveToFirst()
        var idx = cursor?.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return cursor!!.getString(idx!!)
    }

    override
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (this.GALLERY_REQUEST == requestCode && resultCode == Activity.RESULT_OK) {
            var picUri = data!!.data
            file = File(getPath(picUri!!))
            Log.d("onActivityResult", " file path -> "+file.toString())
            uriToBitmap(picUri)
           try{
         Log.d("onActivityResult", "GALLERY_REQUEST try 1")
         iv_userimage.setImageURI(picUri!!)
         Log.d("onActivityResult", "GALLERY_REQUEST try 2")

     }
     catch (excep:java.lang.Exception)
     {
         Log.d("onActivityResult", "excep for setting img GALLERY_REQUEST "+ excep)
     }
          /*  val vendorimage =
                Objects.requireNonNull<Bundle>(data!!.extras).get("data") as Bitmap?
            imageUri = getRealPathFromURI(getImageUri(activity!!, vendorimage!!))
            uri = getImageUri(activity!!, vendorimage)

            file = File(imageUri)
            try{
                Log.d("onActivityResult", "CAMERA_REQUEST try 2")
                iv_userimage.setImageBitmap(vendorimage)
                Log.d("onActivityResult", "CAMERA_REQUEST try 2")
            }
            catch (excep:java.lang.Exception)
            {
                Log.d("onActivityResult", "excep for setting img CAMERA_REQUEST "+ excep)
            }*/

        }

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            try {
                val vendorimage =
                    Objects.requireNonNull<Bundle>(data!!.extras).get("data") as Bitmap?
                imageUri = getRealPathFromURI(getImageUri(activity!!, vendorimage!!))
                uri = getImageUri(activity!!, vendorimage)

                file = File(imageUri)
                try{
                    Log.d("onActivityResult", "CAMERA_REQUEST try 2")
                    iv_userimage.setImageBitmap(vendorimage)
                    Log.d("onActivityResult", "CAMERA_REQUEST try 2")
                }
                catch (excep:java.lang.Exception)
                {
                    Log.d("onActivityResult", "excep for setting img CAMERA_REQUEST "+ excep)
                }
            } catch (exc: Exception) {
                exc.printStackTrace()
            }

        }
    }
    private fun uriToBitmap(selectedFileUri: Uri) {
        try {
            val parcelFileDescriptor: ParcelFileDescriptor =activity!!.getContentResolver().openFileDescriptor(selectedFileUri, "r")!!
            val fileDescriptor: FileDescriptor = parcelFileDescriptor.getFileDescriptor()
            val image: Bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            iv_userimage.setImageBitmap(image)
            parcelFileDescriptor.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.ll_fragEdtProfile -> {
            }
            R.id.img_headerback -> {
                Global_utility.hideKeyboardView(ll_fragEdtProfile, activity!!)
                val manager: FragmentManager = activity!!.supportFragmentManager
                manager.popBackStack()
            }
            R.id.btnsubmit -> {
                try{chkFieldsValidate()
                }
            catch (excep:java.lang.Exception)
            {

            }
                }
            R.id.iv_enablePencil -> {
                iv_disablePencil.visibility = View.VISIBLE
                iv_enablePencil.visibility = View.GONE
                iv_uploadImg.visibility = View.VISIBLE
                btnsubmit.visibility = View.VISIBLE
                etUserName.isEnabled = true
             /*   etUserEmail.isEnabled = true*/
                etPhoneNo.isEnabled = true
                tv_Dob.isEnabled = true
                etCountry.visibility = View.GONE
                fl_chooseCountry.visibility = View.VISIBLE
            }


            R.id.iv_uploadImg -> {
                showpicture()

            }
            R.id.tv_Dob -> {
                showDatePickerDialog()
            }
        }
    }

    private fun showDatePickerDialog() {

var tmpDate="1900-01-01"
        if(!strChoosedDOB.equals(""))
        {
            tmpDate=strChoosedDOB
        }
        var pickerPopWin: DatePickerPopWin =
            DatePickerPopWin.Builder(activity!!, object : DatePickerPopWin.OnDatePickedListener {
                override fun onDatePickCompleted(
                    year: Int,
                    month: Int,
                    day: Int,
                    dateDesc: String?
                ) {
                    strChoosedDOB = "" + dateDesc
                    tv_Dob.text = strChoosedDOB
                 //   Toast.makeText(activity!!, dateDesc, Toast.LENGTH_SHORT).show()
                }
            }).textConfirm("CONFIRM") //text of confirm button
                .textCancel("CANCEL") //text of cancel button
                .btnTextSize(16) // button text size
                .viewTextSize(25) // pick view text size
                .colorCancel(Color.parseColor("#999999")) //color of cancel button
                .colorConfirm(Color.parseColor("#009900")) //color of confirm button
                .minYear(1900) //min year in loop
                .maxYear(2020) // max year in loop
                .showDayMonthYear(true) // shows like dd mm yyyy (default is false)
                .dateChose(tmpDate) // date chose when init popwindow
                .build()

        pickerPopWin.showPopWin(activity!!)

    }

    private fun chkFieldsValidate() {
        if (etUserName.text.toString().isEmpty()) {
            Global_utility.showtost(activity!!, resources.getString(R.string.validation_uname))

        }/* else if (etUserEmail.text.toString()!!.isEmpty() || !Global_utility.isValidEmail(
                etUserEmail.text.toString()
            )
        ) {
            Global_utility.showtost(activity!!, resources.getString(R.string.validation_emial))

        } *//*else if (etPhoneNo.text.toString().trim().length != 10) {
            Global_utility.showtost(activity!!, resources.getString(R.string.validation_mobile))
        } else if (strChoosedDOB.equals("")) {
            Global_utility.showtost(activity!!, resources.getString(R.string.choose_dob))
        }*/ else if (strChoosedCountry.equals("")) {
            Global_utility.showtost(activity!!, resources.getString(R.string.choose_country))
        } else {
            if (Global_utility.isNetworkConnected(activity!!)) {

                var strSession_id = RequestBody.create(
                    okhttp3.MultipartBody.FORM,
                    CSPreferences.readString(activity!!, "session_id")
                    /*"lmPMXE1WDMTh7EIbA7wJfvL4215XOZ9Q"*/
                )
                var strEmail = RequestBody.create(
                    okhttp3.MultipartBody.FORM,
                    etUserEmail?.text.toString()
                )
                var strName = RequestBody.create(
                    okhttp3.MultipartBody.FORM,
                    etUserName?.text.toString()
                )
                var strPhone = RequestBody.create(
                    okhttp3.MultipartBody.FORM,
                    etPhoneNo?.text.toString()
                )
                var strDob = RequestBody.create(
                    okhttp3.MultipartBody.FORM, strChoosedDOB
                )
                var strCountry = RequestBody.create(
                    okhttp3.MultipartBody.FORM, /*strChoosedCountry*/ sp_country.selectedItem.toString()
                )


                /*    var etAddress = RequestBody.create(
                        okhttp3.MultipartBody.FORM,
                        editProfileBinding?.etAddress?.text.toString()
                    )
    */
                var imagerequestFile: RequestBody
                var imagebody: MultipartBody.Part

                if (file.toString().equals("null")) {
                    callUpdateProfileApi(
                        strSession_id,
                        strName,
                        strEmail,
                        strPhone,strDob,strCountry,
                        null,
                        activity!!
                    )
                } else {
                    imagerequestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file)
                    imagebody = MultipartBody.Part.createFormData(
                        "profile_image",
                        file!!.getName(),
                        imagerequestFile
                    )
                    callUpdateProfileApi(
                        strSession_id,
                        strName,
                        strEmail,
                        strPhone,strDob,strCountry,
                        imagebody,
                        activity!!
                    )
                }
            } else {
                Global_utility.showtost(
                    activity!!,
                    resources.getString(R.string.internet_connection)
                )
            }
        }
    }

    private fun callUpdateProfileApi(
        session_id: RequestBody, strName: RequestBody, strEmail: RequestBody,
        strPhone: RequestBody,strDob: RequestBody,strCountry: RequestBody, imagebody: MultipartBody.Part?, context: Context
    ) {
        Log.e("profile ","country ="+strCountry.toString()+"   <<>>  "+strDob.toString())
        val updateProfileModel = ViewModelProviders.of(this).get(LoginVM::class.java)
        updateProfileModel.UpdateProfileApi(
            session_id, strName, strEmail, strPhone,strDob,strCountry, imagebody,
            context
        )!!.observe(this, Observer {

            Log.e("callUpdateProfileApi" ,"status="+it.getStatus()!!)
            if (it.getStatus()!!.equals(200)) {

               if( it.success==true) {
                   CSPreferences.putString(context, "email", it.userDetail!!.getEmail().toString())
                   CSPreferences.putString(
                       context,
                       "user_name",
                       it.userDetail!!.getUserName().toString()
                   )
                   CSPreferences.putString(
                       context,
                       "phone_number",
                       it.userDetail!!.getPhone().toString()
                   )
                   CSPreferences.putString(
                       context,
                       "user_country",
                       it.userDetail!!.getCountry().toString()
                   )
                   CSPreferences.putString(
                       context,
                       "user_dob",
                       it.userDetail!!.getDateOfBirth().toString()
                   )
                   CSPreferences.putString(
                       context,
                       "user_img",
                       it.userDetail!!.getProfileImage().toString()
                   )



                   Global_utility.hideKeyboardView(ll_fragEdtProfile, activity!!)
                   showProfileUpdatedDialog()
               }
                else{
                   Global_utility.showtost(context, ""+it.message!!)
               }

            }

            /*else  if (it.getStatus()!!.equals(403)) {
                    it.message?.let { it1 -> Global_utility.showtost(context, it1) }
                    CSPreferences.putString(context, "session_id", "")
                    val intent = Intent(activity!!, Saplash::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    activity!!.overridePendingTransition(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                    )
                    activity!!.finish()
                }*/ else {
                    it.message?.let { it1 -> Global_utility.showtost(context, it1) }
                }
        })
    }

    private fun showProfileUpdatedDialog() {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_alert)
        dialog.tvMsg.text = activity!!.getString(R.string.profile_updated_successfully)
        dialog.btnNo.visibility = View.GONE
        dialog.btnYes.text="OK"
        dialog.btnYes.setOnClickListener {
            val intentHome = Intent(activity!!, Home_Activity::class.java)
            intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intentHome.putExtra("from","profile")
            startActivity(intentHome)
             (activity!! as Activity).overridePendingTransition(
                android.R.anim.fade_in,
                android.R.anim.fade_out)

            (activity!! as Activity).finish()
        /*    dialog.dismiss()
            dialog.hide()
            dialog.cancel()
            Handler().postDelayed( Runnable {

                img_headerback.callOnClick()

            },600        )*/

        }
        dialog.show()
    }

}
