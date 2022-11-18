package com.bcanon.nbacloneapp.players.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bcanon.nbacloneapp.players.data.datasources.RemotePlayersDataSource
import com.bcanon.nbacloneapp.players.domain.mappers.remoteToDomain
import com.bcanon.nbacloneapp.players.domain.model.Players
import retrofit2.HttpException
import java.io.IOException

private const val INITIAL_LOAD_SIZE = 1

class PlayersPagingSource(
    private val query: String,
    private val remoteDataSource: RemotePlayersDataSource
) : PagingSource<Int, Players>() {
    override fun getRefreshKey(state: PagingState<Int, Players>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Players> {
        val position = params.key ?: INITIAL_LOAD_SIZE

        return try {
            val players = remoteDataSource.getPlayers(
                query = query,
                page = position,
                perPage = params.loadSize
            ).remoteToDomain()

            LoadResult.Page(
                data = players,
                prevKey = if (position == INITIAL_LOAD_SIZE) null else position - 1,
                nextKey = if (players.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}