package br.com.amorim.teiamobilechallenge.feature_nickname.presentation.nicknameAdd

import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
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

    private val _bitmap = mutableStateOf<Bitmap?>(null)
    val bitmap: State<Bitmap?> = _bitmap

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

            is NicknameAddEvent.TakePicture -> {

                event.controller?.takePicture(
                    ContextCompat.getMainExecutor(event.context),
                    object : ImageCapture.OnImageCapturedCallback() {
                        override fun onCaptureSuccess(image: ImageProxy) {
                            super.onCaptureSuccess(image)

                            val matrix = Matrix().apply {
                                postRotate(image.imageInfo.rotationDegrees.toFloat())
                            }
                            val rotatedBitmap = Bitmap.createBitmap(
                                image.toBitmap(),
                                0,
                                0,
                                image.width,
                                image.height,
                                matrix,
                                true
                            )
                            _bitmap.value = rotatedBitmap
                            viewModelScope.launch {
                                _eventFlow.emit(UiEvent.PictureTaken("Foto tirada", rotatedBitmap))
                            }
                        }

                        override fun onError(exception: ImageCaptureException) {
                            super.onError(exception)
                            Log.e("Camera", "Couldn't take photo: ", exception)
                            _bitmap.value = null
                            viewModelScope.launch {
                                _eventFlow.emit(UiEvent.PictureError ("Erro ao tirar foto: ${exception.message}"))
                            }
                        }
                    }
                )
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data object SavedNickname : UiEvent()
        data class PictureTaken(val message: String, val bitmap: Bitmap) : UiEvent()
        data class PictureError(val message: String) : UiEvent()
    }
}
