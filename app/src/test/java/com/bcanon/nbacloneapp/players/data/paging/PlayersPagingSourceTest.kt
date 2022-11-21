package com.bcanon.nbacloneapp.players.data.paging

import androidx.paging.PagingSource
import com.bcanon.nbacloneapp.players.data.datasources.RemotePlayersDataSource
import com.bcanon.nbacloneapp.players.data.network.dto.mockListPlayersDto
import com.bcanon.nbacloneapp.players.domain.mappers.remoteToDomain
import com.bcanon.nbacloneapp.players.domain.model.Players
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class PlayersPagingSourceTest {

    @RelaxedMockK
    private lateinit var remoteDataSource: RemotePlayersDataSource

    private lateinit var pagingSource: PlayersPagingSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        pagingSource = PlayersPagingSource(
            query = "James",
            remoteDataSource = remoteDataSource
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `reviews paging source load - failure - http error`() = runTest {
        //Given
        val error = RuntimeException("500", IOException())

        coEvery {
            remoteDataSource.getPlayers(
                query = "James",
                page = 1,
                perPage = 5
            )
        } throws error

        //When
        val expectedResult = PagingSource.LoadResult.Error<Int, Players>(error)

        // Then
        assertEquals(
            expectedResult.throwable, error
        )
    }

    @Test
    fun `Reviews paging source refresh data since remoteDataSource - success`() = runTest {
        // Given
        coEvery { remoteDataSource.getPlayers(any(), any(), any()) }.returns(mockListPlayersDto)


        // When
        val expectedResult = PagingSource.LoadResult.Page(
            data = mockListPlayersDto.remoteToDomain(),
            prevKey = null,
            nextKey = 2
        )

        // Then

        assertEquals(
            expectedResult, pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `Reviews paging source append data since remoteDataSource - success`() = runTest {
        //Given
        coEvery { remoteDataSource.getPlayers(any(), any(), any()) }.returns(mockListPlayersDto)

        // When the page is 2 prev 1 to next is 3
        val expectedResult = PagingSource.LoadResult.Page(
            data = mockListPlayersDto.remoteToDomain(),
            prevKey = 1,
            nextKey = 3
        )

        assertEquals(
            expectedResult, pagingSource.load(
                PagingSource.LoadParams.Append(
                    key = 2,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `Reviews paging source load - failure - received empty list`() = runTest {
        //Given
        coEvery {
            remoteDataSource.getPlayers(
                any(),
                any(),
                any()
            )
        } returns emptyList()


        // When
        val expectedResult =
            PagingSource.LoadResult.Page<Int, Players>(
                data = emptyList(),
                prevKey = null,
                nextKey = null
            )

        //Then
        assertEquals(
            expectedResult, pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 0,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `Reviews paging source prepend data since remoteDataSource - success`() = runTest {
        //Given
        coEvery { remoteDataSource.getPlayers(any(), any(), any()) }.returns(mockListPlayersDto)

        // When
        val expectedResult = PagingSource.LoadResult.Page(
            data = mockListPlayersDto.remoteToDomain(),
            prevKey = null,
            nextKey = 2
        )

        //Then
        assertEquals(
            expectedResult,
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            ),
        )
    }
}