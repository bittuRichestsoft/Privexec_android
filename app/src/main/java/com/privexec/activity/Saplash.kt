package com.privexec.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import com.lee_da_hang_pte_ltd.utills.CSPreferences
import com.privexec.R
import com.privexec.activity.fingersample.MainActivity



class Saplash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.saplash)
        var countDownTimer = object : CountDownTimer(2000, 500) {
            override fun onTick(millisUntilFinished: Long) {
                // mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            override fun onFinish() {
                // countDownTimer.cancel()
             /*   val intent = Intent(this@Saplash, UsageStatsActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()*/
                if (CSPreferences.readString(
                        this@Saplash,
                        "statusStr"
                    ) == "1" && (!CSPreferences.readString(this@Saplash, "session_id").equals(""))
                ) {
                  if(isFingerFeatureAvailable(this@Saplash)) {
                      val intent = Intent(this@Saplash, MainActivity::class.java)
                      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                      startActivity(intent)
                      overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                      finish()
                  }
                    else{
                      val intent = Intent(this@Saplash, Home_Activity::class.java)
                      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                      startActivity(intent)
                      overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                      finish()
                  }
                  } else if (!CSPreferences.readString(this@Saplash, "session_id").equals("")) {
                    val intent = Intent(this@Saplash, Home_Activity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                } else {
                    val intent = Intent(this@Saplash, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                }
            }
        }.start()

    }



    fun isFingerFeatureAvailable(context: Context): Boolean {
        val fingerprintManager = FingerprintManagerCompat.from(context)
        return fingerprintManager.isHardwareDetected && fingerprintManager.hasEnrolledFingerprints()
    }
}
