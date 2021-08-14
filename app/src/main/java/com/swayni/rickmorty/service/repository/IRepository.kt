package com.swayni.rickmorty.service.repository

import com.swayni.rickmorty.data.ResultData
import com.swayni.rickmorty.model.AllCharactersModel
import com.swayni.rickmorty.model.Character
import com.swayni.rickmorty.model.Episode

interface IRepository {
    suspend fun getAllCharacters():ResultData<AllCharactersModel>
    suspend fun getCharacter(id:Int):ResultData<Character>
    suspend fun getEpisodeList(ids:String):ResultData<List<Episode>>
}