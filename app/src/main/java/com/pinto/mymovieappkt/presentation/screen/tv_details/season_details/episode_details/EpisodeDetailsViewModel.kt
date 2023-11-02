package com.pinto.mymovieappkt.presentation.screen.tv_details.season_details.episode_details

import androidx.lifecycle.viewModelScope
import com.pinto.mymovieappkt.domain.model.EpisodeDetail
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
class EpisodeDetailsViewModel @Inject constructor(
    private val getDetails: GetDetails,
) : BaseViewModel() {


    private val _details = MutableStateFlow(EpisodeDetail.empty)
    val details get() = _details.asStateFlow()

    fun initRequest(tvId: Int, seasonNumber: Int, episodeNumber: Int) {
        detailId = tvId
        fetchEpisodeDetails(seasonNumber, episodeNumber)
    }

    private fun fetchEpisodeDetails(seasonNumber: Int, episodeNumber: Int) {
        viewModelScope.launch {
            getDetails(
                detailType = Detail.TV_EPISODE,
                id = detailId,
                seasonNumber = seasonNumber,
                episodeNumber = episodeNumber
            )
                .collect() { response ->
                    when (response) {
                        is Resource.Success -> {
                            _details.value = response.data as EpisodeDetail
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