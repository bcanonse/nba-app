package com.bcanon.nbacloneapp.teams_detail.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcanon.nbacloneapp.core.StateWrapper
import com.bcanon.nbacloneapp.teams.domain.model.Teams
import com.bcanon.nbacloneapp.teams_detail.domain.usecase.GetTeamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val getTeamUseCase: GetTeamUseCase
) : ViewModel() {

    private val _state: MutableLiveData<StateWrapper<Teams>> = MutableLiveData(StateWrapper())
    val state: LiveData<StateWrapper<Teams>> get() = _state

    fun getTeam(id: Int) {
        viewModelScope.launch {
            _state.value = _state.value?.copy(
                isLoading = true
            )

            val result = getTeamUseCase(id = id)

            result.onSuccess {
                if (it != null)
                    _state.value = _state.value?.copy(
                        data = it
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