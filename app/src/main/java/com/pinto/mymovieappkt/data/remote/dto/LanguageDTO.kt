package com.pinto.mymovieappkt.data.remote.dto


import com.google.gson.annotations.SerializedName

data class LanguageDTO(
    @SerializedName("english_name")
    val englishName: String
)