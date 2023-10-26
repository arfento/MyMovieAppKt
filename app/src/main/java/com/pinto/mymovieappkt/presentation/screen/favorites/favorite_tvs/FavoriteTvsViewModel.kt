package com.pinto.mymovieappkt.presentation.screen.favorites.favorite_tvs

import androidx.lifecycle.viewModelScope
import com.pinto.mymovieappkt.domain.model.FavoriteTv
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

class FavoriteTvsViewModel @Inject constructor(
    private val getFavorites: GetFavorites,
    private val deleteFavorite: DeleteFavorite,
    private val addFavorite: AddFavorite,
) : BaseViewModel() {

    private val _favoriteTvs = MutableStateFlow(emptyList<FavoriteTv>())
    val favoriteTvs get() = _favoriteTvs.asStateFlow()

    init {
        fetchFavoriteTvs()
    }

    fun fetchFavoriteTvs() {
        viewModelScope.launch {
            getFavorites(Detail.TV).collect {
                _favoriteTvs.value = it as List<FavoriteTv>
            }
        }
    }

    fun removeTvFromFavorites(tv: FavoriteTv) {
        viewModelScope.launch {
            deleteFavorite(detailType = Detail.TV, tv = tv)
            fetchFavoriteTvs()
        }
    }

    fun addTvToFavorites(tv: FavoriteTv) {
        viewModelScope.launch {
            addFavorite(detailType = Detail.TV, tv = tv)
            fetchFavoriteTvs()
        }
    }


}