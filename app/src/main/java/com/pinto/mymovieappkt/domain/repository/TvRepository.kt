package com.pinto.mymovieappkt.domain.repository

import com.pinto.mymovieappkt.domain.model.EpisodeDetail
import com.pinto.mymovieappkt.domain.model.FavoriteTv
import com.pinto.mymovieappkt.domain.model.SeasonDetail
import com.pinto.mymovieappkt.domain.model.TvDetail
import com.pinto.mymovieappkt.domain.model.TvList
import com.pinto.mymovieappkt.domain.model.VideoList
import com.pinto.mymovieappkt.utils.Resource

interface TvRepository {
    suspend fun getTvList(listId: String, page: Int): Resource<TvList>
    suspend fun getTrendingTvs(): Resource<TvList>
    suspend fun getTrendingTvTrailers(tvId: Int): Resource<VideoList>
    suspend fun getTvsByGenre(genreId: Int, page: Int): Resource<TvList>
    suspend fun getTvSearchResults(query: String, page: Int): Resource<TvList>
    suspend fun getTvDetails(tvId: Int): Resource<TvDetail>
    suspend fun getSeasonDetails(tvId: Int, seasonNumber: Int): Resource<SeasonDetail>
    suspend fun getEpisodeDetails(tvId: Int, seasonNumber: Int, episodeNumber: Int): Resource<EpisodeDetail>
    suspend fun getFavoriteTvs(): List<FavoriteTv>
    suspend fun tvExists(tvId: Int): Boolean
    suspend fun insertTv(tv: FavoriteTv)
    suspend fun deleteTv(tv: FavoriteTv)

}