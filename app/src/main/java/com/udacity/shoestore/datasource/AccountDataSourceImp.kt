package com.udacity.shoestore.datasource

import android.content.Context
import android.content.SharedPreferences
import com.udacity.shoestore.R

class AccountDataSourceImp(private val context: Context?) : AccountDataSource {

    private var sharedPref: SharedPreferences? = context?.getSharedPreferences(
        context?.getString(R.string.account_preference_file_key),
        Context.MODE_PRIVATE
    )

    override fun getAccount(): String =
        sharedPref?.getString(context?.getString(R.string.user_key), "") ?: ""

    override fun saveAccount(email: String) {
        sharedPref?.let {
            with(it.edit()) {
                putString(context?.getString(R.string.user_key), email)
                apply()
            }
        }
    }

    override fun removeAccount() {
        saveAccount("")
    }
}