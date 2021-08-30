package com.privexec.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lee_da_hang_pte_ltd.utills.Global_utility
import com.privexec.Api_Call.WebAPicall
import com.privexec.R
import kotlinx.android.synthetic.main.activity_privacy_policies.*
import kotlinx.android.synthetic.main.header.*


class WebViewDataActivity : AppCompatActivity(), View.OnClickListener {
var strOldIntentTitle =""
    var strOldIntentUrl=""
    var TAG="WebViewDataActivity "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policies)
    img_headerback.setOnClickListener(this)
         strOldIntentTitle=""+intent.extras!!.getString("title")
        strOldIntentUrl=intent.extras!!.getString("url").toString().replace(" ".toRegex(),"%20")

       Log.e(TAG,"strOldIntentTitle=$strOldIntentTitle \n strOldIntentUrl=$strOldIntentUrl" )
        if(!strOldIntentTitle.equals(""))
        {
            tv_headerTitle.setText(strOldIntentTitle)
            about_webview.getSettings().setJavaScriptEnabled(true)
            about_webview?.loadUrl(""+strOldIntentUrl)

        }
        else{
            tv_headerTitle.setText("Privacy Policy")
            about_webview.getSettings().setJavaScriptEnabled(true)
            about_webview?.loadUrl( WebAPicall.BASE_URL_WithApi+ "privacy-policy")
        }
        about_webview?.webViewClient = MyWebViewClient(this)
    }

    inner class MyWebViewClient internal constructor(private val activity: Activity) : WebViewClient() {
        @SuppressLint("NewApi")
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {

            val url: String = request?.url.toString();
            Log.e(TAG,"shouldOverrideUrlLoading="+url)
           // Global_utility.showDialog(activity)
            progressBar?.visibility = View.VISIBLE
            view?.loadUrl(url)

            return true
         }

        override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
            Global_utility.showDialog(activity)
            webView.loadUrl(url)
            return true
        }

        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
            Toast.makeText(activity, "Got Error! $error", Toast.LENGTH_SHORT).show()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
          try {
              Global_utility.hideDialog(activity)
          }
          catch (exp :Exception)
          {
          }
            progressBar?.visibility = View.GONE
        }

}


    override fun onClick(v: View?) {
when(v!!.id)
{
    R.id.img_headerback->{
        onBackPressed()
    }
}

    }
}