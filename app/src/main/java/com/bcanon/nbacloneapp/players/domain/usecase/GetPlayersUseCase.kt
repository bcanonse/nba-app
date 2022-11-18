package com.bcanon.nbacloneapp.players.domain.usecase

import androidx.paging.PagingData
import com.bcanon.nbacloneapp.players.data.repositories.PlayersRepository
import com.bcanon.nbacloneapp.players.domain.model.Players
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlayersUseCase @Inject constructor(
    private val repository: PlayersRepository
) {
    operator fun invoke(query: String): Flow<PagingData<Players>> = repository.getPlayers(query)
}