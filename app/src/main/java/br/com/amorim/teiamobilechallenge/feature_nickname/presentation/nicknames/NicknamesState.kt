package br.com.amorim.teiamobilechallenge.feature_nickname.presentation.nicknames

data class NicknamesState(
    val isLoading: Boolean = false,
    val nicknames: List<String> = emptyList(),
)
