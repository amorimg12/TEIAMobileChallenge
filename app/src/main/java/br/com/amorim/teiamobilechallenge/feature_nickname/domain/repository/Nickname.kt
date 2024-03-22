package br.com.amorim.teiamobilechallenge.feature_nickname.domain.repository

import br.com.amorim.teiamobilechallenge.feature_nickname.domain.model.Nickname
import kotlinx.coroutines.flow.Flow

//Interface to simplify the implementation of tests
// Allows to use a fake repository to test the use cases
interface  NicknameRepository {

    fun getNicknames(): Flow<List<Nickname>>

    suspend fun getNicknameById(id: Int): Nickname?

    suspend fun getNicknameByNickname(nickname: String): Nickname?

    suspend fun insertNickname(nickname: Nickname)

    suspend fun deleteNickname(nickname: Nickname)
}