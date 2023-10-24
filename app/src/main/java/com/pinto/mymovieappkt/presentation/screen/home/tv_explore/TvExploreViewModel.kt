package com.pinto.mymovieappkt.presentation.screen.home.tv_explore

import androidx.lifecycle.ViewModel
import com.pinto.mymovieappkt.domain.model.Tv
import com.pinto.mymovieappkt.presentation.base.BaseViewModel
import com.pinto.mymovieappkt.utils.Constants
import com.pinto.mymovieappkt.utils.UiState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TvExploreViewModel : BaseViewModel() {
    private val _trendingTvShows = MutableStateFlow(emptyList<Tv>())
    val trendingTvShows get() = _trendingTvShows.asStateFlow()

    private val _popularTvShows = MutableStateFlow(emptyList<Tv>())
    val popularTvShows get() = _popularTvShows.asStateFlow()

    private val _topRatedTvShows = MutableStateFlow(emptyList<Tv>())
    val topRatedTvShows get() = _topRatedTvShows.asStateFlow()

    private val _airingTvShows = MutableStateFlow(emptyList<Tv>())
    val airingTvShows get() = _airingTvShows.asStateFlow()

    private val _airingTime = MutableStateFlow(Constants.LIST_ID_AIRING_DAY)
    val airingTime get() = _airingTime.asStateFlow()

    private var pagePopular = 1
    private var pageTopRated = 1
    private var pageAiring = 1

    fun onLoadMore(type: Any?) {
        _uiState.value = UiState.loadingState(isInitial)

        when (type as String) {
            Constants.LIST_ID_POPULAR -> pagePopular++
            Constants.LIST_ID_TOP_RATED -> pageTopRated++
            Constants.LIST_ID_AIRING_DAY, Constants.LIST_ID_AIRING_WEEK -> pageAiring++
        }

//        viewModelScope.launch {
//            coroutineScope { fetchLists(type) }
//            setUiState()
//        }
    }
}