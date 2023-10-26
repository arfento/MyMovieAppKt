package com.pinto.mymovieappkt.domain.usecase

import com.pinto.mymovieappkt.domain.model.VideoList
import com.pinto.mymovieappkt.domain.repository.MovieRepository
import com.pinto.mymovieappkt.domain.repository.TvRepository
import com.pinto.mymovieappkt.utils.Constants
import com.pinto.mymovieappkt.utils.Detail
import com.pinto.mymovieappkt.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTrendingVideos @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository
) {
    operator fun invoke(
        detailType: Detail,
        id: Int
    ): Flow<Resource<VideoList>> = flow {
        emit(
            when (detailType) {
                Detail.MOVIE -> movieRepository.getTrendingMovieTrailers(id)
                Detail.TV -> tvRepository.getTrendingTvTrailers(id)
                else -> throw IllegalArgumentException(Constants.ILLEGAL_ARGUMENT_DETAIL_TYPE)
            }
        )
    }
}