package com.pinto.mymovieappkt.domain.usecase

import com.pinto.mymovieappkt.domain.repository.MovieRepository
import com.pinto.mymovieappkt.domain.repository.TvRepository
import com.pinto.mymovieappkt.utils.Constants
import com.pinto.mymovieappkt.utils.Detail
import com.pinto.mymovieappkt.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetByGenre @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository,
) {
    operator fun invoke(
        detailType: Detail,
        genreId: Int,
        page: Int,
    ): Flow<Resource<Any>> = flow {
        emit(
            when (detailType) {
                Detail.MOVIE -> movieRepository.getMoviesByGenre(genreId, page)
                Detail.TV -> tvRepository.getTvsByGenre(genreId, page)
                else -> throw IllegalArgumentException(Constants.ILLEGAL_ARGUMENT_DETAIL_TYPE)
            }
        )

    }
}