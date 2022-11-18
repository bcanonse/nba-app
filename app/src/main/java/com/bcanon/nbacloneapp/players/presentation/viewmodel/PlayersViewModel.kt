package com.bcanon.nbacloneapp.players.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.bcanon.nbacloneapp.players.domain.usecase.GetPlayersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Using LiveData for manage the data flows with paging
 *
 * private val players = currentQuery.switchMap {
getPlayersUseCase(it).cachedIn(viewModelScope)
}
 * */

@HiltViewModel
class PlayersViewModel @Inject constructor(
    private val getPlayersUseCase: GetPlayersUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val currentQuery = savedStateHandle.getStateFlow(CURRENT_QUERY, DEFAULT_QUERY)

    @OptIn(ExperimentalCoroutinesApi::class)
    val players = currentQuery.flatMapLatest { query ->
        getPlayersUseCase(query = query).cachedIn(viewModelScope)
    }

    fun searchPlayer(query: String) {
        savedStateHandle[CURRENT_QUERY] = query
    }

    override fun onCleared() {
        savedStateHandle[CURRENT_QUERY] = currentQuery.value
        super.onCleared()
    }

    companion object {
        private const val CURRENT_QUERY: String = "current_query"
        const val DEFAULT_QUERY = "James"
    }

}