package br.com.amorim.teiamobilechallenge.feature_nickname.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.model.Nickname

@Database(entities = [Nickname::class], version = 1)
abstract class NicknameDatabase : RoomDatabase(){
    abstract val nicknameDao: NicknameDao

    companion object {
        val DATABASE_NAME = "nickname_db"
    }
}