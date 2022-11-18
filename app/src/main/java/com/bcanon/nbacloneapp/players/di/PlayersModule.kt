package com.bcanon.nbacloneapp.players.di

import com.bcanon.nbacloneapp.home.data.database.NbaDatabase
import com.bcanon.nbacloneapp.players.data.database.dao.PlayersDao
import com.bcanon.nbacloneapp.players.data.database.dao.PlayersRemoteKeysDao
import com.bcanon.nbacloneapp.players.data.datasources.LocalPlayersDataSource
import com.bcanon.nbacloneapp.players.data.datasources.LocalPlayersDataSourceImpl
import com.bcanon.nbacloneapp.players.data.datasources.RemotePlayersDataSource
import com.bcanon.nbacloneapp.players.data.datasources.RemotePlayersDataSourceImpl
import com.bcanon.nbacloneapp.players.data.network.PlayersApi
import com.bcanon.nbacloneapp.players.data.repositories.PlayersRepository
import com.bcanon.nbacloneapp.players.data.repositories.PlayersRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlayersModule {

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): PlayersApi = retrofit.create(PlayersApi::class.java)

    @Singleton
    @Provides
    fun providePlayersDao(db: NbaDatabase) = db.getPlayersDao()

    @Singleton
    @Provides
    fun providePlayersRemoteKeysDao(db: NbaDatabase) = db.getRemoteKeysPlayersDao()

    @Provides
    @Singleton
    fun provideRemoteDataSource(api: PlayersApi): RemotePlayersDataSource =
        RemotePlayersDataSourceImpl(api)


    @Provides
    @Singleton
    fun provideLocalDataSource(
        playersDao: PlayersDao,
        remoteKeysDao: PlayersRemoteKeysDao
    ): LocalPlayersDataSource =
        LocalPlayersDataSourceImpl(playersDao, remoteKeysDao)

    @Provides
    @Singleton
    fun provideRepository(
        remoteTeamsDataSource: RemotePlayersDataSource,
        localTeamsDataSource: LocalPlayersDataSource
    ): PlayersRepository = PlayersRepositoryImpl(
        remoteDataSource = remoteTeamsDataSource,
        localDataSource = localTeamsDataSource
    )
}