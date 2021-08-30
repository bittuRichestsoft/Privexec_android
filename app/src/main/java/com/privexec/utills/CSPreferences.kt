package com.lee_da_hang_pte_ltd.utills

import android.content.Context
import android.content.SharedPreferences

class CSPreferences(){

    companion object {

        private fun getPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences("CS_PREF", Context.MODE_PRIVATE)
        }

        fun readString(context: Context, key: String): String? {
            return getPreferences(context).getString(key, "")
        }

        fun putString(context: Context, key: String, value: String) {
            getPreferences(context).edit().putString(key, value).apply()
        }

        fun getString(context: Context, key: String, value: String) {
            getPreferences(context).getString(key, value)
        }

        fun clearPref(context: Context) {
            getPreferences(context).edit().clear().apply()
        }

        fun putBolean(context: Context, key: String, value: Boolean) {
            getPreferences(context).edit().putBoolean(key, value).apply()
        }

        fun getBoolean(context: Context, key: String): Boolean {
            return getPreferences(context).getBoolean(key, false)
        }
    }
}