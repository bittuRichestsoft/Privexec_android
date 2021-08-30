package com.privexec.adapter

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.lee_da_hang_pte_ltd.utills.CSPreferences
import com.lee_da_hang_pte_ltd.utills.Global_utility
import com.privexec.R
import com.privexec.activity.Saplash
import com.privexec.fragmnet.Myapp_deatal
import com.privexec.fragmnet.RatingDetail
import com.privexec.otherutility.Global_Class
import com.privexec.pojoclass.All_ratingPojo
import com.privexec.viewmodel.MyAppVM
import com.privexec.viewmodel.RatingDetailVM
import com.willy.ratingbar.ScaleRatingBar
import io.reactivex.disposables.CompositeDisposable

class PrivacyRating_Adapter (var activity: FragmentActivity,var ratinglist : ArrayList<All_ratingPojo.Rating>,var strFrom:String) : RecyclerView.Adapter<PrivacyRating_Adapter.MyViewHolder>() {
  var TAG="PrivacyRating_Adapter "

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int  ): MyViewHolder {
        val itemLayoutView = LayoutInflater.from(parent.context).inflate(R.layout.item_privacyrating_row, null)
        val viewHolder = MyViewHolder(itemLayoutView)
        return viewHolder;
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
Log.e(TAG,"onBindViewHolder ratinglist.size="+ratinglist.size)
            if(ratinglist.get(position).getId()!=null) {
                Global_Class.fragment(
                    activity!!, RatingDetail.newInstance(
                        ratinglist.get(position),
                        ratinglist[position].getDetail()!!
                            .getAllReviews() as ArrayList<All_ratingPojo.Rating.AllReview>,strFrom
                    ), true
                )
            }
            else{
                if (Global_utility.isNetworkConnected(activity!!)) {
                    getRatingByPkgName( activity!!, ratinglist.get(position).getAndroidPackageName().toString())
                } else {
                    Global_utility.showtost(
                        activity!!,
                        activity!!.resources.getString(R.string.internet_connection)
                    )
                }
            }
            }

        if (!ratinglist[position].getTitle().isNullOrEmpty()) {
            if (ratinglist[position].getAvgRating()!=null) {
                val prova =
                    ratinglist[position].getAvgRating() // float literal is auto-boxed to a Float
                //Log.d("sdsd",prova)
                prova?.let { holder.ratingbar?.setRating(it.toFloat()) };
            }



            holder.text_appname!!.text = ratinglist[position].getTitle()
            try {
                var strAppPkgName = "" +ratinglist[position].getAndroidPackageName()
                strAppPkgName = strAppPkgName!!.replace("\"", "")
                strAppPkgName = strAppPkgName!!.replace("\"", "")

                val icon = (activity as Activity)!!.applicationContext.getPackageManager()
                    .getApplicationIcon(strAppPkgName)
                    /*getAppIconByPackageName(strAppPkgName, activity)*/
                holder.img_app!!.setImageDrawable(icon)

            } catch (excep: Exception) {
                Log.e(
                    "PrivacyRating_Adapter",
                    "getAppIconByPackageName()  excep=" + excep.toString()
                )
                Glide.with(activity).load(ratinglist[position].getImage())
                    .into(holder.img_app!!)  }
        }
    }

    private fun getRatingByPkgName(  activity: FragmentActivity, strPkgName:String) {

        val reatingVM = ViewModelProviders.of(activity!!).get(RatingDetailVM::class.java)
          reatingVM.get_ratingWithPkgName(
            CSPreferences.readString(activity!!, "session_id").toString(),
            "" + strPkgName,
            activity
        )!!.observe(activity, Observer {
            Log.d("Myapp_adapter" + " appdata()", "api getStatus : " + it.getStatus()!!)

            if ((it.getStatus()!!) == 200) {

                Global_utility.hideDialog(activity!!)
                Global_utility.hideDialog(activity!!)
                if(it.getRating()!!.size == 0)
                {
                    it.getMessage()?.let { it1 -> Global_utility.showtost(activity!!, ""+it.getMessage()) }
                }

                if (it.getRating()!!.size > 0) {
                    Global_Class.fragment(
                        activity!!, RatingDetail.newInstance(
                            it.getRating()!!.get(0),
                            it.getRating()!!.get(0).getDetail()!!
                                .getAllReviews() as ArrayList<All_ratingPojo.Rating.AllReview>,strFrom
                        ), true)
                }
            } else
                if (it.getStatus()!!.equals(403)) {
                    CSPreferences.putString(activity, "session_id", "")
                    val intent = Intent(activity!!, Saplash::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    activity!!.startActivity(intent)
                    activity!!.overridePendingTransition(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                    )
                    activity!!.finish()
                } else {
                it.getMessage()?.let { it1 -> Global_utility.showtost(activity!!, it1) }
            }
        })
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
          return null
/*
            ContextCompat.getDrawable(
                (activity as Activity)!!.applicationContext,
                R.mipmap.ic_launcher
            )
*/

        }
        if (!excep.equals(""))
            Log.e("PrivacyRating_Adapter", "getAppIconByPackageName()  excep=" + excep.toString())


        return drawable
    }

    override fun getItemCount(): Int {
        return ratinglist.size
    }

    class MyViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

var img_app : ImageView?=null
var text_appname : TextView?=null
var ratingbar : ScaleRatingBar?=null
        init {
            img_app  = itemView.findViewById(R.id.img_app)
            text_appname  = itemView.findViewById(R.id.text_appname)
            ratingbar  = itemView.findViewById(R.id.ratingbar)
        }
    }
}
