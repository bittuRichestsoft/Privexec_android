package com.privexec.adapter

import android.graphics.Color
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
import com.privexec.pojoclass.reportAppByCategory.ReportAppByCategoryPojo
import com.willy.ratingbar.ScaleRatingBar

class ReportAppByCategoryAdapter (var activity: FragmentActivity, var allReviewlist : ArrayList<ReportAppByCategoryPojo.AppsCategoryCount> ) : RecyclerView.Adapter<ReportAppByCategoryAdapter.MyViewHolder>() {

var TAG="ReportAppByCategoryAdapter "
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int  ): MyViewHolder {
        val itemLayoutView = LayoutInflater.from(parent.context).inflate(R.layout.item_chart_category_with_color, null)
        val viewHolder = MyViewHolder(itemLayoutView)
        return viewHolder;
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
     //   if ( allReviewlist[position].categoriesCount>0) {
            holder.iv_categoryColor!!.setColorFilter(Color.parseColor(allReviewlist[position].hexaColor));
            holder.tv_categoryName!!.text = allReviewlist[position].categoriesName+" ("+ allReviewlist[position].categoriesCount+")"
             println(TAG+" .."+  allReviewlist[position].categoriesName+" ("+ allReviewlist[position].categoriesCount+")")
           // }
Log.e(TAG,""+  allReviewlist[position].categoriesName+" "+ allReviewlist[position].categoriesCount)
    }

    override fun getItemCount(): Int {
            if(!allReviewlist.isNullOrEmpty()){
                return allReviewlist.size
            }else{
                return 0
            }
    }

    class MyViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        var iv_categoryColor : ImageView?=null
        var tv_categoryName : TextView?=null
           init {
            iv_categoryColor  = itemView.findViewById(R.id.iv_categoryColor)
            tv_categoryName  = itemView.findViewById(R.id.tv_categoryName)
              }
    }
}
