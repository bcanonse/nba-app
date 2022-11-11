package com.bcanon.nbacloneapp.teams.di

import com.bcanon.nbacloneapp.home.data.database.NbaDatabase
import com.bcanon.nbacloneapp.teams.data.database.dao.TeamsDao
import com.bcanon.nbacloneapp.teams.data.datasources.LocalTeamsDataSource
import com.bcanon.nbacloneapp.teams.data.datasources.LocalTeamsDataSourceImpl
import com.bcanon.nbacloneapp.teams.data.datasources.RemoteTeamsDataSource
import com.bcanon.nbacloneapp.teams.data.datasources.RemoteTeamsDataSourceImpl
import com.bcanon.nbacloneapp.teams.data.network.TeamsApi
import com.bcanon.nbacloneapp.teams.data.repositories.TeamsRepository
import com.bcanon.nbacloneapp.teams.data.repositories.TeamsRepositoryImpl
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TeamModule {

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): TeamsApi = retrofit.create(TeamsApi::class.java)

    @Singleton
    @Provides
    fun provideQuoteDao(db: NbaDatabase) = db.getTeamsDao()

    @Provides
    @Singleton
    fun provideRemoteDataSource(api: TeamsApi): RemoteTeamsDataSource =
        RemoteTeamsDataSourceImpl(api)

    @Provides
    @Singleton
    fun provideLocalDataSource(dao: TeamsDao): LocalTeamsDataSource = LocalTeamsDataSourceImpl(dao)

    @Provides
    @Singleton
    fun provideRepository(
        remoteTeamsDataSource: RemoteTeamsDataSource,
        localTeamsDataSource: LocalTeamsDataSource
    ): TeamsRepository = TeamsRepositoryImpl(
        remoteTeamsDataSource,
        localTeamsDataSource
    )
}