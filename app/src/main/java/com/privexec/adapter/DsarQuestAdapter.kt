package com.privexec.adapter

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.privexec.R
import com.privexec.pojoclass.AllDsarQuestionPojo


class DsarQuestAdapter (var activity: FragmentActivity, var questlist : ArrayList<AllDsarQuestionPojo.AllDsarQuestionModel> ) : RecyclerView.Adapter<DsarQuestAdapter.MyViewHolder>() {
var TAG="DsarQuestAdapter "

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int  ): MyViewHolder {

        val itemLayoutView = LayoutInflater.from(parent.context).inflate(R.layout.item_dsar_question, null)

        val viewHolder = MyViewHolder(itemLayoutView)
        return viewHolder;

    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d(TAG,"Quest $position ="+ questlist[position].questions)

        if (!questlist[position].questions.isNullOrEmpty()) {
            holder.tv_dsarQuestion!!.text = questlist[position].questions
            if ( questlist[position].optional==1) {
                holder.tv_dsarQuestion!!.text = questlist[position].questions+" ( optional )"
                //      holder.tv_dsarQueOptionalOrNot!!.text = questlist[position].optional
            }

            if(holder.ll_dsarQues!=null)
            {
                  val display = (activity).windowManager.defaultDisplay
                holder.ll_dsarQues!!.getLayoutParams().width = Math.round((display.width ).toFloat())
            }
        }

        holder.et_dsarAnswer!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                questlist[position].answer=""+s
            Log.e(TAG,"textchanged = "+s)
            }
        })


    }

    override fun getItemCount(): Int {
        if(!questlist.isNullOrEmpty()){
            return questlist.size
        }else{
            return 0
        }
    }

    class MyViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
    //    var img_app : ImageView?=null
        var tv_dsarQuestion : TextView?=null
        var et_dsarAnswer : EditText?=null
        var tv_dsarQueOptionalOrNot : TextView?=null
        var ll_dsarQues : LinearLayout?=null

        init {
          //  img_app  = itemView.findViewById(R.id.image_app)
            tv_dsarQuestion  = itemView.findViewById(R.id.tv_dsarQuestion)
            et_dsarAnswer  = itemView.findViewById(R.id.et_dsarAnswer)
            tv_dsarQueOptionalOrNot = itemView.findViewById(R.id.tv_dsarQueOptionalOrNot)
            ll_dsarQues = itemView.findViewById(R.id.ll_itemDsarQues)
        }
    }
}
