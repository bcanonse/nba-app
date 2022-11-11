package com.bcanon.nbacloneapp.teams_detail.domain.usecase

import com.bcanon.nbacloneapp.teams.domain.model.Teams
import com.bcanon.nbacloneapp.teams_detail.data.repositories.TeamRepository
import javax.inject.Inject

class GetTeamUseCase @Inject constructor(
    private val repository: TeamRepository
) {
    suspend operator fun invoke(id: Int): Result<Teams?> = repository.getTeamById(id = id)
}