package com.pinto.mymovieappkt.presentation.screen.tv_details.season_details

import androidx.lifecycle.viewModelScope
import com.pinto.mymovieappkt.domain.model.SeasonDetail
import com.pinto.mymovieappkt.domain.usecase.GetDetails
import com.pinto.mymovieappkt.presentation.base.BaseViewModel
import com.pinto.mymovieappkt.utils.Detail
import com.pinto.mymovieappkt.utils.Resource
import com.pinto.mymovieappkt.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeasonDetailsViewModel @Inject constructor(
    private val getDetails: GetDetails,
) : BaseViewModel() {


    private val _details = MutableStateFlow(SeasonDetail.empty)
    val details get() = _details.asStateFlow()

    fun initRequest(tvId: Int, seasonNumber: Int) {
        detailId = tvId
        fetchSeasonDetails(seasonNumber)
    }

    private fun fetchSeasonDetails(seasonNumber: Int) {
        viewModelScope.launch {
            getDetails(detailType = Detail.TV_SEASON, id = detailId, seasonNumber = seasonNumber)
                .collect() { response ->
                    when (response) {
                        is Resource.Success -> {
                            _details.value = response.data as SeasonDetail
                            _uiState.value = UiState.successState()
                        }

                        is Resource.Error -> {
                            _uiState.value = UiState.errorState(errorText = response.message)
                        }
                    }
                }
        }
    }


}