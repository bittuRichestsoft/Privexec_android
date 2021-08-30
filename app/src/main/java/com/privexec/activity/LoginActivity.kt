package com.privexec.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.lee_da_hang_pte_ltd.utills.CSPreferences
import com.lee_da_hang_pte_ltd.utills.Global_utility
import com.privexec.R
import com.privexec.databinding.ActivityLoginBinding
import com.privexec.viewmodel.LoginVM
import androidx.lifecycle.Observer
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import com.privexec.Api_Call.WebAPicall

class LoginActivity : AppCompatActivity() {
    private var email: String? = null
    private var token: String? = null
    private var loginbinding: ActivityLoginBinding? = null
var TAG="LoginActivity ";
    override fun onCreate(savedInstanceState: Bundle?) {
       FirebaseApp.initializeApp(applicationContext)
        super.onCreate(savedInstanceState)
        loginbinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        loginbinding!!.login.setOnClickListener {
            validation()
        }

        loginbinding!!.signup.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignUp::class.java))
        }
        loginbinding!!.forgotPassword.setOnClickListener {
            showDialog()
        }
        loginbinding!!.privacyPolicy.setOnClickListener {
            val intent = Intent(this@LoginActivity, WebViewDataActivity::class.java)
            var bundle = Bundle()
            bundle.putString("title", "Privacy Policy")
            bundle.putString("url", WebAPicall.BASE_URL_WithApi+ "privacy-policy")
            intent.putExtras(bundle)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

    }


    fun validation() {
        email = loginbinding?.lEmail?.getText()?.trim().toString()
      if(token==null || token=="" ) {
          token = FirebaseInstanceId.getInstance().getToken()
          Log.e(TAG, "" + token)
      }
        if (email!!.isEmpty() || !Global_utility.isValidEmail(email!!)) {
            Global_utility.showtost(this, resources.getString(R.string.validation_emial))

        } else if (loginbinding!!.lPassword.text.toString().trim().length < 6) {
            Global_utility.showtost(this, resources.getString(R.string.validation_password))
        } else {
            // startActivity(Intent(this@LoginActivity, Home_Activity::class.java))
            if (Global_utility.isNetworkConnected(this)) {

                val hashMap: HashMap<String, String> =
                    HashMap<String, String>() //define empty hashmap            hashMap!!.put("name","dsasdsa")
                hashMap!!.put("email", email!!)
                hashMap!!.put("password", loginbinding!!.lPassword.text.toString())
                hashMap!!.put("device_token",""+token)
                hashMap!!.put("device_type", "1")
                callApi(hashMap, this)

            } else {
                Global_utility.showtost(this, resources.getString(R.string.internet_connection))
            }

        }
    }

    //Register  Api call ...........................................
    private fun callApi(hashMap: HashMap<String, String>, context: Context) {
        val loginViewmodel = ViewModelProviders.of(this).get(LoginVM::class.java)
        loginViewmodel.login(hashMap, context)!!.observe(this, Observer {
            if (it.getStatus().equals("200")) {
                CSPreferences.putString(
                    context,
                    "session_id",
                    it.getProfile()?.getSessionId().toString()
                )
                CSPreferences.putString(context, "email", it.getProfile()?.getEmail().toString())
                CSPreferences.putString(
                    context,
                    "phone_number",
                    it.getProfile()?.getPhone().toString()
                )
                CSPreferences.putString(
                    context,
                    "user_name",
                    it.getProfile()?.getUserName().toString()
                )
                CSPreferences.putString(
                    context,
                    "notifyNotUsed",
                    it.getProfile()?.getNotifyTime()!! + " Month"
                )
                CSPreferences.putString(context, "dsarStatus", it.getProfile()?.getDsarStatus()!!)
                CSPreferences.putString(
                    context,
                    "changePassword",
                    it.getProfile()?.getPasswordTime()!! + " Month"
                )
                CSPreferences.putString(context, "user_id", it.getProfile()?.getUserId().toString())
                CSPreferences.putBolean(context, "login", true)
                CSPreferences.putString(
                    context,
                    "user_country",
                    it.getProfile()?.getCountry().toString()
                )
                CSPreferences.putString(
                    context,
                    "user_dob",
                    it.getProfile()?.getDateOfBirth().toString()
                )
                CSPreferences.putString(
                    context,
                    "user_img",
                    it.getProfile()?.getProfileImage().toString()
                )
                CSPreferences.putString(
                    context,
                    "statusStr",
                    it.getProfile()?.getTouchId().toString()
                )
                CSPreferences.putString(context, "push_active_app_status", "1")

                //      it.getMessage()?.let { it1 -> Global_utility.showtost(context, it1) }

                val intent = Intent(this@LoginActivity, Home_Activity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            } else {
                it.getMessage()?.let { it1 -> Global_utility.showtost(context, it1) }
            }
        })
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_forget_psd)
        var emaill = dialog.findViewById(R.id.et_email) as EditText
        val yesBtn = dialog.findViewById(R.id.btnSend) as Button
        val closeBtn = dialog.findViewById(R.id.iv_closeDialog) as ImageView
        yesBtn.setOnClickListener {

            if (emaill.text.toString()!!
                    .isEmpty() || !Global_utility.isValidEmail(emaill.text.toString()!!)
            ) {
                Global_utility.showtost(this, resources.getString(R.string.validation_emial))

            } else {
                if (Global_utility.isNetworkConnected(this)) {
                    forgotPasswordApi(this, emaill.text.toString())
                } else {
                    Global_utility.showtost(this, resources.getString(R.string.internet_connection))
                }
            }
            dialog.dismiss()
            dialog.cancel()
        }
        closeBtn.setOnClickListener {
            dialog.dismiss()
            dialog.cancel()
        }

        dialog.show()

    }

    private fun forgotPasswordApi(context: LoginActivity, sessionid: String) {
        val myappViewmodel = ViewModelProviders.of(this).get(LoginVM::class.java)
        myappViewmodel.forgotPasswordApi(sessionid, context)!!.observe(this, Observer {
            if (it.getError()!!.equals("bad_request")) {

                it.getMessage()?.let { it1 -> Global_utility.showtost(context, it1) }
            } else {
                // myappInterface.getmyapp(it.getResult()!!)
                it.getMessage()?.let { it1 -> Global_utility.showtost(context, it1) }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        token = FirebaseInstanceId.getInstance().getToken()

    }
}
