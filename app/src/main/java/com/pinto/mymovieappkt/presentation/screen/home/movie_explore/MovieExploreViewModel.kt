package com.pinto.mymovieappkt.presentation.screen.home.movie_explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinto.mymovieappkt.domain.model.Movie
import com.pinto.mymovieappkt.presentation.base.BaseViewModel
import com.pinto.mymovieappkt.utils.Constants
import com.pinto.mymovieappkt.utils.UiState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MovieExploreViewModel : BaseViewModel() {
    private val _trendingMovies = MutableStateFlow(emptyList<Movie>())
    val trendingMovies get() = _trendingMovies.asStateFlow()

    private val _popularMovies = MutableStateFlow(emptyList<Movie>())
    val popularMovies get() = _popularMovies.asStateFlow()

    private val _topRatedMovies = MutableStateFlow(emptyList<Movie>())
    val topRatedMovies get() = _topRatedMovies.asStateFlow()

    private val _nowPlayingMovies = MutableStateFlow(emptyList<Movie>())
    val nowPlayingMovies get() = _nowPlayingMovies.asStateFlow()

    private val _countryCode = MutableStateFlow("")
    val countyCode get() = _countryCode.asStateFlow()

    private val _countryName = MutableStateFlow("")
    val countryName get() = _countryName.asStateFlow()

    private var pagePopular = 1
    private var pageTopRated = 1
    private var pageNowPlaying = 1
    private var pageUpcoming = 1

    fun onLoadMore(type: Any?) {
        _uiState.value = UiState.loadingState(isInitial)

        when (type as String) {
            Constants.LIST_ID_POPULAR -> pagePopular++
            Constants.LIST_ID_TOP_RATED -> pageTopRated++
            Constants.LIST_ID_NOW_PLAYING -> pageNowPlaying++
            Constants.LIST_ID_UPCOMING -> pageUpcoming++
        }

//        viewModelScope.launch {
//            coroutineScope { fetchList(type) }
//            setUiState()
//        }
    }
}