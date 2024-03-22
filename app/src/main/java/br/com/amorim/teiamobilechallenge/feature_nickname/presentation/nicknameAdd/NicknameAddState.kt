package br.com.amorim.teiamobilechallenge.feature_nickname.presentation.nicknameAdd

data class NicknameAddState(
    val isLoading: Boolean = false,
    val nicknames: List<String> = emptyList(),
)
