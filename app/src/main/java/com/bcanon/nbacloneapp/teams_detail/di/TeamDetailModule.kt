package com.bcanon.nbacloneapp.teams_detail.di

import com.bcanon.nbacloneapp.teams.data.database.dao.TeamsDao
import com.bcanon.nbacloneapp.teams_detail.data.datasources.LocalTeamDataSource
import com.bcanon.nbacloneapp.teams_detail.data.datasources.LocalTeamDataSourceImpl
import com.bcanon.nbacloneapp.teams_detail.data.datasources.RemoteTeamDataSource
import com.bcanon.nbacloneapp.teams_detail.data.datasources.RemoteTeamDataSourceImpl
import com.bcanon.nbacloneapp.teams_detail.data.network.TeamApi
import com.bcanon.nbacloneapp.teams_detail.data.repositories.TeamRepository
import com.bcanon.nbacloneapp.teams_detail.data.repositories.TeamRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TeamDetailModule {

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): TeamApi = retrofit.create(TeamApi::class.java)

    @Provides
    @Singleton
    fun provideLocalDataSource(dao: TeamsDao): LocalTeamDataSource = LocalTeamDataSourceImpl(dao)

    @Provides
    @Singleton
    fun provideRemoteDataSource(api: TeamApi): RemoteTeamDataSource =
        RemoteTeamDataSourceImpl(api)

    @Provides
    @Singleton
    fun provideRepository(
        remoteTeamsDataSource: RemoteTeamDataSource,
        localTeamsDataSource: LocalTeamDataSource
    ): TeamRepository = TeamRepositoryImpl(
        remoteTeamsDataSource,
        localTeamsDataSource
    )
}