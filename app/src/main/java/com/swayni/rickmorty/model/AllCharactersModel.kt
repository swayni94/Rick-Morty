package com.swayni.rickmorty.model

import com.google.gson.annotations.SerializedName

data class AllCharactersModel(
    @SerializedName("info") val info: Info,
    @SerializedName("results") val result: List<Character>
)
