package com.lee_da_hang_pte_ltd.utills

import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.style.CubeGrid
import com.privexec.R
import java.lang.Exception

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class Global_utility {
    companion object {
        lateinit var dialog2: Dialog
        // Emial validation
        val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123
        val MY_PERMISSIONS_REQUEST_CAMERA = 100
        val CAMERA_PIC_REQUEST = 1
        val SELECT_GALLERY_IMAGE = 2


        fun isValidEmail(email: String): Boolean {
            return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        /*fun animate(linerlayot : LinearLayout, context: Context, imageView: ImageView) {
            val animation = AnimationUtils.loadAnimation(context, R.anim.right_to_left)
            animation.setDuration(300)
            linerlayot.setAnimation(animation)
            imageView.setAnimation(animation)
            linerlayot.animate()
            animation.start()
        }*/
        fun showtost(context: Context, message: String) {
            Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show()
        }

        fun isNetworkConnected( context:Context):Boolean
        {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()
        }
/*

        fun fragment(fragmentActivity: FragmentActivity, fragment: Fragment, addToBackStack: Boolean ) {
            if (addToBackStack) {
                fragmentActivity.getSupportFragmentManager()
                    .beginTransaction().add(R.id.framlayout, fragment)
                    .addToBackStack(null).commit()
            }
            else {
                fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.framlayout, fragment).commit()
            }
        }

        fun fragment_replace(fragmentActivity: FragmentActivity, fragment: Fragment, addToBackStack: Boolean ) {
            if (addToBackStack) {
                fragmentActivity.getSupportFragmentManager()
                    .beginTransaction().replace(R.id.famlayout, fragment)
                    .addToBackStack(null).commit()
            }
            else {
                fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.famlayout, fragment).commit()
            }
        }
        fun fragmentadd(fragmentActivity: FragmentActivity, fragment: Fragment, addToBackStack:Boolean) {
            if (addToBackStack) {
                fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.famlayout, fragment).commit()
            }
            else {
                fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.famlayout, fragment).commit()
            }
        }

            fun popBackStack(fragmentActivity: FragmentActivity) {
                if (fragmentActivity.getSupportFragmentManager().backStackEntryCount > 0) {
                    fragmentActivity.getSupportFragmentManager().popBackStack()
                }
            }
        fun isNetworkConnected( context:Context):Boolean
        {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()
        }

        // Progress ................................
        fun showDialog(context: Context) {
            dialog2 = Dialog(context)
            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog2.setCancelable(false)
            dialog2.setContentView(R.layout.custom_progress)
            var spinkit : SpinKitView
            spinkit= dialog2.findViewById(R.id.spin_kit)
            dialog2.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val circal = CubeGrid()
            spinkit.setIndeterminateDrawable(circal)
            dialog2.show()
            dialog2.show()
        }
*/
// Progress ................................
fun showDialog(context: Context) {
    dialog2 = Dialog(context)
    dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog2.setCancelable(false)
    dialog2.setContentView(R.layout.custom_progress)
    var spinkit : SpinKitView
    spinkit= dialog2.findViewById(R.id.spin_kit)
    dialog2.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    val circal = CubeGrid()
    spinkit.setIndeterminateDrawable(circal)
    dialog2.show()
    dialog2.show()
}
        fun hideDialog(context: Context) {
//            if (dialog2!=null)
//            {
try{
    dialog2.dismiss()
}
catch (exp:Exception)
{

}
//            }
            //dialog2!!.dismiss()
        }



        fun hideKeyboardView(view: View, ctx:Context) {
            val inputMethodManager = ctx.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }






            /* fun csprefrence(context: Context){
            CSPreferences.putString(context,"Contact_number","")
            CSPreferences.putString(context,"Contact_name","")
        }*/
            //awesome invite

            /*fun showDialog(context:Context,hashMap: HashMap<String,String>,stringbuffer : StringBuffer)
        {
            var dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.awesome_invite)
            dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
            val lp = WindowManager.LayoutParams()
            dialog.getWindow()!!.getAttributes()
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.MATCH_PARENT
            lp.gravity = Gravity.CENTER
            dialog.getWindow()!!.setAttributes(lp)
            var close:ImageView=dialog.findViewById(R.id.close)
            var send_invite:Button=dialog.findViewById(R.id.send_invite)

            send_invite.setOnClickListener {

                Log.d("ssdsds",stringbuffer.toString())
                WebapiCall.inviteallbusinesses(context,hashMap,dialog,stringbuffer)
            }
            close.setOnClickListener {
                dialog.dismiss()
                stringbuffer.setLength(0);
            }
//inviteallbusinesses
            dialog.show()
        }

        fun showInfoDialog(context:Context)
        {
            var dialog: Dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.info_dialog)
            val lp = WindowManager.LayoutParams()
            lp.copyFrom(dialog.getWindow()!!.getAttributes())
            lp.gravity = Gravity.RIGHT

            dialog.getWindow()!!.setAttributes(lp)
            dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()

            var img_close:ImageView = dialog.findViewById(R.id.img_close)

            img_close.setOnClickListener {
                dialog.dismiss()
            }

        }*/


            // date picker---------------------

            /* fun datepicker(context: Context,YEAR:Int,MONTH:Int,DAY:Int,DOB:String,textView: TextView){
            lateinit var date : DatePickerDialog.OnDateSetListener
            lateinit var datePickerDialog: DatePickerDialog
            val myCalendar = Calendar.getInstance()
            val myFormat = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            datePickerDialog=DatePickerDialog(
                context, R.style.CustomPickerTheme, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()


            date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                textView.setText(sdf.format(myCalendar.time))

            }


        }
*/
            /*  // Add premission---------------
        // Premission granted
        fun checkpermission(activity: Activity) {
            if (Build.VERSION.SDK_INT >= 23) {
                TedPermission(activity)
                    .setPermissionListener(permissionlistener)
                    //.setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                    .setPermissions(
                        Manifest.permission.INTERNET,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS)
                    .check()
            }
        }
        var permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
            }

            override fun onPermissionGranted() {
                //Toast.makeText(Drawer_Activity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

        }

        //hide keyboard
        fun hideKeyboard(activity:Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = activity.getCurrentFocus()
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null)
            {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
        }

        @SuppressLint("MissingPermission")
        fun callDialog(context:Context, name : String, phone : String) {
            var dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.call_dialog)
            dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
            val lp = WindowManager.LayoutParams()
            dialog.getWindow()!!.getAttributes()
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.MATCH_PARENT
            lp.gravity = Gravity.CENTER
            dialog.getWindow()!!.setAttributes(lp)

            var btn_cancel:TextView=dialog.findViewById(R.id.btn_cancel)
            var btn_call:TextView=dialog.findViewById(R.id.btn_call)
            var tv_title:TextView=dialog.findViewById(R.id.tv_title)
            var number:TextView=dialog.findViewById(R.id.number)

            tv_title.setText("Call "+name+" ?")
            number.setText(phone)



            btn_cancel.setOnClickListener {
                dialog.dismiss()
            }

            btn_call.setOnClickListener {
                dialog.dismiss()

                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" +phone))
                context.startActivity(intent)

            }

            dialog.show()
        }

        fun openurl(context: Context,storepojo: Store_pojo){
            var uri = Uri.parse(storepojo.getWebsite()) // missing 'http://' will cause crashed
            var intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        }

        fun checkPermissions(context:Context):Boolean {
            val currentAPIVersion = Build.VERSION.SDK_INT
            if (currentAPIVersion >= Build.VERSION_CODES.M)
            {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) === PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED)
                {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.CAMERA))
                    {
                        ActivityCompat.requestPermissions(context as Activity, arrayOf<String>(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
                    }
                    else
                    {
                        ActivityCompat.requestPermissions(context as Activity, arrayOf<String>(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
                    }
                    return false
                }
                else
                {
                    return true
                }
            }
            else
            {
                return true
            }
        }

        //re-gift dialog

        fun reGiftDialog(context:Context)
        {
            var dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.gift_redeem_dailog)
            dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.darker_gray)
            val lp = WindowManager.LayoutParams()
            dialog.getWindow()!!.getAttributes()
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.MATCH_PARENT
            lp.gravity = Gravity.CENTER
            dialog.getWindow()!!.setAttributes(lp)

            var tv_title:TextView=dialog.findViewById(R.id.tv_title)
            var tv_msg:TextView=dialog.findViewById(R.id.tv_msg)
            var close:ImageView=dialog.findViewById(R.id.close)
            var send_invite:Button=dialog.findViewById(R.id.send_invite)

            tv_title.setText("Share the love!")
            tv_msg.setText("You've passed on joy to someone by sending them a gift that was given to you. Keep the momentum rolling!")
            send_invite.setText("Gift again")

            close.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }

        // progress dailog show
        //private var progressDialog: ProgressDialog?=null
        *//*fun showProgressDialog(context:Context)
        {
            progressDialog = ProgressDialog(context)
            progressDialog!!.setTitle("Loading...")
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()
        }

        fun dismissProgressDialog()
        {
            if (progressDialog!! != null)
            {
                progressDialog!!.dismiss()
            }
        }
        *//*
        private var progressDialog: Dialog?=null
        fun showProgressDialog(context: Context) {
            progressDialog = Dialog(context)
            progressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setContentView(R.layout.custom_progress)
            var spinkit : SpinKitView?=null
            spinkit= progressDialog!!.findViewById(R.id.spin_kit)
            progressDialog!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val circal = CubeGrid()
            spinkit.setIndeterminateDrawable(circal)
            progressDialog!!.show()
            progressDialog!!.show()
        }
        fun dismissProgressDialog(context: Context) {
            if (progressDialog!=null)
            {
                progressDialog!!.dismiss()
            }

        }





        fun isNetworkConnected( context:Context):Boolean
        {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()
        }

        //forgot pin
        fun forgotPinDialog(viewmodel: LoginPinVm?, context: Context)
        {
            var dialog2:Dialog = Dialog(context)
            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog2.setCancelable(false)
            dialog2.setContentView(R.layout.forgot_password)
            dialog2.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
            val lp = WindowManager.LayoutParams()
            lp.copyFrom(dialog2!!.getWindow()!!.getAttributes())
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            lp.gravity = Gravity.CENTER
            dialog2.getWindow()!!.setAttributes(lp)

            var close : ImageView=dialog2.findViewById(R.id.close)
            var forgetpassword:EditText=dialog2.findViewById(R.id.forgetpassword)
            var send_link_button:Button=dialog2.findViewById(R.id.send_link_button)

            close.setOnClickListener {
                dialog2.dismiss()
            }

            send_link_button.setOnClickListener {
                if (forgetpassword.text.toString().trim().isNullOrEmpty() )
                {
                    showtost(context,"Please enter email")
                }
                else if (!isValidEmail(forgetpassword.text.toString().trim())){

                    showtost(context,"Please enter valid email")
                }
                else
                {

                    if (isNetworkConnected(context))
                    {
                        val hashMap:HashMap<String,String> = HashMap<String,String>()

                        hashMap.put("email",forgetpassword.text.toString().trim())
                        viewmodel!!.forgotPinApi(context,hashMap)
                    }
                    else
                    {
                        showtost(context,context.resources.getString(R.string.internet_connection))
                    }

                    dialog2.dismiss()
                }
            }





            dialog2.show()
        }

        fun getAddress(latitude:String, longitude:String,textView: TextView,context: Context):String {
            val result = StringBuilder()
            try
            {
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(latitude.toDouble(), longitude.toDouble(), 1)
                if (addresses.size > 0)
                {
                    val address = addresses.get(0)
                    result.append(address.getLocality()).append("\n")
                    textView.setText(address.getAddressLine(0))
                    Log.d("dgfsdf",addresses.get(0).locality)

                    //  Toast.makeText(activity, "" + address.getLocality(), Toast.LENGTH_SHORT).show()
                    result.append(address.getCountryName())
                }

            }
            catch (e: IOException) {
                Log.e("tag", e.message)
            }
            return result.toString()
        }
        fun showDialoginvite(cardId: String?, position: Int,context: Context) {
            var dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.awesome_invite)
            dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.darker_gray)
            val lp = WindowManager.LayoutParams()
            dialog.getWindow()!!.getAttributes()
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.MATCH_PARENT
            lp.gravity = Gravity.CENTER
            dialog.getWindow()!!.setAttributes(lp)
            var close: ImageView =dialog.findViewById(R.id.close)
            var send_invite:Button=dialog.findViewById(R.id.send_invite)

            close.setOnClickListener {
                dialog.dismiss()
            }

            send_invite.setOnClickListener {
                if (Global_utility.isNetworkConnected(context))
                {
                    val hashMap:HashMap<String,String> = HashMap<String,String>()

                    // hashMap.put("session_id", CSPreferences.readString(context,"session_id")!!)
                    //  hashMap.put("store_id",cardId!!)

                    // storeInviteApi(hashMap,dialog,position)
                }
                else
                {
                    Global_utility.showtost(context,context.resources.getString(R.string.internet_connection))
                }
            }

            dialog.show()
        }

        fun getRoundedCornerBitmap(context: Context,bitmap: ImageView) {
            val mbitmap = (context.getResources().getDrawable(R.drawable.image) as BitmapDrawable).getBitmap()
            val imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig())
            val canvas = Canvas(imageRounded)
            val mpaint = Paint()
            mpaint.setAntiAlias(true)
            mpaint.setShader(BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP))
            canvas.drawRoundRect((RectF(0F, 0F, mbitmap.getWidth().toFloat(), mbitmap.getHeight().toFloat())),
                100F, 100F, mpaint)// Round Image Corner 100 100 100 100
            bitmap.setImageBitmap(imageRounded)
        }

        fun displayLocationSettingsRequest(context:Context) {
            var mLocationCallback : LocationCallback?=null
            var mCurrentLocation: Location? = null


            val REQUEST_LOCATION = 199
            val googleApiClient = GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build()
            googleApiClient.connect()



            val locationRequest = LocationRequest.create()

            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            locationRequest.setInterval(10000)
            locationRequest.setFastestInterval(10000 / 2)
            val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
            builder.setAlwaysShow(true)
            var mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);






            mLocationCallback = object:LocationCallback() {
                override fun onLocationResult(locationResult:LocationResult) {
                    mCurrentLocation = locationResult.lastLocation
                    Log.d("sdddddddddd",mCurrentLocation!!.longitude.toString())
                }
            }
            val result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
            result.setResultCallback(object: ResultCallback<LocationSettingsResult> {
                override fun onResult(result:LocationSettingsResult) {
                    val status = result.getStatus()
                    when (status.getStatusCode()) {
                        LocationSettingsStatusCodes.SUCCESS ->{
                            Log.i("d", "All location settings are satisfied.")

                        }
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                            Log.i("d", "Location settings are not satisfied. Show the user a dialog to upgrade location settings ")
                            try
                            {
                                // Show the dialog by calling startResolutionForResult(), and check the result
                                // in onActivityResult().
                                status.startResolutionForResult(context as Activity, REQUEST_LOCATION)
                            }
                            catch (e: IntentSender.SendIntentException) {
                                Log.i("d", "PendingIntent unable to execute request.")
                            }
                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> Log.i("d", "Location settings are inadequate, and cannot be fixed here. Dialog not created.")
                    }
                }
            })
            if (mFusedLocationClient != null) {
                mFusedLocationClient.removeLocationUpdates(mLocationCallback);
            }

*/

    object Utility {
        val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123


        val MY_PERMISSIONS_REQUEST_USAGE_ACCESS_SETTING = 123

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        fun checkStoragePermission(context: Context): Boolean {
            val currentAPIVersion = Build.VERSION.SDK_INT
            if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) !== PackageManager.PERMISSION_GRANTED
                ) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            context as Activity,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    ) {
                        val alertBuilder = AlertDialog.Builder(context)
                        alertBuilder.setCancelable(true)
                        alertBuilder.setTitle("Permission necessary")
                        alertBuilder.setMessage("External storage permission is necessary")
                        alertBuilder.setPositiveButton(android.R.string.yes,

                            DialogInterface.OnClickListener { dialogInterface, i ->
                                ActivityCompat.requestPermissions(
                                    context,
                                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
                            })
                        val alert = alertBuilder.create()
                        alert.show()
                    } else {
                        ActivityCompat.requestPermissions(
                            context,
                            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                        )
                    }
                    return false
                } else {
                    return true
                }
            } else {
                return true
            }
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        fun checkAccessUsagePermission(context: Context): Boolean {
            val currentAPIVersion = Build.VERSION.SDK_INT
            if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) !== PackageManager.PERMISSION_GRANTED
                ) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            context as Activity,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    ) {
                        val alertBuilder = AlertDialog.Builder(context)
                        alertBuilder.setCancelable(true)
                        alertBuilder.setTitle("Permission necessary")
                        alertBuilder.setMessage("External storage permission is necessary")
                        alertBuilder.setPositiveButton(android.R.string.yes,

                            DialogInterface.OnClickListener { dialogInterface, i ->
                                ActivityCompat.requestPermissions(
                                    context,
                                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
                            })
                        val alert = alertBuilder.create()
                        alert.show()
                    } else {
                        ActivityCompat.requestPermissions(
                            context,
                            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                        )
                    }
                    return false
                } else {
                    return true
                }
            } else {
                return true
            }
        }
    }
    }





