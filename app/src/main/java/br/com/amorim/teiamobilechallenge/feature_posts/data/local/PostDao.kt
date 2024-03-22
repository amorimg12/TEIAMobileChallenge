package br.com.amorim.teiamobilechallenge.feature_posts.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PostDao {

    @Upsert
    suspend fun upsertAll(posts: List<PostEntity>)

    @Query("SELECT * FROM postentity")
    fun paggingSource(): PagingSource<Int, PostEntity>

    @Query("DELETE FROM postentity")
    suspend fun clearAll()
}