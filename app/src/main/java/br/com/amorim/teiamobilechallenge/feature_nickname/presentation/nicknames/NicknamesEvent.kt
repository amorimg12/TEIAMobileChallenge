package br.com.amorim.teiamobilechallenge.feature_nickname.presentation.nicknames

import br.com.amorim.teiamobilechallenge.feature_nickname.domain.model.Nickname

sealed class NicknamesEvent {
    data class SaveNickname(val nickname: Nickname): NicknamesEvent()
    data class NicknameChanged(val value: String): NicknamesEvent()
}