package com.pinto.mymovieappkt.domain.usecase

import com.pinto.mymovieappkt.domain.repository.MovieRepository
import com.pinto.mymovieappkt.domain.repository.TvRepository
import com.pinto.mymovieappkt.utils.Constants
import com.pinto.mymovieappkt.utils.Detail
import com.pinto.mymovieappkt.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetList @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository
) {
    operator fun invoke(
        detailType: Detail,
        listId: String?,
        page: Int? = null,
        region: String? = null
    ): Flow<Resource<Any>> = flow {
        emit(
            when (detailType) {
                Detail.MOVIE -> {
                    if (listId == null) movieRepository.getTrendingMovies()
                    else movieRepository.getMovieList(listId, page!!, region)
                }

                Detail.TV -> {
                    if (listId == null) tvRepository.getTrendingTvs()
                    else tvRepository.getTvList(listId, page!!)
                }

                else -> throw IllegalArgumentException(Constants.ILLEGAL_ARGUMENT_DETAIL_TYPE)
            }
        )
    }
}