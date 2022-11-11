package com.bcanon.nbacloneapp.teams.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcanon.nbacloneapp.teams.domain.usecase.GetTeamsUseCase
import com.bcanon.nbacloneapp.teams.presentation.ui.TeamsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamsViewModel @Inject constructor(
    private val getTeamsUseCase: GetTeamsUseCase
) : ViewModel() {

    private val _state: MutableLiveData<TeamsState> = MutableLiveData(TeamsState())
    val state: LiveData<TeamsState> get() = _state

    fun getTeams() {
        viewModelScope.launch {
            _state.value = _state.value?.copy(
                isLoading = true
            )
            val result = getTeamsUseCase()
            result.onSuccess {
                _state.value = _state.value?.copy(
                    teams = it
                )
            }.onFailure {
                _state.value = _state.value?.copy(
                    error = it.message.toString()
                )
            }
            _state.value = _state.value?.copy(isLoading = false)
        }
    }

}