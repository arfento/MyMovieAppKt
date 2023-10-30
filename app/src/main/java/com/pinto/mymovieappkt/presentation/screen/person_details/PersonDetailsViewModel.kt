package com.pinto.mymovieappkt.presentation.screen.person_details

import androidx.lifecycle.viewModelScope
import com.pinto.mymovieappkt.domain.model.Movie
import com.pinto.mymovieappkt.domain.model.PersonDetail
import com.pinto.mymovieappkt.domain.model.Tv
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
class PersonDetailsViewModel @Inject constructor(
    private val getDetails: GetDetails,
) : BaseViewModel() {

    private val _details = MutableStateFlow(PersonDetail.empty)
    val details get() = _details.asStateFlow()

    private val _movieSeeAllList = MutableStateFlow(emptyList<Movie>())
    val movieSeeAllList get() = _movieSeeAllList.asStateFlow()

    private val _tvSeeAllList = MutableStateFlow(emptyList<Tv>())
    val tvSeeAllList get() = _tvSeeAllList.asStateFlow()

    private val _movieSeeAllTitle = MutableStateFlow("")
    val movieSeeAllTitle get() = _movieSeeAllTitle.asStateFlow()

    private val _tvSeeAllTitle = MutableStateFlow("")
    val tvSeeAllTitle get() = _tvSeeAllTitle.asStateFlow()

    fun initRequest(personId: Int) {
        detailId = personId
        fetchPersonDetails()
    }

    private fun fetchPersonDetails() {
        viewModelScope.launch {
            getDetails(Detail.PERSON, detailId).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _details.value = response.data as PersonDetail
                        _uiState.value = UiState.successState()
                    }

                    is Resource.Error -> {
                        _uiState.value = UiState.errorState(errorText = response.message)
                    }
                }
            }
        }
    }

    fun setMovieSeeAllList(creditType: PersonDetailsFragment.CreditsType, title: String) {
        val movieList = _details.value.movieCredits
        when (creditType) {
            PersonDetailsFragment.CreditsType.CAST -> {
                _movieSeeAllList.value = movieList.cast
                _movieSeeAllTitle.value = title + "(${movieList.cast.size})"
            }

            PersonDetailsFragment.CreditsType.CREW -> {
                _movieSeeAllList.value = movieList.crew
                _movieSeeAllTitle.value = title + "(${movieList.crew.size})"
            }
        }
    }

    fun setTvSeeAllList(creditType: PersonDetailsFragment.CreditsType, title: String) {
        val tvList = _details.value.tvCredits
        when (creditType) {
            PersonDetailsFragment.CreditsType.CAST -> {
                _tvSeeAllList.value = tvList.cast
                _tvSeeAllTitle.value = title + "(${tvList.cast.size})"
            }

            PersonDetailsFragment.CreditsType.CREW -> {
                _tvSeeAllList.value = tvList.crew
                _tvSeeAllTitle.value = title + "(${tvList.crew.size})"
            }
        }
    }
}