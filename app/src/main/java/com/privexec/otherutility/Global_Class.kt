package com.privexec.otherutility

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.privexec.R

class Global_Class {

    companion object{

        fun fragment(fragmentActivity: FragmentActivity, fragment: Fragment, addToBackStack: Boolean ) {
            if (addToBackStack) {
                fragmentActivity.getSupportFragmentManager()
                    .beginTransaction().add(R.id./*frame*/drawer_layout, fragment)
                    .addToBackStack(null).commit()
            }
            else {
                fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id./*frame*/drawer_layout, fragment).commit()
            }
        }
    }
}