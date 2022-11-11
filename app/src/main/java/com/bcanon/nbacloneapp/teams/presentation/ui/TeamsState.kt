package com.bcanon.nbacloneapp.teams.presentation.ui

import com.bcanon.nbacloneapp.teams.domain.model.Teams

data class TeamsState(
    val isLoading: Boolean = true,
    val teams: List<Teams> = emptyList(),
    val error: String? = null
)
