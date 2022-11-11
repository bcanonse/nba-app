package com.bcanon.nbacloneapp.teams.data.datasources

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.bcanon.nbacloneapp.home.data.database.NbaDatabase
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

//TOdo: Agregar testing.
@RunWith(JUnit4::class)
class LocalTeamsDataSourceImplTest() {

    private lateinit var database: NbaDatabase

    private lateinit var localDataSource: LocalTeamsDataSource

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            NbaDatabase::class.java
        ).build()


        localDataSource = LocalTeamsDataSourceImpl(
            dao = database.getTeamsDao()
        )
    }

    @After
    fun tearDown() = database.close()

    @Test
    @Throws(Exception::class)
    fun `When is insert list teams in database using local remote data source`() = runBlocking {
        // GIVEN - A new task saved in the database.
        coEvery { localDataSource.insertTeams(any()) }

        // WHEN
        // THEN - Same task is returned.
        coVerify { localDataSource.insertTeams(any()) }
    }
}