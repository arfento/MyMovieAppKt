package com.pinto.mymovieappkt.presentation.screen.setting

import androidx.lifecycle.viewModelScope
import com.pinto.mymovieappkt.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() :
    BaseViewModel() {

    fun initRequests() {
        viewModelScope.launch {
            setUiState()
        }

    }

}