package com.bcanon.nbacloneapp.teams.data.datasources

import com.bcanon.nbacloneapp.teams.data.network.TeamsApi
import com.bcanon.nbacloneapp.teams.data.network.dto.TeamsDto
import io.mockk.MockKAnnotations
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

class RemoteTeamsDataSourceImplTest {

    private lateinit var remoteDataSource: RemoteTeamsDataSource

    private lateinit var webServer: MockWebServer

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        webServer = MockWebServer()

        val okHttpClient = OkHttpClient.Builder()
            .writeTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .connectTimeout(1, TimeUnit.SECONDS)
            .build()

        val api = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(webServer.url("/")).build().create(TeamsApi::class.java)

        remoteDataSource = RemoteTeamsDataSourceImpl(
            teamsApi = api
        )
    }

    @After
    fun tearDown() {
        webServer.shutdown()
    }

    @Test
    fun `When get all list teams, returns success response and not empty list`() =
        runBlocking {
            //Given
            webServer.enqueue(MockResponse().setBody(getTeams).setResponseCode(200))
            //When
            val result = remoteDataSource.getTeams()
            //Then
            assert(result.isNotEmpty())
        }

    @Test
    fun `When get all list teams, returns 200 status code and response is empty list`() =
        runBlocking {
            //Given
            webServer.enqueue(
                MockResponse().setResponseCode(200).setBody(getTeamsEmpty)
            )
            //When
            val result = remoteDataSource.getTeams()
            //Then
            assert(result == emptyList<TeamsDto>())
        }

    @Test
    fun `When get all list teams, is response status code not success and execute exception`() =
        runBlocking {
            //Given
            webServer.enqueue(
                MockResponse().setResponseCode(500).setResponseCode(404)
            )

            //When
            var exceptionHandle = false
            try {
                remoteDataSource.getTeams()
            } catch (e: Exception) {
                exceptionHandle = true
            }

            //Then
            assert(exceptionHandle)
        }


}