package com.pinto.mymovieappkt.presentation.screen

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pinto.mymovieappkt.R
import com.pinto.mymovieappkt.databinding.ActivityMainBinding
import com.pinto.mymovieappkt.utils.Constants
import com.pinto.mymovieappkt.utils.Constants.DARK
import com.pinto.mymovieappkt.utils.Constants.IS_FIRST_LAUNCH
import com.pinto.mymovieappkt.utils.Constants.LIGHT
import com.pinto.mymovieappkt.utils.Constants.LOCALE_KEY
import com.pinto.mymovieappkt.utils.LocaleUtils
import com.pinto.mymovieappkt.utils.SharedPreferencesHelper
import com.pinto.mymovieappkt.utils.Storage
import com.pinto.mymovieappkt.utils.isDarkColor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var oldPrefLocaleCode: String

    @Inject
    lateinit var dataStore: DataStore<Preferences>

    @Inject
    lateinit var preferencesHelper: SharedPreferencesHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        applyCurrentTheme()


        if (getIsFirstLaunch()) showAlertDialog()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavBar.setupWithNavController(navController)

        updateThemeAndStatusBarOnDestinationChange(binding, navController)

    }

    private fun updateThemeAndStatusBarOnDestinationChange(
        binding: ActivityMainBinding,
        navController: NavController,
    ) {
        with(WindowInsetsControllerCompat(window, window.decorView)) {
            navController.addOnDestinationChangedListener { _, destination, bundle ->
                window.statusBarColor = when (destination.id) {
                    R.id.homeFragment, R.id.searchFragment, R.id.favoritesFragment, R.id.settingsFragment -> {
                        binding.bottomNavBar.visibility = View.VISIBLE
                        setTheme(R.style.Theme_MyMovieAppKt)
                        WindowCompat.setDecorFitsSystemWindows(window, true)
                        isAppearanceLightStatusBars = true

                        when (Build.VERSION.SDK_INT) {
                            21, 22 -> Color.BLACK
                            else -> ContextCompat.getColor(
                                this@MainActivity,
                                R.color.day_night_inverse
                            )
                        }
                    }

                    else -> {
                        binding.bottomNavBar.visibility = View.GONE
                        val backgroundColor = bundle?.getInt(Constants.BACKGROUND_COLOR) ?: 0
                        val isDarkBackground = backgroundColor.isDarkColor()

                        when (destination.id) {
                            R.id.fullscreenImageFragment -> {
                                WindowCompat.setDecorFitsSystemWindows(window, true)
                                Color.BLACK
                            }

                            R.id.seeAllFragment -> {
                                WindowCompat.setDecorFitsSystemWindows(window, true)

                                if (backgroundColor != 0) {
                                    setTheme(if (isDarkBackground) R.style.DetailDarkTheme else R.style.DetailLightTheme)
                                    isAppearanceLightStatusBars = !isDarkBackground
                                    backgroundColor
                                } else {
                                    setTheme(R.style.Theme_MyMovieAppKt)
                                    isAppearanceLightStatusBars = true
                                    if (Build.VERSION.SDK_INT in 21..22) Color.BLACK
                                    else ContextCompat.getColor(
                                        this@MainActivity,
                                        R.color.day_night_inverse
                                    )
                                }
                            }

                            else -> {
                                window.statusBarColor = backgroundColor
                                WindowCompat.setDecorFitsSystemWindows(window, false)
                                setTheme(if (isDarkBackground) R.style.DetailDarkTheme else R.style.DetailLightTheme)
                                isAppearanceLightStatusBars = !isDarkBackground
                                Color.TRANSPARENT
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showAlertDialog() {
        MaterialAlertDialogBuilder(this)
            .setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_tmdb_24))
            .setTitle(getString(R.string.tmdb_attribution_title))
            .setMessage(getString(R.string.tmdb_attribution_message))
            .setPositiveButton(getString(R.string.tmdb_attribution_button_text)) { dialog, _ ->
                setIsFirstLaunch()
                dialog.dismiss()
            }
            .setCancelable(false)
            .create()
            .show()
    }

    private fun getIsFirstLaunch(): Boolean = runBlocking {
        dataStore.data.first()[PreferencesKey.is_first_launch] ?: true
    }

    private fun setIsFirstLaunch(): Unit = runBlocking {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.is_first_launch] = false
        }
    }

    private object PreferencesKey {
        val is_first_launch = booleanPreferencesKey(IS_FIRST_LAUNCH)
        val localeKey = stringPreferencesKey(LOCALE_KEY)

    }

    private fun changeTheme(theme: Int) {
        AppCompatDelegate.setDefaultNightMode(theme)
        delegate.applyDayNight()
    }

    private fun applyCurrentTheme() {
        changeTheme(
            when (preferencesHelper.darkMode) {
                DARK -> {
                    AppCompatDelegate.MODE_NIGHT_YES
                }

                LIGHT -> {
                    AppCompatDelegate.MODE_NIGHT_NO
                }

                else -> {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
            }
        )
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        oldPrefLocaleCode = Storage(newBase!!).getPreferredLocale()
        applyOverrideConfiguration(LocaleUtils.getLocalizedConfiguration(oldPrefLocaleCode))
    }

    override fun onResume() {
        super.onResume()
        val currentLocale = Storage(this).getPreferredLocale()
        if (oldPrefLocaleCode != currentLocale) {
            recreate()
            oldPrefLocaleCode = currentLocale
        }
    }

}