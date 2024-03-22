package br.com.amorim.teiamobilechallenge.feature_nickname.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(indices = [androidx.room.Index(value = ["nickname"], unique = true)])
data class Nickname (
    val pat: Int = 0,
    @ColumnInfo(name = "nickname")
    val nickname: String = "",
    val timestamp: Long,
    @PrimaryKey val id: Int? = null
)

class InvalidNickNameException(message: String): Exception(message)
class InvalidPATException(message: String): Exception(message)