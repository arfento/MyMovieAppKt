package com.pinto.mymovieappkt.data.repository

import com.pinto.mymovieappkt.data.mapper.toPersonDetail
import com.pinto.mymovieappkt.data.mapper.toPersonList
import com.pinto.mymovieappkt.data.remote.api.PersonApi
import com.pinto.mymovieappkt.domain.model.PersonDetail
import com.pinto.mymovieappkt.domain.model.PersonList
import com.pinto.mymovieappkt.domain.repository.PersonRepository
import com.pinto.mymovieappkt.utils.Resource
import com.pinto.mymovieappkt.utils.SafeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonRepositoryImpl @Inject constructor(
    private val api: PersonApi,
    private val safeApiCall: SafeApiCall
) : PersonRepository {
    override suspend fun getPersonSearchResults(query: String, page: Int): Resource<PersonList> = safeApiCall.execute {
        api.getPersonSearchResults(query, page).toPersonList()
    }

    override suspend fun getPersonDetails(personId: Int): Resource<PersonDetail> = safeApiCall.execute {
        api.getPersonDetails(personId).toPersonDetail()
    }
}