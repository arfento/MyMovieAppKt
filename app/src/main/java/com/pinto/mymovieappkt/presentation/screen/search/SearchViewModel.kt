package com.pinto.mymovieappkt.presentation.screen.search

import com.pinto.mymovieappkt.domain.usecase.GetSearchResults
import com.pinto.mymovieappkt.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel

class SearchViewModel @Inject constructor(private val getSearchResults: GetSearchResults) : BaseViewModel() {

}