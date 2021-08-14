package com.swayni.rickmorty.service.remote

import com.swayni.rickmorty.data.ResultData
import com.swayni.rickmorty.model.AllCharactersModel
import com.swayni.rickmorty.model.Character
import com.swayni.rickmorty.model.Episode

interface RemoteDataSource {
    suspend fun getAllCharacters():ResultData<AllCharactersModel>
    suspend fun getCharacterInfo(id:Int):ResultData<Character>
    suspend fun getEpisodeList(ids:String):ResultData<List<Episode>>
}