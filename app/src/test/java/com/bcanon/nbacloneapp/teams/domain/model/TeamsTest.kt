package com.bcanon.nbacloneapp.teams.domain.model

import com.bcanon.nbacloneapp.teams.data.database.entity.TeamsEntity
import com.bcanon.nbacloneapp.teams.data.network.dto.TeamsDto
import com.bcanon.nbacloneapp.teams.domain.mapper.toDomain
import org.junit.Test

class TeamsTest {

    private companion object {
        private val teamsDto = TeamsDto(
            id = 1,
            name = "Atlanta",
            conference = "East",
            city = "Atlanta",
            division = "East",
            abbreviation = "Hawks",
            fullName = "Atlanta Hawks"
        )

        private val teamsEntity = TeamsEntity(
            id = 1,
            name = "Atlanta",
            conference = "East",
            city = "Atlanta",
            division = "East",
            abbreviation = "Hawks",
            fullName = "Atlanta Hawks"
        )

        private val teams = Teams(
            id = 1,
            name = "Atlanta",
            conference = "East",
            abbreviation = "Hawks",
            fullName = "Atlanta Hawks"
        )
    }

    @Test
    fun `Get object mapped for domain layer from dto service`() {
        //Given
        val teamsMap = teamsDto.toDomain()
        //When
        //Then
        assert(teamsMap.javaClass == teams.javaClass)
        assert(teamsMap == teams)
    }

    @Test
    fun `Get object mapped for domain layer from entity to database`() {
        //Given
        val teamsMap = teamsEntity.toDomain()
        //When
        //Then
        assert(teamsMap.javaClass == teams.javaClass)
        assert(teamsMap == teams)
    }

    @Test
    fun `Get object list mapped for domain layer from dto service`() {
        //Given
        val teamsListDto = listOf(
            teamsDto
        )
        //When
        val teamsListMap = teamsListDto.map { it.toDomain() }
        //Then
        assert(teamsListMap.first() == teams)
        assert(teamsListMap == listOf(teams))
    }

    @Test
    fun `Get object list mapped for domain layer from entity to database`() {
        //Given
        val teamsListEntity = listOf(
            teamsEntity
        )
        //When
        val teamsListMap = teamsListEntity.map { it.toDomain() }
        //Then
        assert(teamsListMap.first() == teams)
        assert(teamsListMap == listOf(teams))
    }
}