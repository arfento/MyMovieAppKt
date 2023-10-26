package com.pinto.mymovieappkt.domain.usecase

import com.pinto.mymovieappkt.domain.model.FavoriteMovie
import com.pinto.mymovieappkt.domain.model.FavoriteTv
import com.pinto.mymovieappkt.domain.repository.MovieRepository
import com.pinto.mymovieappkt.domain.repository.TvRepository
import com.pinto.mymovieappkt.utils.Constants
import com.pinto.mymovieappkt.utils.Detail
import javax.inject.Inject

class AddFavorite @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository
) {
    suspend operator fun invoke(
        detailType: Detail,
        movie: FavoriteMovie? = null,
        tv: FavoriteTv? = null
    ) {
        when (detailType) {
            Detail.MOVIE -> movieRepository.insertMovie(movie!!)
            Detail.TV -> tvRepository.insertTv(tv!!)
            else -> throw IllegalArgumentException(Constants.ILLEGAL_ARGUMENT_DETAIL_TYPE)
        }
    }
}