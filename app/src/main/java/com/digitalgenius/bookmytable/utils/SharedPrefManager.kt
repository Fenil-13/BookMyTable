package com.digitalgenius.bookmytable.utils

import android.content.Context
import android.content.SharedPreferences
import com.digitalgenius.bookmytable.api.models.entities.UserData
import com.digitalgenius.bookmytable.utils.SharedPrefManager

class SharedPrefManager internal constructor(private val context: Context) {
    private val MyPREFERENCES = "MyPREFERENCES"
    private val sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor
    fun setStringData(key: String?, value: String?) {
        editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun getStringData(key: String?): String? {
        return sharedPreferences.getString(key, "")
    }

    var userData: UserData
        get() = UserData(
            sharedPreferences.getString("user_auth_id", "")!!,
            sharedPreferences.getString("user_device_token", "")!!,
            sharedPreferences.getString("user_email", "")!!,
            sharedPreferences.getString("user_id", "")!!,
            sharedPreferences.getString("user_location", "")!!,
            sharedPreferences.getString("user_name", "")!!,
            sharedPreferences.getString("user_phone_number", "")!!,
            sharedPreferences.getString("user_profile_pic", "")!!
        )
        set(data) {
            editor = sharedPreferences.edit()
            editor.putString("user_auth_id", data.userAuthId)
            editor.putString("user_id", data.userId)
            editor.putString("user_device_token", data.userDeviceToken)
            editor.putString("user_email", data.userEmail)
            editor.putString("user_location", data.userLocation)
            editor.putString("user_name", data.userName)
            editor.putString("user_phone_number", data.userPhoneNumber)
            editor.putString("user_profile_pic", data.userProfilePic)
            editor.commit()
        }

    companion object {
        private var sharePrefManager: SharedPrefManager? = null
        @JvmStatic
        fun getInstance(context: Context): SharedPrefManager? {
            if (sharePrefManager == null) {
                sharePrefManager = SharedPrefManager(context)
            }
            return sharePrefManager
        }
    }

    init {
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }
}