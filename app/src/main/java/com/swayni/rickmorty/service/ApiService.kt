package com.swayni.rickmorty.service

import com.swayni.rickmorty.model.AllCharactersModel
import com.swayni.rickmorty.model.Character
import com.swayni.rickmorty.model.Episode
import com.swayni.rickmorty.model.baseURL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface ApiService {
    @GET("character")
    suspend fun getAllCharacter() : AllCharactersModel

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id:Int): Character

    @GET("episode/{pathIds}")
    suspend fun getEpisodeInfo(@Path("pathIds") pathIds:String) : List<Episode>

    companion object{
        operator fun invoke(): ApiService {
            val client = OkHttpClient.Builder().readTimeout(1200, TimeUnit.SECONDS).connectTimeout(1200,TimeUnit.SECONDS).build()
            val retrofit = Retrofit.Builder().baseUrl(baseURL).addCallAdapterFactory(
                RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build()
            return retrofit.create(ApiService::class.java)
        }
    }
}