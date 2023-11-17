package com.pinto.mymovieappkt.utils

import android.content.SharedPreferences
import com.google.gson.Gson
import com.pinto.mymovieappkt.utils.Constants.LIGHT
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesHelper @Inject constructor(
    private val gson: Gson,
    private val sharedPref: SharedPreferences
) {

    var darkMode
        get() = sharedPref.getInt("DARK_STATUS", LIGHT)
        set(value) = sharedPref.edit().putInt("DARK_STATUS", value).apply()


    fun <T> save(key: String, value: T) {
        with(sharedPref.edit()) {
            putString(key, gson.toJson(value))
            apply()
        }
    }

    fun remove(key: String) {
        with(sharedPref.edit().remove(key)) {
            apply()
            commit()
        }
    }

    fun read(key: String, defaultValue: String): String? {
        return sharedPref.getString(key, defaultValue)
    }
}