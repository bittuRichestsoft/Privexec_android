package com.privexec.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.privexec.R
import com.privexec.pojoclass.SearchedAppsPojo
import com.willy.ratingbar.ScaleRatingBar

class SearchedAppForRate_Adapter(
    var activity: FragmentActivity,
    var allSearchedApplist: ArrayList<SearchedAppsPojo.Result>
) : RecyclerView.Adapter<SearchedAppForRate_Adapter.MyViewHolder>() {

    var TAG = "SearchedAppForRate_Adapter "
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemLayoutView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_privacyrating_row, null)
        val viewHolder = MyViewHolder(itemLayoutView)
        return viewHolder;
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.ratingbar!!.visibility = View.GONE
        holder.chkBox!!.visibility = View.VISIBLE
      //  Log.e(TAG, "onBindViewHolder=" + allSearchedApplist[position].name)
        if (!allSearchedApplist[position].name.isNullOrEmpty()) {
            Glide.with(activity).load(allSearchedApplist[position].image)
                .into(holder.img_app!!)
            holder.text_appname!!.text = allSearchedApplist[position].name

            if (allSearchedApplist[position].isSelectedChk == true) {
                holder.chkBox!!.isChecked = allSearchedApplist[position].isSelectedChk!!
            }

            holder.chkBox!!.setOnCheckedChangeListener(object :
                CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                    if (isChecked) {
                        allSearchedApplist[position].isSelectedChk = true
                    } else {
                        allSearchedApplist[position].isSelectedChk = false
                    }
                    //    notifyDataSetChanged()
                }
            })
        }
    }

/*
    override fun getItemCount(): Int {
        if (!allSearchedApplist.isNullOrEmpty()) {
            return allSearchedApplist.size
        } else {
            return 0
        }
    }
*/

    override fun getItemCount(): Int {
        return allSearchedApplist.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img_app: ImageView? = null
        var text_appname: TextView? = null
        var chkBox: CheckBox? = null
        var ratingbar: ScaleRatingBar? = null

        init {
            img_app = itemView.findViewById(R.id.img_app)
            text_appname = itemView.findViewById(R.id.text_appname)
            chkBox = itemView.findViewById(R.id.chk_box)
            ratingbar = itemView.findViewById(R.id.ratingbar)
        }
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
