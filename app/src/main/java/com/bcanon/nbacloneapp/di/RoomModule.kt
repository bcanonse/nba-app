package com.bcanon.nbacloneapp.di

import android.content.Context
import androidx.room.Room
import com.bcanon.nbacloneapp.home.data.database.NbaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DB_NAME = "nba_db"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, NbaDatabase::class.java, DB_NAME).build()
}