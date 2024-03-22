package br.com.amorim.teiamobilechallenge.feature_posts.data.remote

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import br.com.amorim.teiamobilechallenge.feature_posts.data.local.PostDatabase
import br.com.amorim.teiamobilechallenge.feature_posts.data.local.PostEntity
import br.com.amorim.teiamobilechallenge.feature_posts.data.mappers.toPostEntity
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PostRemoteMediator(
    private val postDb: PostDatabase,
    private val postApi: PostApi,
) : RemoteMediator<Int, PostEntity>() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostEntity>,
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    }else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val posts = postApi.getPosts(
                page = loadKey,
                pageCount = state.config.pageSize
            )

            postDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    postDb.postDao.clearAll()
                }
                val postEntities = posts.map {it.toPostEntity() }
                postDb.postDao.upsertAll(postEntities)
            }

            MediatorResult.Success(endOfPaginationReached = posts.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}