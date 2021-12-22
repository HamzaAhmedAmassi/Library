package com.h.alamassi.library.datasource

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceHelper {
    companion object {
        private var instance: SharedPreferenceHelper? = null
        private var sharedPreference: SharedPreferences? = null

        fun getInstance(context: Context): SharedPreferenceHelper? {
            if (instance == null) {
                instance = SharedPreferenceHelper()
                sharedPreference =
                    context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            }
            return instance
        }
    }


    fun setString(key: String, value: String) {
        sharedPreference?.edit()?.putString(key, value)?.apply()
    }

    fun getString(key: String, defValue: String = "") =
        sharedPreference?.getString(key, defValue)


    fun setInt(key: String, value: Int) {
        sharedPreference?.edit()?.putInt(key, value)?.apply()
    }

    fun getInt(key: String, defValue: Int): Int =
        sharedPreference?.getInt(key, defValue) ?: 0


    fun setFloat(key: String, value: Float) {
        sharedPreference?.edit()?.putFloat(key, value)?.apply()
    }

    fun getFloat(key: String, defValue: Float): Float =
        sharedPreference?.getFloat(key, defValue) ?: 0.0f


    fun setBoolean(key: String, value: Boolean) {
        sharedPreference?.edit()?.putBoolean(key, value)?.apply()
    }


    fun getBoolean(key: String, defValue: Boolean): Boolean =
        sharedPreference?.getBoolean(key, defValue) ?: false

}