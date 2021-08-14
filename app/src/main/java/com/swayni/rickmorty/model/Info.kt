package com.swayni.rickmorty.model

import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("count") val count : Int,
    @SerializedName("pages") val  pages : Int,
    @SerializedName("next") val nextUrl: String,
    @SerializedName("prev") val prevUrl:String
)
