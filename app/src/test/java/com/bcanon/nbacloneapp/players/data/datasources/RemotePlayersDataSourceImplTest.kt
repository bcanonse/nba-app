package com.bcanon.nbacloneapp.players.data.datasources

import com.bcanon.nbacloneapp.players.data.network.PlayersApi
import com.bcanon.nbacloneapp.players.data.network.dto.PlayersDto
import com.bcanon.nbacloneapp.players.data.network.dto.getPlayers
import com.bcanon.nbacloneapp.players.data.network.dto.getPlayersEmpty
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class RemotePlayersDataSourceImplTest {

    private lateinit var remoteDataSource: RemotePlayersDataSource
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()

        val okHttpClient = OkHttpClient.Builder()
            .writeTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .connectTimeout(1, TimeUnit.SECONDS)
            .build()

        val api = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(mockWebServer.url("/")).build().create(PlayersApi::class.java)

        remoteDataSource = RemotePlayersDataSourceImpl(
            playersApi = api
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `When get all players using query params, returns success response and not empty list`() =
        runBlocking {
            //Given
            mockWebServer.enqueue(MockResponse().setBody(getPlayers).setResponseCode(200))
            //When
            val result = remoteDataSource.getPlayers(query = "James", page = 1, perPage = 1)
            //Then
            assert(result.isNotEmpty())
        }


    @Test
    fun `When get all players using query params, returns 200 status code and response is empty list`() =
        runBlocking {
            //Given
            mockWebServer.enqueue(
                MockResponse().setResponseCode(200).setBody(getPlayersEmpty)
            )
            //When
            val result = remoteDataSource.getPlayers(query = "James", page = 1, perPage = 1)
            //Then
            assert(result == emptyList<PlayersDto>())
        }

    @Test
    fun `When get all players using query params, is response status code not success and execute exception`() =
        runBlocking {
            //Given
            mockWebServer.enqueue(
                MockResponse().setResponseCode(500).setResponseCode(404)
            )

            //When
            var exceptionHandle = false
            try {
                remoteDataSource.getPlayers(query = "James", page = 1, perPage = 1)
            } catch (e: Exception) {
                exceptionHandle = true
            }

            //Then
            assert(exceptionHandle)
        }
}