package br.com.amorim.teiamobilechallenge.feature_nickname.presentation.nicknameAdd

import br.com.amorim.teiamobilechallenge.feature_nickname.domain.model.Nickname

sealed class NicknameAddEvent {
    data class SaveNickname(val nickname: Nickname): NicknameAddEvent()
    data class NicknameChanged(val value: String): NicknameAddEvent()
}