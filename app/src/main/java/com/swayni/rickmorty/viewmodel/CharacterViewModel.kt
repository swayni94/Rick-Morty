package com.swayni.rickmorty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swayni.rickmorty.data.ResultData
import com.swayni.rickmorty.model.Character
import com.swayni.rickmorty.service.repository.Repository
import kotlinx.coroutines.launch

class CharacterViewModel (private val repository: Repository) : ViewModel() {

    private var _allCharactersMutableLiveData = MutableLiveData<List<Character>>()
    val allCharacterLiveData : LiveData<List<Character>> get() = _allCharactersMutableLiveData

    private val _errorString = MutableLiveData<String>()
    val errorString : LiveData<String> get() = _errorString

    fun getAllCharacters(){
        viewModelScope.launch {
            when (val response = repository.getAllCharacters()){
                is ResultData.Success->{
                    _allCharactersMutableLiveData.postValue(response.data.result)
                    _errorString.postValue("")
                }
                is ResultData.Error->{
                    _errorString.postValue("${response.exception.message} in character list")
                }
            }
        }
    }

}