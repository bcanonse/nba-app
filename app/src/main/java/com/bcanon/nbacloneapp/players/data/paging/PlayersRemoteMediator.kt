package com.bcanon.nbacloneapp.players.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.bcanon.nbacloneapp.players.data.database.entity.PlayersEntity
import com.bcanon.nbacloneapp.players.data.database.entity.PlayersRemoteKeys
import com.bcanon.nbacloneapp.players.data.database.mappers.toDatabase
import com.bcanon.nbacloneapp.players.data.datasources.LocalPlayersDataSource
import com.bcanon.nbacloneapp.players.data.datasources.RemotePlayersDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

private const val INITIAL_LOAD_SIZE = 1

@OptIn(ExperimentalPagingApi::class)
class PlayersRemoteMediator(
    private val query: String,
    private val localDataSource: LocalPlayersDataSource,
    private val remoteDataSource: RemotePlayersDataSource
) : RemoteMediator<Int, PlayersEntity>() {
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PlayersEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_LOAD_SIZE
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for prepend.
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }


        try {
            val players =
                remoteDataSource.getPlayers(
                    query = query,
                    page = page,
                    perPage = state.config.pageSize
                )

            val endOfPaginationReached = players.isEmpty()
            withContext(Dispatchers.Default) {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    localDataSource.clearRemoteKeys()
                    localDataSource.deleteAllPlayers()
                }
                val prevKey = if (page == INITIAL_LOAD_SIZE) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = players.map {
                    PlayersRemoteKeys(
                        playerId = it.id,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                localDataSource.insertRemoteKeys(keys)
                localDataSource.insertPlayers(players.toDatabase())
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PlayersEntity>): PlayersRemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                localDataSource.remoteKeysPlayerId(repo.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PlayersEntity>): PlayersRemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                localDataSource.remoteKeysPlayerId(repo.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PlayersEntity>
    ): PlayersRemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                localDataSource.remoteKeysPlayerId(repoId)
            }
        }
    }

}