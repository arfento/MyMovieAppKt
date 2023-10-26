package com.pinto.mymovieappkt.data.mapper

import com.pinto.mymovieappkt.data.local.entity.FavoriteMovieEntity
import com.pinto.mymovieappkt.data.local.entity.FavoriteTvEntity
import com.pinto.mymovieappkt.domain.model.FavoriteMovie
import com.pinto.mymovieappkt.domain.model.FavoriteTv


fun FavoriteMovie.toFavoriteMovieEntity(): FavoriteMovieEntity = FavoriteMovieEntity(
    id = id,
    posterPath = posterPath,
    releaseDate = releaseDate,
    runtime = runtime,
    title = title,
    voteAverage = voteAverage,
    voteCount = voteCount,
    date = date
)

fun FavoriteMovieEntity.toFavoriteMovie(): FavoriteMovie = FavoriteMovie(
    id = id,
    posterPath = posterPath,
    releaseDate = releaseDate,
    runtime = runtime,
    title = title,
    voteAverage = voteAverage,
    voteCount = voteCount,
    date = date
)

fun FavoriteTv.toFavoriteTvEntity(): FavoriteTvEntity = FavoriteTvEntity(
    id = id,
    episodeRunTime = episodeRunTime,
    firstAirDate = firstAirDate,
    name = name,
    posterPath = posterPath,
    voteAverage = voteAverage,
    voteCount = voteCount,
    date = date
)

fun FavoriteTvEntity.toFavoriteTv(): FavoriteTv = FavoriteTv(
    id = id,
    episodeRunTime = episodeRunTime,
    firstAirDate = firstAirDate,
    name = name,
    posterPath = posterPath,
    voteAverage = voteAverage,
    voteCount = voteCount,
    date = date
)