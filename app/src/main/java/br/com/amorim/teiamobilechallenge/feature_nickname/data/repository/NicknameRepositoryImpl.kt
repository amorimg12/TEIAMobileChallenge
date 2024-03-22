package br.com.amorim.teiamobilechallenge.feature_nickname.data.repository

import br.com.amorim.teiamobilechallenge.feature_nickname.data.data_source.local.NicknameDao
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.repository.NicknameRepository
import kotlinx.coroutines.flow.Flow

class NicknameRepositoryImpl(
    private val dao: NicknameDao
): NicknameRepository {
    override fun getNicknames(): Flow<List<br.com.amorim.teiamobilechallenge.feature_nickname.domain.model.Nickname>> {
        return dao.getNicknames()
    }

    override suspend fun getNicknameById(id: Int): br.com.amorim.teiamobilechallenge.feature_nickname.domain.model.Nickname? {
        return dao.getNicknameById(id)
    }

    override suspend fun getNicknameByNickname(nickname: String): br.com.amorim.teiamobilechallenge.feature_nickname.domain.model.Nickname? {
        return dao.getNicknameByNickname(nickname)
    }

    override suspend fun insertNickname(nickname: br.com.amorim.teiamobilechallenge.feature_nickname.domain.model.Nickname) {
        dao.insertNickname(nickname)
    }

    override suspend fun deleteNickname(nickname: br.com.amorim.teiamobilechallenge.feature_nickname.domain.model.Nickname) {
        dao.deleteNickname(nickname)
    }
}