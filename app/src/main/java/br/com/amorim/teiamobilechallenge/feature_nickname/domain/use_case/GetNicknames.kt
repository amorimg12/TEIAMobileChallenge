package br.com.amorim.teiamobilechallenge.feature_nickname.domain.use_case

import br.com.amorim.teiamobilechallenge.feature_nickname.domain.model.Nickname
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.repository.NicknameRepository
import kotlinx.coroutines.flow.Flow

class GetNicknames(
    private val repository: NicknameRepository
) {
    operator fun invoke() : Flow<List<Nickname>> {
        return repository.getNicknames()
    }
}
