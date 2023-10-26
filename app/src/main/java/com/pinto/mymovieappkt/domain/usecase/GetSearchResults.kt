package com.pinto.mymovieappkt.domain.usecase

import com.pinto.mymovieappkt.domain.repository.MovieRepository
import com.pinto.mymovieappkt.domain.repository.PersonRepository
import com.pinto.mymovieappkt.domain.repository.TvRepository
import com.pinto.mymovieappkt.utils.Constants
import com.pinto.mymovieappkt.utils.Detail
import com.pinto.mymovieappkt.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSearchResults @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository,
    private val personRepository: PersonRepository
) {
    operator fun invoke(
        detailType: Detail,
        query: String,
        page: Int
    ): Flow<Resource<Any>> = flow {
        emit(
            when (detailType) {
                Detail.MOVIE -> movieRepository.getMovieSearchResults(query, page)
                Detail.TV -> tvRepository.getTvSearchResults(query, page)
                Detail.PERSON -> personRepository.getPersonSearchResults(query, page)
                else -> throw IllegalArgumentException(Constants.ILLEGAL_ARGUMENT_DETAIL_TYPE)
            }
        )
    }
}