package com.pinto.mymovieappkt.domain.usecase

import com.pinto.mymovieappkt.domain.repository.MovieRepository
import com.pinto.mymovieappkt.domain.repository.TvRepository
import com.pinto.mymovieappkt.utils.Constants
import com.pinto.mymovieappkt.utils.Detail
import javax.inject.Inject

class CheckFavorites @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository
) {
    suspend operator fun invoke(
        detailType: Detail,
        id: Int
    ): Boolean = when (detailType) {
        Detail.MOVIE -> movieRepository.movieExists(id)
        Detail.TV -> tvRepository.tvExists(id)
        else -> throw IllegalArgumentException(Constants.ILLEGAL_ARGUMENT_DETAIL_TYPE)
    }
}