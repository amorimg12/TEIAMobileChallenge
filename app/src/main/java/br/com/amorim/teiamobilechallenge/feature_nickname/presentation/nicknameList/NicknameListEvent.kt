package br.com.amorim.teiamobilechallenge.feature_nickname.presentation.nicknameList

sealed class NicknameListEvent {
   data object LoadNicknames: NicknameListEvent()
}