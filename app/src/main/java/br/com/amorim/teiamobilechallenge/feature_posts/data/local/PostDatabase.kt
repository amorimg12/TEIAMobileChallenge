package br.com.amorim.teiamobilechallenge.feature_posts.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PostEntity::class], version = 1)
abstract class PostDatabase : RoomDatabase() {
    abstract val postDao: PostDao

    companion object {
        val DATABASE_NAME = "post_db"
    }
}