package com.pinto.mymovieappkt.presentation.screen.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.transition.TransitionManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.pinto.mymovieappkt.R
import com.pinto.mymovieappkt.databinding.FragmentSettingsBinding
import com.pinto.mymovieappkt.utils.Constants.DARK
import com.pinto.mymovieappkt.utils.Constants.LANG_PREF
import com.pinto.mymovieappkt.utils.Constants.LIGHT
import com.pinto.mymovieappkt.utils.SharedPreferencesHelper
import com.pinto.mymovieappkt.utils.recreateTask
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    @Inject
    lateinit var gson: Gson
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val locales = arrayOf("Indonesian", "English")

    @Inject
    lateinit var preferencesHelper: SharedPreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.themeCardView.setOnClickListener {
            if (binding.themeRadioGroup.isVisible) {
                collapseCard()
            } else {
                expandCard()
            }
        }

        val currentLang = preferencesHelper.read(LANG_PREF, "1")?.toInt()!!
        binding.languageTextView.text = locales[currentLang]

        setThemeChoice(preferencesHelper.darkMode)
        binding.themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.lightRadioButton.id -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    preferencesHelper.darkMode = LIGHT
                }

                binding.darkRadioButton.id -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    preferencesHelper.darkMode = DARK
                }
            }
            requireActivity().recreate()
        }

        binding.languageCardView.setOnClickListener {
            openLanguageSelectorDialog(currentLang)
        }
        return binding.root
    }

    private fun openLanguageSelectorDialog(currentLang: Int) {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle(getString(R.string.language))

        builder.setSingleChoiceItems(locales, currentLang) { _, which ->
            when (which) {
                0 -> {
                    val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("in")
                    AppCompatDelegate.setApplicationLocales(appLocale)
                    preferencesHelper.save(LANG_PREF, 0)
                    requireActivity().recreateTask()
                }

                1 -> {
                    val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("en")
                    AppCompatDelegate.setApplicationLocales(appLocale)
                    preferencesHelper.save(LANG_PREF, 1)
                    requireActivity().recreateTask()
                }
            }
        }
        builder.create().apply {
            show()
        }


    }

    private fun setThemeChoice(darkMode: Int) {
        when (darkMode) {
            LIGHT -> {
                binding.lightRadioButton.isChecked = true
            }

            DARK -> {
                binding.darkRadioButton.isChecked = true

            }
        }
    }

    private fun expandCard() {
        TransitionManager.beginDelayedTransition(binding.root as ViewGroup)
        binding.themeTextView.setCompoundDrawablesWithIntrinsicBounds(
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_theme), null,
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_keyboard_arrow_down_24
            ), null
        )
        binding.themeRadioGroup.visibility = View.VISIBLE
    }

    private fun collapseCard() {

        TransitionManager.beginDelayedTransition(binding.root as ViewGroup)
        binding.themeTextView.setCompoundDrawablesWithIntrinsicBounds(
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_theme), null,
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_keyboard_arrow_up_24
            ), null
        )
        binding.themeRadioGroup.visibility = View.GONE
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}