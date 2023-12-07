package com.pinto.mymovieappkt.data.remote.api

import com.pinto.mymovieappkt.data.remote.dto.PersonDetailDTO
import com.pinto.mymovieappkt.data.remote.dto.PersonListDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonApi {

    @GET("search/person")
    suspend fun getPersonSearchResults(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("language") language: String? = "en-US"
    ): PersonListDTO

    @GET("person/{person_id}?&append_to_response=images,movie_credits,tv_credits,external_ids")
    suspend fun getPersonDetails(
        @Path("person_id") personId: Int,
        @Query("language") language: String? = "en-US"
    ): PersonDetailDTO
}