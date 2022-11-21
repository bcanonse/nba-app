package com.bcanon.nbacloneapp.players.domain.model

import com.bcanon.nbacloneapp.players.data.datasources.mockListPlayersEntity
import com.bcanon.nbacloneapp.players.data.datasources.mockPlayersEntity
import com.bcanon.nbacloneapp.players.data.network.dto.playersDto
import com.bcanon.nbacloneapp.players.domain.mappers.dbToDomain
import com.bcanon.nbacloneapp.players.domain.mappers.remoteToDomain
import com.bcanon.nbacloneapp.players.domain.mappers.toDomain
import org.junit.Test

class PlayersTest {
    @Test
    fun `Get object mapped for domain layer from dto service`() {
        //Given
        val playersMap = playersDto.toDomain()

        //When
        //Then
        assert(playersMap.javaClass == players.javaClass)
        assert(playersMap == players)
    }

    @Test
    fun `Get object mapped for domain layer from entity to database`() {
        //Given
        val playersMap = mockPlayersEntity.toDomain()
        //When
        //Then
        assert(playersMap.javaClass == players.javaClass)
        assert(playersMap == players)
    }

    @Test
    fun `Get object list mapped for domain layer from dto service`() {
        //Given
        val playersListDto = listOf(
            playersDto
        )
        //When
        val playersListMap = playersListDto.remoteToDomain()
        //Then
        assert(playersListMap.first() == players)
        assert(playersListMap == listOf(players))
    }

    @Test
    fun `Get object list mapped for domain layer from entity to database`() {
        //Given
        val playersListEntity = mockListPlayersEntity
        //When
        val playersListMap = playersListEntity.dbToDomain()
        //Then
        assert(playersListMap.first() == players)
        assert(playersListMap == listOf(players))
    }
}