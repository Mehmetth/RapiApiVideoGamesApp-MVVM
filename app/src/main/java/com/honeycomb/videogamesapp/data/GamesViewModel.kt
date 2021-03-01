package com.honeycomb.videogamesapp.data

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.honeycomb.videogamesapp.repository.RapidRepository

class GamesViewModel@ViewModelInject constructor(
    private val repository: RapidRepository,
    @Assisted state: SavedStateHandle
) : ViewModel() {

    val currentQuery = state.getLiveData("", "")

    val games = currentQuery.switchMap {
        repository.getGamesResults().cachedIn(viewModelScope)
    }

    fun searchNameChanged(name:String){
        currentQuery.switchMap {
            repository.getGamesResults().cachedIn(viewModelScope)
        }
    }
}