package br.com.amorim.teiamobilechallenge.feature_nickname.domain.use_case

import br.com.amorim.teiamobilechallenge.core.extensions.isAlphanumeric
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.model.InvalidNickNameException
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.model.Nickname
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.repository.NicknameRepository

class AddNickName (
    private val repository: NicknameRepository
) {
    @Throws(InvalidNickNameException::class)
    suspend operator fun invoke(nickname: Nickname) {
        if(!nickname.nickname.isAlphanumeric()) {
            throw InvalidNickNameException("Apenas caracteres alfanuméricos são permitidos.")
        }
        if(nickname.nickname.length < 3) {
            throw InvalidNickNameException("O apelido deve ter no mínimo 3 caracteres.")
        }
        if (nickname.nickname.length > 20) {
            throw InvalidNickNameException("O apelido deve ter no máximo 20 caracteres.")
        }
        repository.insertNickname(nickname)
    }
}