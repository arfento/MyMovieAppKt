package com.pinto.mymovieappkt.domain.usecase

import com.pinto.mymovieappkt.domain.repository.MovieRepository
import com.pinto.mymovieappkt.domain.repository.PersonRepository
import com.pinto.mymovieappkt.domain.repository.TvRepository
import com.pinto.mymovieappkt.utils.Detail
import com.pinto.mymovieappkt.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetDetails @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository,
    private val personRepository: PersonRepository,
) {
    operator fun invoke(
        detailType : Detail,
        id : Int,
        seasonNumber : Int? = null,
        episodeNumber : Int? = null,
    ): Flow<Resource<Any>> = flow{
        emit(
            when(detailType){
                Detail.MOVIE -> movieRepository.getMovieDetails(id)
                Detail.TV -> tvRepository.getTvDetails(id)
                Detail.TV_SEASON -> tvRepository.getSeasonDetails(id, seasonNumber!!)
                Detail.TV_EPISODE -> tvRepository.getEpisodeDetails(id, seasonNumber!!, episodeNumber!!)
                Detail.PERSON -> personRepository.getPersonDetails(id)
            }
        )

    }
}