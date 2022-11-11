package com.bcanon.nbacloneapp.teams.domain.usecase

import com.bcanon.nbacloneapp.teams.data.repositories.TeamsRepository
import com.bcanon.nbacloneapp.teams.domain.model.Teams
import javax.inject.Inject

class GetTeamsUseCase @Inject constructor(
    private val repository: TeamsRepository
) {
    suspend operator fun invoke(): Result<List<Teams>> = repository.getTeams()
}