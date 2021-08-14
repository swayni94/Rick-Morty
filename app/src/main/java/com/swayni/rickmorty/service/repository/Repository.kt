package com.swayni.rickmorty.service.repository

import com.swayni.rickmorty.data.ResultData
import com.swayni.rickmorty.model.AllCharactersModel
import com.swayni.rickmorty.model.Character
import com.swayni.rickmorty.model.Episode
import com.swayni.rickmorty.service.remote.RemoteDataSource
import java.lang.Exception

class Repository (private val remoteDataSource : RemoteDataSource) : IRepository{

    override suspend fun getAllCharacters(): ResultData<AllCharactersModel> {
        return when(val result = remoteDataSource.getAllCharacters()){
            is ResultData.Success->{
                ResultData.Success(result.data)
            }
            is ResultData.Error ->{
                ResultData.Error(Exception("Data not found"))
            }
        }
    }

    override suspend fun getCharacter(id:Int): ResultData<Character> {
        return when(val result = remoteDataSource.getCharacterInfo(id)){
            is ResultData.Success->{
                ResultData.Success(result.data)
            }
            is ResultData.Error->{
                ResultData.Error(Exception("Data not found"))
            }
        }
    }

    override suspend fun getEpisodeList(ids: String): ResultData<List<Episode>> {
        return when(val result = remoteDataSource.getEpisodeList(ids)){
            is ResultData.Success->{
                ResultData.Success(result.data)
            }
            is ResultData.Error->{
                ResultData.Error(Exception("Data not found"))
            }
        }
    }

}