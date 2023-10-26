package com.pinto.mymovieappkt.di

import com.pinto.mymovieappkt.data.repository.MovieRepositoryImpl
import com.pinto.mymovieappkt.data.repository.PersonRepositoryImpl
import com.pinto.mymovieappkt.data.repository.TvRepositoryImpl
import com.pinto.mymovieappkt.domain.repository.MovieRepository
import com.pinto.mymovieappkt.domain.repository.PersonRepository
import com.pinto.mymovieappkt.domain.repository.TvRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindMovieRepository(repository: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun bindTvRepository(repository: TvRepositoryImpl): TvRepository

    @Binds
    abstract fun bindPersonRepository(repository: PersonRepositoryImpl): PersonRepository
}