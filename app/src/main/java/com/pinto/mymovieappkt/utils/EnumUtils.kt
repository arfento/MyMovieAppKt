package com.pinto.mymovieappkt.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Detail : Parcelable {
    MOVIE,
    TV,
    TV_SEASON,
    TV_EPISODE,
    PERSON
}


enum class ImageQuality(val imageBaseUrl: String) {
    LOW("https://image.tmdb.org/t/p/w300"),
    MEDIUM("https://image.tmdb.org/t/p/w500"),
    HIGH("https://image.tmdb.org/t/p/w780"),
    ORIGINAL("https://image.tmdb.org/t/p/original")
}

@Parcelize
enum class Content : Parcelable {
    EXPLORE_LIST,
    VIDEOS,
    CAST,
    IMAGES,
    RECOMMENDATIONS,
    PERSON_CREDITS,
    SEARCH,
    GENRE
}