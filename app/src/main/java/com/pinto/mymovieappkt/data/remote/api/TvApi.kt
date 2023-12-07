package com.pinto.mymovieappkt.data.remote.api

import com.pinto.mymovieappkt.data.remote.dto.EpisodeDetailDTO
import com.pinto.mymovieappkt.data.remote.dto.SeasonDetailDTO
import com.pinto.mymovieappkt.data.remote.dto.TvDetailDTO
import com.pinto.mymovieappkt.data.remote.dto.TvListDTO
import com.pinto.mymovieappkt.data.remote.dto.VideoListDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvApi {

    @GET("tv/{list_id}")
    suspend fun getTvList(
        @Path("list_id") listId: String,
        @Query("page") page: Int,
        @Query("language") language: String? = "en-US"
    ): TvListDTO

    @GET("trending/tv/week")
    suspend fun getTrendingTvs(
        @Query("language") language: String? = "en-US"
    ): TvListDTO

    @GET("tv/{tv_id}/videos")
    suspend fun getTrendingTvTrailers(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String? = "en-US"
    ): VideoListDTO

    @GET("discover/tv")
    suspend fun getTvsByGenre(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int,
        @Query("language") language: String? = "en-US"
    ): TvListDTO

    @GET("search/tv")
    suspend fun getTvSearchResults(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("language") language: String? = "en-US"
    ): TvListDTO

    @GET("tv/{tv_id}?&append_to_response=credits,videos,images,recommendations,external_ids")
    suspend fun getTvDetails(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String? = "en-US"
    ): TvDetailDTO

    @GET("tv/{tv_id}/season/{season_number}?&append_to_response=credits,videos,images")
    suspend fun getSeasonDetails(
        @Path("tv_id") tvId: Int,
        @Path("season_number") seasonNumber: Int,
        @Query("language") language: String? = "en-US"
    ): SeasonDetailDTO

    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}?&append_to_response=credits,videos,images")
    suspend fun getEpisodeDetails(
        @Path("tv_id") tvId: Int,
        @Path("season_number") seasonNumber: Int,
        @Path("episode_number") episodeNumber: Int,
        @Query("language") language: String? = "en-US"
    ): EpisodeDetailDTO
}