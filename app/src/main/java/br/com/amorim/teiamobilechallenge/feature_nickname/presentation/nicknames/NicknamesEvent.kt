package br.com.amorim.teiamobilechallenge.feature_nickname.presentation.nicknames

import br.com.amorim.teiamobilechallenge.feature_nickname.domain.model.Nickname

sealed class NicknamesEvent {
    data object SaveNickname: NicknamesEvent()
    data class NicknameChanged(val value: String): NicknamesEvent()
}