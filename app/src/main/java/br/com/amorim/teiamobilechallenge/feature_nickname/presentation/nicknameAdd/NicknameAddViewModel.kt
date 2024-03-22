package br.com.amorim.teiamobilechallenge.feature_nickname.presentation.nicknameAdd

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.model.InvalidNickNameException
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.model.InvalidPATException
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.use_case.NicknameUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NicknameAddViewModel @Inject constructor(
    private val nicknameUseCases: NicknameUseCases,
) : ViewModel() {

    private val _patError = mutableStateOf(false)
    val patError: State<Boolean> = _patError
    private val _nickname = mutableStateOf("")
    val nickname: State<String> = _nickname
    private val _isError = mutableStateOf(false)
    val isError: State<Boolean> = _isError
    private val _errorText = mutableStateOf("")
    val errorText: State<String> = _errorText

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: NicknameAddEvent) {
        when (event) {
            is NicknameAddEvent.SaveNickname -> {
                viewModelScope.launch {
                    try {
                        nicknameUseCases.addNickName(
                            event.nickname
                        )
                        _isError.value = false
                        _patError.value = false
                        _errorText.value = ""
                        _nickname.value = ""
                        _eventFlow.emit(UiEvent.SavedNickname)
                    } catch (e: InvalidNickNameException) {
                        _isError.value = true
                        _errorText.value = e.message ?: "O apelido não pode ser salvo."
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = "Há erros no formulário."
                            )
                        )
                    } catch (e: InvalidPATException) {
                        _patError.value = true
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = "Há um problema com o PAT"
                            )
                        )
                    }
                }
            }

            is NicknameAddEvent.NicknameChanged -> {
                _isError.value = false
                _errorText.value = ""
                _nickname.value = event.value
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data object SavedNickname : UiEvent()
    }
}
