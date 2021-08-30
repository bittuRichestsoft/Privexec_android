package com.privexec.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.privexec.R
import com.privexec.pojoclass.All_ratingPojo
import com.willy.ratingbar.ScaleRatingBar

class Rating_Adapter (var activity: FragmentActivity,var allReviewlist : ArrayList<All_ratingPojo.Rating.AllReview> ) : RecyclerView.Adapter<Rating_Adapter.MyViewHolder>() {

var TAG="Rating_Adapter "
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int  ): MyViewHolder {
        val itemLayoutView = LayoutInflater.from(parent.context).inflate(R.layout.item_review_row, null)
        val viewHolder = MyViewHolder(itemLayoutView)
        return viewHolder;
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (!allReviewlist[position].getName().isNullOrEmpty()) {
            holder.text_appname!!.text = allReviewlist[position].getName()
            holder.app_dicraption!!.text = allReviewlist[position].getReview()
             println("ratingBar0.."+ allReviewlist[position].getAvgRating()!!.toFloat())
            holder.ratingbar!!.rating = allReviewlist[position].getAvgRating()!!.toFloat()
      Log.e(TAG,"rating = "+allReviewlist[position].getAvgRating()!!)
        }

    }

    override fun getItemCount(): Int {
            if(!allReviewlist.isNullOrEmpty()){
                return allReviewlist.size
            }else{
                return 0
            }
    }

    class MyViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        var img_app : ImageView?=null
        var text_appname : TextView?=null
        var app_dicraption : TextView?=null
        var ratingbar : ScaleRatingBar?=null
        init {
            img_app  = itemView.findViewById(R.id.image_app)
            text_appname  = itemView.findViewById(R.id.app_tital)
            app_dicraption  = itemView.findViewById(R.id.app_dicraption)
            ratingbar = itemView.findViewById(R.id.ratingbar)
        }
    }
}
