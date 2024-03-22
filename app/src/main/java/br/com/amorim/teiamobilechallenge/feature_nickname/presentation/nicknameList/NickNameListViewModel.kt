package br.com.amorim.teiamobilechallenge.feature_nickname.presentation.nicknameList

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.model.Nickname
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.use_case.NicknameUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NickNameListViewModel @Inject constructor(
    private val nicknameUseCases: NicknameUseCases,
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _nicknamesList = mutableStateOf(emptyList<Nickname>())
    val nicknamesList: State<List<Nickname>> = _nicknamesList
    fun onEvent(event: NicknameListEvent) {
        when (event) {
            NicknameListEvent.LoadNicknames -> {
                _isLoading.value = true
                viewModelScope.launch {
                    nicknameUseCases.getAllNicknames().collect { nicknames ->
                        _nicknamesList.value = nicknames
                        _eventFlow.emit(UiEvent.LoadedNickNames)
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data object LoadedNickNames : UiEvent()
    }
}