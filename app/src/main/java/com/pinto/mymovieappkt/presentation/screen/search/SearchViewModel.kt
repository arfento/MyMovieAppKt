package com.pinto.mymovieappkt.presentation.screen.search

import androidx.lifecycle.viewModelScope
import com.pinto.mymovieappkt.domain.model.Movie
import com.pinto.mymovieappkt.domain.model.MovieList
import com.pinto.mymovieappkt.domain.model.Person
import com.pinto.mymovieappkt.domain.model.PersonList
import com.pinto.mymovieappkt.domain.model.Tv
import com.pinto.mymovieappkt.domain.model.TvList
import com.pinto.mymovieappkt.domain.usecase.GetSearchResults
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
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val getSearchResults: GetSearchResults) :
    BaseViewModel() {

    private val _isSearchInitialized = MutableStateFlow(false)
    val isSearchInitialized get() = _isSearchInitialized.asStateFlow()

    private val _query = MutableStateFlow("")
    val query get() = _query.asStateFlow()

    private val _movieResults = MutableStateFlow(emptyList<Movie>())
    val movieResults get() = _movieResults.asStateFlow()

    private val _movieTotalResults = MutableStateFlow(0)
    val movieTotalResults get() = _movieTotalResults.asStateFlow()

    private val _tvResults = MutableStateFlow(emptyList<Tv>())
    val tvResults get() = _tvResults.asStateFlow()

    private val _tvTotalResults = MutableStateFlow(0)
    val tvTotalResults get() = _tvTotalResults.asStateFlow()

    private val _personResults = MutableStateFlow(emptyList<Person>())
    val personResults get() = _personResults.asStateFlow()

    private val _personTotalResults = MutableStateFlow(0)
    val personTotalResults get() = _personTotalResults.asStateFlow()

    private var pageMovie = 1
    private var pageTv = 1
    private var pagePerson = 1

    private var isQueryChanged = false

    fun initRequests() {
        viewModelScope.launch {
            coroutineScope {
                launch {
                    fetchSearchResults(Detail.MOVIE)
                    fetchSearchResults(Detail.TV)
                    fetchSearchResults(Detail.PERSON)
                }
            }
            setUiState()
        }
    }


    private suspend fun fetchSearchResults(detailType: Detail) {
        val page = when (detailType) {
            Detail.MOVIE -> pageMovie
            Detail.TV -> pageTv
            Detail.PERSON -> pagePerson
            else -> throw IllegalArgumentException(Constants.ILLEGAL_ARGUMENT_DETAIL_TYPE)
        }

        getSearchResults(
            detailType = detailType,
            page = page,
            query = query.value
        ).collect { response ->
            when (response) {
                is Resource.Success -> {
                    when (detailType) {
                        Detail.MOVIE -> with(response.data as MovieList) {
                            _movieResults.value =
                                if (isQueryChanged) results else _movieResults.value + results
                            _movieTotalResults.value = totalResults
                        }

                        Detail.TV -> with(response.data as TvList) {
                            _tvResults.value =
                                if (isQueryChanged) results else _tvResults.value + results
                            _tvTotalResults.value = totalResults
                        }

                        Detail.MOVIE -> with(response.data as PersonList) {
                            _personResults.value =
                                if (isQueryChanged) results else _personResults.value + results
                            _personTotalResults.value = totalResults
                        }

                        else -> throw IllegalArgumentException(Constants.ILLEGAL_ARGUMENT_DETAIL_TYPE)
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
        isQueryChanged = false

        when (type as Detail) {
            Detail.MOVIE -> pageMovie++
            Detail.TV -> pageTv++
            Detail.PERSON -> pagePerson++
            else -> throw IllegalArgumentException(Constants.ILLEGAL_ARGUMENT_DETAIL_TYPE)
        }

        viewModelScope.launch {
            coroutineScope { fetchSearchResults(type) }
            setUiState()
        }
    }

    fun fetchInitialSearch(query: String) {
        _uiState.value = UiState.loadingState(isInitial)
        _isSearchInitialized.value = true
        _query.value = query

        isQueryChanged = true
        isInitial = true

        pagePerson = 1
        pageTv = 1
        pageMovie = 1

        initRequests()
    }

    fun clearSearchResults() {
        _isSearchInitialized.value = false
        _query.value = ""

        _movieResults.value = emptyList()
        _tvResults.value = emptyList()
        _personResults.value = emptyList()
    }
}