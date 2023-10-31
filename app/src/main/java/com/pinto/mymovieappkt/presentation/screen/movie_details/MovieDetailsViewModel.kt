package com.pinto.mymovieappkt.presentation.screen.movie_details

import androidx.lifecycle.viewModelScope
import com.pinto.mymovieappkt.domain.model.FavoriteMovie
import com.pinto.mymovieappkt.domain.model.MovieDetail
import com.pinto.mymovieappkt.domain.usecase.AddFavorite
import com.pinto.mymovieappkt.domain.usecase.CheckFavorites
import com.pinto.mymovieappkt.domain.usecase.DeleteFavorite
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
class MovieDetailsViewModel @Inject constructor(
    private val getDetails: GetDetails,
    private val checkFavorites: CheckFavorites,
    private val deleteFavorite: DeleteFavorite,
    private val addFavorite: AddFavorite,
) : BaseViewModel() {

    private val _details = MutableStateFlow(MovieDetail.empty)
    val details get() = _details.asStateFlow()

    private val _isInFavorites = MutableStateFlow(false)
    val isInFavorites get() = _isInFavorites.asStateFlow()

    private lateinit var favoriteMovie: FavoriteMovie

    fun initRequests(movieId: Int) {
        detailId = movieId
        checkMovieFavorites()
        fetchMovieDetails()
    }

    private fun checkMovieFavorites() {
        viewModelScope.launch {
            _isInFavorites.value = checkFavorites(Detail.MOVIE, detailId)
        }
    }

    fun updateFavorites() {
        viewModelScope.launch {
            if (_isInFavorites.value) {
                deleteFavorite(detailType = Detail.MOVIE, movie = favoriteMovie)
                _isInFavorites.value = false
            } else {
                addFavorite(detailType = Detail.MOVIE, movie = favoriteMovie)
                _isInFavorites.value = true
            }
        }
    }

    private fun fetchMovieDetails() {
        viewModelScope.launch {
            getDetails(detailType = Detail.MOVIE, id = detailId).collect() { response ->
                when (response) {
                    is Resource.Success -> {
                        (response.data as MovieDetail).apply {
                            _details.value = this
                            favoriteMovie = FavoriteMovie(
                                id = id,
                                posterPath = posterPath,
                                releaseDate = releaseDate,
                                runtime = runtime,
                                title = title,
                                voteAverage = voteAverage,
                                voteCount = voteCount,
                                date = System.currentTimeMillis()
                            )
                        }
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