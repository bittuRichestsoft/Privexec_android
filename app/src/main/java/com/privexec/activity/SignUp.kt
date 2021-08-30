package com.privexec.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.iid.FirebaseInstanceId
import com.lee_da_hang_pte_ltd.utills.CSPreferences
import com.lee_da_hang_pte_ltd.utills.Global_utility
import com.privexec.Api_Call.WebAPicall
import com.privexec.R
import com.privexec.databinding.ActivitySignUpBinding
import com.privexec.viewmodel.SignUpVM
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    var activitySignUpBinding : ActivitySignUpBinding?=null
    private var email: String? = null
    private var token: String? = null
    var TAG="SignUp ";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)


activitySignUpBinding!!.signupbuttion.setOnClickListener {
    validation()
}
         tv_gotoLogin.setOnClickListener {

           /*  var intent =Intent(this,LoginActivity::class.java)
             intent.addFlags(   Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
              startActivity(intent)
             overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
             */finish()
        }
        activitySignUpBinding!!.ivCloseSignup.setOnClickListener {
            finish()
        }
        activitySignUpBinding!!.tvPrivacyPolicy.setOnClickListener {
            val intent = Intent(this@SignUp, WebViewDataActivity::class.java)
            var bundle = Bundle()
            bundle.putString("title","Privacy Policy")
            bundle.putString("url",WebAPicall.BASE_URL_WithApi+"privacy-policy")
            intent.putExtras(bundle)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    
    }

//demso123@mailinator.com
    //12345678
    fun validation(){
    token=CSPreferences.readString(
        this,
        "regId"
    )
    if(token==null|| token=="" || token=="null") {
        token = FirebaseInstanceId.getInstance().getToken()
        CSPreferences.putString(this, "regId",""+token)
    }


    email = activitySignUpBinding?.email?.getText()?.trim().toString()
        if (activitySignUpBinding!!.uname.text.toString().isEmpty()) {
            Global_utility.showtost(this,resources.getString(R.string.validation_uname))

        }else if (email!!.isEmpty() || !Global_utility.isValidEmail(email!!)){
            Global_utility.showtost(this,resources.getString(R.string.validation_emial))

        }else if (activitySignUpBinding!!.password.text.toString().trim().length < 6){
            Global_utility.showtost(this,resources.getString(R.string.validation_password))

        }else if(activitySignUpBinding!!.cPassword.text.toString().trim().isEmpty()){
            Global_utility.showtost(this," Confirm Your Password")
        }else if (activitySignUpBinding!!.cPassword.text.trim().toString().equals(""+activitySignUpBinding!!.password.text.trim().toString())){
            if (Global_utility.isNetworkConnected(this)) {
                val hashMap: HashMap<String, String> = HashMap<String, String>() //define empty hashmap            hashMap!!.put("name","dsasdsa")
                hashMap!!.put("name",activitySignUpBinding!!.uname.text.toString())
                hashMap!!.put("email",email!!)
                hashMap!!.put("password",activitySignUpBinding!!.password.text.trim().toString())
                hashMap!!.put("confirm_password",activitySignUpBinding!!.password.text.trim().toString())
                hashMap!!.put("device_token",""+token)
                hashMap!!.put("device_type","1")

                callApi(hashMap, this)
            }else{
                Global_utility.showtost(this,resources.getString(R.string.internet_connection))
            }

        }else{
            Global_utility.showtost(this,resources.getString(R.string.validation_cPassword))



        }
    }


    //Register  Api call ...........................................
    private fun callApi(hashMap: HashMap<String , String>,context : Context){
        val signupViewmodel = ViewModelProviders.of(this).get(SignUpVM::class.java)
        signupViewmodel.registerApi(hashMap,context)!!.observe(this, Observer {
            if (it.getStatus().equals("200")){
                CSPreferences.putString(context, "session_id", it.getProfile()?.getSessionId().toString())
                CSPreferences.putString(context,"sid",it.getProfile()?.getSessionId().toString())
                CSPreferences.putString(context,"phone_nuession_mber",it.getProfile()?.getPhone().toString())

                CSPreferences.putString(context, "email", it.getProfile()?.getEmail().toString())
                CSPreferences.putString(context, "phone_number", it.getProfile()?.getPhone().toString())
                CSPreferences.putString(context, "user_name", it.getProfile()?.getUserName().toString())
                CSPreferences.putString(context, "user_id", it.getProfile()?.getUserId().toString())
                CSPreferences.putString(context, "notifyNotUsed", it.getProfile()?.getNotifyTime()!! + " Month")
                CSPreferences.putString(context, "dsarStatus", it.getProfile()?.getDsarStatus()!! )
                CSPreferences.putString(context, "changePassword",it.getProfile()?.getPasswordTime()!!+" Month")
                CSPreferences.putString(context, "user_id", it.getProfile()?.getUserId().toString())
                CSPreferences.putBolean(context, "login", true)
                CSPreferences.putString(context, "user_country", it.getProfile()?.getCountry().toString())
                CSPreferences.putString(context, "user_dob", it.getProfile()?.getDateOfBirth().toString())
                CSPreferences.putString(context, "user_img", it.getProfile()?.getProfileImage().toString())
                CSPreferences.putString(context, "statusStr",  it.getProfile()?.getTouchId().toString())
                CSPreferences.putString(context, "push_active_app_status","1")


                it.getMessage()?.let { it1 -> Global_utility.showtost(context, it1) }


                val intent = Intent(this@SignUp, Home_Activity::class.java)
                intent.addFlags(   Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()

            }else{
                it.getMessage()?.let { it1 -> Global_utility.showtost(context, it1) }
            }
        })
    }
    override fun onResume() {
        super.onResume()
        token = FirebaseInstanceId.getInstance().getToken()

    }
}
