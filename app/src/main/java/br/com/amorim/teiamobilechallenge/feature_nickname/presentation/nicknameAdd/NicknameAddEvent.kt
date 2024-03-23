package br.com.amorim.teiamobilechallenge.feature_nickname.presentation.nicknameAdd

import android.content.Context
import androidx.camera.view.LifecycleCameraController
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.model.Nickname

sealed class NicknameAddEvent {
    data class SaveNickname(val nickname: Nickname): NicknameAddEvent()
    data class NicknameChanged(val value: String): NicknameAddEvent()
    data class  TakePicture(val controller: LifecycleCameraController, val context: Context): NicknameAddEvent()
    data class CameraPermissionGranted(val granted: Boolean): NicknameAddEvent()
}