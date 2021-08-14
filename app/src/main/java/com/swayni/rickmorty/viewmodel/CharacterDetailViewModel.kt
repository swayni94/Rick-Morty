package com.swayni.rickmorty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swayni.rickmorty.data.ResultData
import com.swayni.rickmorty.model.Character
import com.swayni.rickmorty.model.Episode
import com.swayni.rickmorty.service.repository.Repository
import kotlinx.coroutines.launch

class CharacterDetailViewModel(private val repository: Repository) : ViewModel() {

    private var _characterInfoMutableLiveData = MutableLiveData<Character>()
    val characterInfoLiveData : LiveData<Character> get() = _characterInfoMutableLiveData

    private var _episodeInfoListMutableLiveData = MutableLiveData<List<Episode>>()
    val episodeInfoListLiveData : LiveData<List<Episode>> get() = _episodeInfoListMutableLiveData

    private val _errorString = MutableLiveData<String>()
    val errorString : LiveData<String> get() = _errorString


    fun getCharacterInfo(id:Int){
        viewModelScope.launch {
            when (val response = repository.getCharacter(id)){
                is ResultData.Success->{
                    _characterInfoMutableLiveData.postValue(response.data)
                    _errorString.postValue("")
                }
                is ResultData.Error -> {
                    _errorString.postValue("${response.exception.message} in character info")
                }
            }
        }
    }

    fun getEpisodesNames(ids:String){
        viewModelScope.launch {
            when(val response = repository.getEpisodeList(ids)){
                is ResultData.Success->{
                    _episodeInfoListMutableLiveData.postValue(response.data)
                    _errorString.postValue("")
                }
                is ResultData.Error->{
                    _errorString.postValue("${response.exception.message} in episode info list")
                }
            }
        }
    }
}