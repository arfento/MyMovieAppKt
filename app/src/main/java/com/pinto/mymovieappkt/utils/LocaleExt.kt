package com.pinto.mymovieappkt.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.LocaleList
import androidx.core.os.ConfigurationCompat
import java.util.Locale


fun Context.recreateTask() {
    this.packageManager
        .getLaunchIntentForPackage(this.packageName)
        ?.let { intent ->
            val restartIntent = Intent.makeRestartActivityTask(intent.component)
            this.startActivity(restartIntent)
            Runtime.getRuntime().exit(0)
        }
}

class LocaleUtils{
    companion object {
        val supportedLocales = listOf("en", "in", "de")
        const val OPTION_PHONE_LANGUAGE = "sys"

        fun getLocaleFromPrefCode(prefCode: String): Locale {
            val localeCode = if (prefCode != OPTION_PHONE_LANGUAGE) {
                prefCode
            } else {
                val systemLang =
                    ConfigurationCompat.getLocales(Resources.getSystem().configuration)
                        .get(0)?.language
                if (systemLang in supportedLocales) {
                    systemLang
                } else {
                    "en"
                }
            }
            return localeCode?.let { Locale(it) }!!
        }

        fun getLocalizedConfiguration(prefLocaleCode: String): Configuration {
            val locale = getLocaleFromPrefCode(prefLocaleCode)
            return getLocalizedConfiguration(locale)
        }

        fun getLocalizedConfiguration(locale: Locale): Configuration {
            val config = Configuration()
            return config.apply {
                config.setLayoutDirection(locale)
                config.setLocale(locale)
                val localeList = LocaleList(locale)
                LocaleList.setDefault(localeList)
                config.setLocales(localeList)
            }
        }

        fun getLocalizedContext(baseContext: Context, prefLocaleCode: String): Context {
            val currentLocale = getLocaleFromPrefCode(prefLocaleCode)
            val baseLocale = getLocaleFromConfiguration(baseContext.resources.configuration)
            Locale.setDefault(currentLocale)
            return if (!baseLocale.toString().equals(currentLocale.toString(), ignoreCase = true)) {
                val config = getLocalizedConfiguration(currentLocale)
                baseContext.createConfigurationContext(config)
                baseContext
            } else {
                baseContext
            }
        }

        fun applyLocalizedContext(baseContext: Context, prefLocaleCode: String) {
            val currentLocale = getLocaleFromPrefCode(prefLocaleCode)
            val baseLocale = getLocaleFromConfiguration(baseContext.resources.configuration)
            Locale.setDefault(currentLocale)
            if (!baseLocale.toString().equals(currentLocale.toString(), ignoreCase = true)) {
                val config = getLocalizedConfiguration(currentLocale)
                baseContext.createConfigurationContext(config)
            }
        }

        private fun getLocaleFromConfiguration(configuration: Configuration): Locale {
            return configuration.locales.get(0)
        }

        fun getLocalizedResources(resources: Resources, prefLocaleCode: String): Resources {
            val locale = getLocaleFromPrefCode(prefLocaleCode)
            val config = resources.configuration
            @Suppress("DEPRECATION")
            config.locale = locale
            config.setLayoutDirection(locale)

            @Suppress("DEPRECATION")
            resources.updateConfiguration(config, resources.displayMetrics)
            return resources
        }
    }
}

class Storage(context: Context){
    private  var preferences: SharedPreferences =
        context.getSharedPreferences("sp", Context.MODE_PRIVATE)

    fun getPreferredLocale(): String {
        return preferences.getString("preferred_locale", LocaleUtils.OPTION_PHONE_LANGUAGE)!!
    }

    fun setPreferredLocale(localeCode : String){
        preferences.edit().putString("preferred_locale", localeCode).apply()
    }
}