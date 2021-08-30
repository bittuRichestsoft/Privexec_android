package com.privexec.fragmnet

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lee_da_hang_pte_ltd.utills.CSPreferences
import com.lee_da_hang_pte_ltd.utills.Global_utility
import com.privexec.R
import com.privexec.viewmodel.LoginVM
import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.android.synthetic.main.header.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChangePassword.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChangePasswordFrag : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        img_headerback.setOnClickListener(this)
        tv_headerTitle.setText("Change Password")
        ll_fragChangePsd.setOnClickListener(this)
        btn_changePsd.setOnClickListener(this)
    }
    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChangePasswordFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(v: View?) {
          when(v!!.id)
        {
            R.id.ll_fragChangePsd->
            {
                   }
            R.id.img_headerback->
            {
                Global_utility.hideKeyboardView(ll_fragChangePsd, activity!!)
                val manager: FragmentManager = activity!!.supportFragmentManager
                manager.popBackStack()

            }
              R.id.btn_changePsd->
              {
                  chkFieldsValidate()
       }

        }
    }


    private fun chkFieldsValidate() {

        if (et_oldPsd.text.toString().isEmpty()) {
            Global_utility.showtost(activity!!,resources.getString(R.string.validation_password))

        }else if (etnewPsd.text.toString()!!.isEmpty() || etnewPsd.text.length<6){
            Global_utility.showtost(activity!!,resources.getString(R.string.validation_password))

        }else if (! etConfirmPsd.text.toString().equals(etnewPsd.text.toString()) ) {
            Global_utility.showtost(activity!!, resources.getString(R.string.password_not_confirmed))
        }else{
            if (Global_utility.isNetworkConnected(activity!!)) {
                val hashMap: HashMap<String, String> = HashMap<String, String>() //define empty hashmap
                hashMap!!.put("current_password",et_oldPsd.text.toString()!!)
                hashMap!!.put("new_password",etnewPsd.text.toString())
                hashMap!!.put("confirm_password",""+etConfirmPsd.text.toString().trim())
                hashMap!!.put("session_id",""+ CSPreferences.readString(activity!!,"session_id"))

               callUpdatePasswordApi(hashMap, activity!!)
           }else{
                Global_utility.showtost(activity!!,resources.getString(R.string.internet_connection))
            }

        }
    }

    private fun callUpdatePasswordApi(hashMap: java.util.HashMap<String, String>, context: Context) {


        val updatePasswordModel = ViewModelProviders.of(this).get(LoginVM::class.java)
        updatePasswordModel.UpdatePasswordApi(hashMap, context)!!.observe(this, Observer {

            if (it.error!!.equals("")) {
                 it.message?.let { it1 -> Global_utility.showtost(context, it1) }
                img_headerback.callOnClick()
             } else
                if (it.error!!.equals("bad_request")) {

                it.message?.let { it1 -> Global_utility.showtost(context, it1) }
            }
else{
                    it.message?.let { it1 -> Global_utility.showtost(context, it1) }

                }
        })
    }

}