package com.bcanon.nbacloneapp.teams_detail.data.datasources

import com.bcanon.nbacloneapp.teams.data.datasources.getTeams
import com.bcanon.nbacloneapp.teams_detail.data.network.TeamApi
import junit.framework.TestCase.assertNotNull
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

class RemoteTeamDataSourceImplTest {
    private lateinit var remoteDataSource: RemoteTeamDataSource

    private lateinit var webServer: MockWebServer

    @Before
    fun setUp() {
        webServer = MockWebServer()

        val okHttpClient = OkHttpClient.Builder()
            .writeTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .connectTimeout(1, TimeUnit.SECONDS)
            .build()

        val api = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(webServer.url("/")).build().create(TeamApi::class.java)

        remoteDataSource = RemoteTeamDataSourceImpl(
            api = api,
        )
    }

    @After
    fun tearDown() {
        webServer.shutdown()
    }

    @Test
    fun `When get object one team, returns success response and not empty list`() =
        runBlocking {
            //Given
            webServer.enqueue(MockResponse().setBody(getTeams).setResponseCode(200))
            //When
            val result = remoteDataSource.getTeamById(id = 1)
            //Then
            assertNotNull(result)
        }

    @Test
    fun `When get object one team, is response status code not success and execute exception`() =
        runBlocking {
            //Given
            webServer.enqueue(
                MockResponse().setResponseCode(500).setResponseCode(404)
            )

            //When
            var exceptionHandle = false
            try {
                remoteDataSource.getTeamById(id = 1)
            } catch (e: Exception) {
                exceptionHandle = true
            }

            //Then
            assert(exceptionHandle)
        }
}