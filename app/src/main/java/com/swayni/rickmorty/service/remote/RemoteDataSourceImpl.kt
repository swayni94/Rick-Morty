package com.swayni.rickmorty.service.remote

import com.swayni.rickmorty.data.ResultData
import com.swayni.rickmorty.model.AllCharactersModel
import com.swayni.rickmorty.model.Character
import com.swayni.rickmorty.model.Episode
import com.swayni.rickmorty.service.ApiService
import java.lang.Exception

class RemoteDataSourceImpl : RemoteDataSource {
    private val service = ApiService.invoke()
    override suspend fun getAllCharacters(): ResultData<AllCharactersModel> {

        return try {
            val request = service.getAllCharacter()
            ResultData.Success(request)
        }catch (e : Exception){
            ResultData.Error(e)
        }

    }

    override suspend fun getCharacterInfo(id:Int): ResultData<Character> {

        return try {
            val request = service.getCharacter(id)
            ResultData.Success(request)
        }catch (e : Exception){
            ResultData.Error(e)
        }
    }

    override suspend fun getEpisodeList(ids: String): ResultData<List<Episode>> {
        return try {
            val request = service.getEpisodeInfo(ids)
            ResultData.Success(request)
        }catch (e : Exception){
            //Exception("Error when get data!")
            ResultData.Error(e)
        }
    }
}