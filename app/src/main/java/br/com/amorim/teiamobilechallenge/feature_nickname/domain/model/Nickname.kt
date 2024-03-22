package br.com.amorim.teiamobilechallenge.feature_nickname.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Nickname (
    val pat: Int = 0,
    val nickname: String = "",
    val timestamp: Long,
    @PrimaryKey val id: Int? = null
)

class InvalidNickNameException(message: String): Exception(message)