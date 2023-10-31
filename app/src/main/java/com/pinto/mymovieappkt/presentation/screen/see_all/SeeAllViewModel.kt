package com.pinto.mymovieappkt.presentation.screen.see_all

import android.os.Parcelable
import androidx.lifecycle.viewModelScope
import com.pinto.mymovieappkt.domain.model.MovieList
import com.pinto.mymovieappkt.domain.model.PersonList
import com.pinto.mymovieappkt.domain.model.TvList
import com.pinto.mymovieappkt.domain.usecase.GetByGenre
import com.pinto.mymovieappkt.domain.usecase.GetList
import com.pinto.mymovieappkt.domain.usecase.GetSearchResults
import com.pinto.mymovieappkt.presentation.base.BaseViewModel
import com.pinto.mymovieappkt.utils.Constants
import com.pinto.mymovieappkt.utils.Content
import com.pinto.mymovieappkt.utils.Detail
import com.pinto.mymovieappkt.utils.Resource
import com.pinto.mymovieappkt.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeAllViewModel @Inject constructor(
    private val getList: GetList,
    private val getSearchResults: GetSearchResults,
    private val getByGenre: GetByGenre,
) : BaseViewModel() {

    private val _results = MutableStateFlow(setOf<Any>())
    val results get() = _results.asStateFlow()

    private var contentType: Parcelable? = null
    private var detailType: Parcelable? = null
    private var genreId: Int? = null
    private var listId: String? = null
    private var region: String? = null

    private var page = 1

    fun initRequest(
        contentType: Parcelable?,
        detailType: Parcelable?,
        genreId: Int?,
        listId: String?,
        region: String?,
    ) {
        this.contentType = contentType
        this.detailType = detailType
        this.genreId = genreId
        this.listId = listId
        this.region = region

        when (contentType) {
            Content.EXPLORE_LIST -> fetchList()
            Content.GENRE -> fetchGenreResults()
            Content.SEARCH -> fetchSearchResults()
            else -> _uiState.value = UiState.successState()
        }
    }

    fun onLoadMore(type: Any?) {
        _uiState.value = UiState.loadingState()
        page++
        when (contentType) {
            Content.EXPLORE_LIST -> fetchList()
            Content.SEARCH -> fetchSearchResults()
            Content.GENRE -> fetchGenreResults()
            else -> throw IllegalArgumentException(Constants.ILLEGAL_ARGUMENT_DETAIL_TYPE)
        }

    }

    private fun fetchList() {
        viewModelScope.launch {
            getList(
                detailType = detailType as Detail,
                listId = listId,
                page = page,
                region = region
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _results.value += when (detailType) {
                            Detail.MOVIE -> (response.data as MovieList).results
                            Detail.TV -> (response.data as TvList).results
                            else -> throw IllegalArgumentException(Constants.ILLEGAL_ARGUMENT_DETAIL_TYPE)
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

    private fun fetchSearchResults() {
        viewModelScope.launch {
            getSearchResults(
                detailType = detailType as Detail,
                query = listId!!,
                page = page,
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _results.value += when (detailType) {
                            Detail.MOVIE -> (response.data as MovieList).results
                            Detail.TV -> (response.data as TvList).results
                            else -> (response.data as PersonList).results
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

    private fun fetchGenreResults() {
        viewModelScope.launch {
            getByGenre(
                detailType = detailType as Detail,
                genreId = genreId!!,
                page = page
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _results.value += when (detailType) {
                            Detail.MOVIE -> (response.data as MovieList).results
                            Detail.TV -> (response.data as TvList).results
                            else -> throw IllegalArgumentException(Constants.ILLEGAL_ARGUMENT_DETAIL_TYPE)
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