package com.pinto.mymovieappkt.presentation.screen.home.tv_explore

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.pinto.mymovieappkt.domain.model.Tv
import com.pinto.mymovieappkt.domain.model.TvList
import com.pinto.mymovieappkt.domain.usecase.GetList
import com.pinto.mymovieappkt.domain.usecase.GetTrendingVideos
import com.pinto.mymovieappkt.presentation.base.BaseViewModel
import com.pinto.mymovieappkt.utils.Constants
import com.pinto.mymovieappkt.utils.Detail
import com.pinto.mymovieappkt.utils.Resource
import com.pinto.mymovieappkt.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class TvExploreViewModel @Inject constructor(
    private val getList: GetList,
    private val getTrendingVideos: GetTrendingVideos,
) : BaseViewModel() {

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

    init {
        initRequests()
    }

    fun initRequests() {
        viewModelScope.launch {
            coroutineScope {
                fetchLists()
                fetchLists(Constants.LIST_ID_POPULAR)
                fetchLists(Constants.LIST_ID_TOP_RATED)
                fetchLists(Constants.LIST_ID_AIRING_DAY)
            }
            setUiState()
        }
    }

    private suspend fun fetchLists(listId: String? = null) {
        val page = when (listId) {
            Constants.LIST_ID_POPULAR -> pagePopular
            Constants.LIST_ID_TOP_RATED -> pageTopRated
            Constants.LIST_ID_AIRING_DAY, Constants.LIST_ID_AIRING_WEEK -> pageAiring
            else -> null
        }

        getList(
            detailType = Detail.TV,
            listId = listId,
            page = page,
        ).collect { response ->
            when (response) {
                is Resource.Success -> {
                    val tvList = (response.data as TvList).results
                    when (listId) {
                        Constants.LIST_ID_POPULAR -> _popularTvShows.value += tvList
                        Constants.LIST_ID_TOP_RATED -> _topRatedTvShows.value += tvList
                        Constants.LIST_ID_AIRING_DAY, Constants.LIST_ID_AIRING_WEEK -> _airingTvShows.value += tvList
                        else -> _trendingTvShows.value = tvList
                    }
                    areResponsesSuccessful.add(true)
                    isInitial = false
                }

                is Resource.Error -> {
                    errorText = response.message
                    areResponsesSuccessful.add(false)
                }
            }
        }
    }

    fun onLoadMore(type: Any?) {
        _uiState.value = UiState.loadingState(isInitial)

        when (type as String) {
            Constants.LIST_ID_POPULAR -> pagePopular++
            Constants.LIST_ID_TOP_RATED -> pageTopRated++
            Constants.LIST_ID_AIRING_DAY, Constants.LIST_ID_AIRING_WEEK -> pageAiring++
        }

        viewModelScope.launch {
            coroutineScope { fetchLists(type) }
            setUiState()
        }
    }

    fun getTrendingTvTrailerKey(tvId: Int): String = runBlocking {
        var videoKey = ""

        coroutineScope {
            getTrendingVideos(Detail.TV, tvId).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        Log.d("url adapter tvid", "message adapter movie: getTrendingTvTrailerKey $tvId , ${response.data.results}")
                        if (response.data.results.isNotEmpty()) {
                            videoKey = response.data.filterVideos(true).first().key
                            _uiState.value = UiState.successState()
                        } else {
                            _uiState.value = UiState.errorState(false, "Video is Empty")
                        }
                    }

                    is Resource.Error -> {
                        _uiState.value = UiState.errorState(false, response.message)
                    }
                }
            }
        }
        videoKey
    }

    fun switchAiringTime(airingTime: String) {
        _uiState.value = UiState.loadingState(isInitial)
        _airingTvShows.value = emptyList()
        _airingTime.value = airingTime

        pageAiring = 1

        viewModelScope.launch {
            coroutineScope { fetchLists(airingTime) }
            setUiState()
        }
    }
}