package br.com.amorim.teiamobilechallenge.feature_nickname.data.data_source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.model.Nickname
import kotlinx.coroutines.flow.Flow

@Dao
interface NicknameDao {

    @Query("SELECT * FROM nickname")
    fun getNicknames(): Flow<List<Nickname>>

    @Query("SELECT * FROM nickname WHERE id = :id")
    suspend fun getNicknameById(id: Int): Nickname?

    @Query("SELECT * FROM nickname WHERE nickname = :nickname")
    suspend fun getNicknameByNickname(nickname: String): Nickname?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNickname(nickname: Nickname)

    @Delete
    suspend fun deleteNickname(nickname: Nickname)
}