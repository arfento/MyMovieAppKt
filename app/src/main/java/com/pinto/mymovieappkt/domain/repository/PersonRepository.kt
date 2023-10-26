package com.pinto.mymovieappkt.domain.repository

import com.pinto.mymovieappkt.domain.model.PersonDetail
import com.pinto.mymovieappkt.domain.model.PersonList
import com.pinto.mymovieappkt.utils.Resource

interface PersonRepository {
    suspend fun getPersonSearchResults(query: String, page: Int): Resource<PersonList>
    suspend fun getPersonDetails(personId: Int): Resource<PersonDetail>
}