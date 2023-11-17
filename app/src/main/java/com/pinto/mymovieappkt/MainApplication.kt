package com.pinto.mymovieappkt

import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.pinto.mymovieappkt.utils.LocaleUtils
import com.pinto.mymovieappkt.utils.Storage
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(
            LocaleUtils.getLocalizedContext(
                base,
                Storage(base).getPreferredLocale()
            )
        )
    }

    override fun getWorkManagerConfiguration() = Configuration.Builder()
        .setMinimumLoggingLevel(android.util.Log.INFO)
        .setWorkerFactory(workerFactory)
        .build()
}