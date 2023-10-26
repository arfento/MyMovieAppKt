package com.pinto.mymovieappkt.presentation.screen.favorites.favorite_movies

import androidx.lifecycle.viewModelScope
import com.pinto.mymovieappkt.domain.model.FavoriteMovie
import com.pinto.mymovieappkt.domain.usecase.AddFavorite
import com.pinto.mymovieappkt.domain.usecase.DeleteFavorite
import com.pinto.mymovieappkt.domain.usecase.GetFavorites
import com.pinto.mymovieappkt.presentation.base.BaseViewModel
import com.pinto.mymovieappkt.utils.Detail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(
    private val getFavorites: GetFavorites,
    private val deleteFavorite: DeleteFavorite,
    private val addFavorite: AddFavorite,
) : BaseViewModel() {

    private val _favoriteMovies = MutableStateFlow(emptyList<FavoriteMovie>())
    val favoriteMovies get() = _favoriteMovies.asStateFlow()

    init {
        fetchFavoriteMovies()
    }

    fun fetchFavoriteMovies() {
        viewModelScope.launch {
            getFavorites(Detail.MOVIE).collect {
                _favoriteMovies.value = it as List<FavoriteMovie>
            }
        }
    }

    fun removeMovieFromFavorites(movie: FavoriteMovie) {
        viewModelScope.launch {
            deleteFavorite(detailType = Detail.MOVIE, movie = movie)
            fetchFavoriteMovies()
        }
    }

    fun addMovieToFavorites(movie: FavoriteMovie) {
        viewModelScope.launch {
            addFavorite(detailType = Detail.MOVIE, movie = movie)
            fetchFavoriteMovies()
        }
    }


}