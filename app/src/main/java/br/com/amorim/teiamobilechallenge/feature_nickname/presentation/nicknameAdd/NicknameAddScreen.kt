package br.com.amorim.teiamobilechallenge.feature_nickname.presentation.nicknameAdd

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.amorim.teiamobilechallenge.R
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.model.Nickname
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.repository.NicknameRepository
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.use_case.AddNickName
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.use_case.GetNicknames
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.use_case.NicknameUseCases
import br.com.amorim.teiamobilechallenge.ui.core.components.CameraPreview
import br.com.amorim.teiamobilechallenge.ui.theme.TEIAMobileChallengeTheme
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: NicknameAddViewModel = hiltViewModel(),
    pat: Int,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val nicknameState = viewModel.nickname.value
    val context = LocalContext.current
    val errorState = viewModel.isError
    val errorMessageState = viewModel.errorText
    val patError = viewModel.patError

    val density = LocalDensity.current.density
    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }

    var camPreviewDialog by remember { mutableStateOf(false) }
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE
            )
        }
    }


    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            viewModel.onEvent(NicknameAddEvent.CameraPermissionGranted(true))
            camPreviewDialog = true
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            viewModel.onEvent(NicknameAddEvent.CameraPermissionGranted(false))
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is NicknameAddViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is NicknameAddViewModel.UiEvent.SavedNickname -> {
                    snackbarHostState.showSnackbar(
                        message = context.getString(R.string.nickname_saved)
                    )
                }

                is NicknameAddViewModel.UiEvent.PictureError -> {
                    camPreviewDialog = false
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is NicknameAddViewModel.UiEvent.PictureTaken -> {
                    camPreviewDialog = false
                    capturedBitmap = viewModel.bitmap.value
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    TEIAMobileChallengeTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.Center
                ) {
                    if (pat != 0) {
                        Text(text = "PAT: $pat", modifier = Modifier.padding(start = 16.dp))
                    } else {
                        var patColor = MaterialTheme.colorScheme.primary
                        if (patError.value) {
                            patColor = MaterialTheme.colorScheme.error
                        }
                        Text(
                            text = "PAT: Não há um PAT válido",
                            modifier = Modifier.padding(start = 16.dp),
                            color = patColor
                        )
                    }
                    OutlinedTextField(
                        label = { Text(stringResource(R.string.label_nickname)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp),
                        value = nicknameState,
                        onValueChange = {
                            viewModel.onEvent(NicknameAddEvent.NicknameChanged(it))
                        },
                        isError = errorState.value,
                        supportingText = { Text(errorMessageState.value) }
                    )

                    AsyncImage(
                        modifier = Modifier
                            .clickable {
                                // Request camera permission
                                if (ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.CAMERA
                                    ) == PackageManager.PERMISSION_GRANTED
                                ) {
                                    camPreviewDialog = true
                                } else {
                                    permissionLauncher.launch(Manifest.permission.CAMERA)
                                }
                            }
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp)
                            .size(100.dp),
                        model = capturedBitmap,
                        error = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "photo")

                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            viewModel.onEvent(
                                NicknameAddEvent.SaveNickname(
                                    Nickname(
                                        pat = pat,
                                        nickname = nicknameState,
                                        timestamp = System.currentTimeMillis()
                                    )
                                )
                            )
                        }) {
                        Text(text = stringResource(R.string.button_home_save))
                    }
                }
            })
        if (camPreviewDialog) {
            BasicAlertDialog(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .padding(16.dp),
                properties = DialogProperties(
                    usePlatformDefaultWidth = false,
                    dismissOnBackPress = true
                ),
                onDismissRequest = {
                    camPreviewDialog = false
                },
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        CameraPreview(
                            controller = controller,
                            modifier = Modifier
                                .fillMaxSize()
                        )

                        IconButton(
                            onClick = {
                                controller.cameraSelector =
                                    if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                                        CameraSelector.DEFAULT_FRONT_CAMERA
                                    } else CameraSelector.DEFAULT_BACK_CAMERA
                            },
                            modifier = Modifier
                                .offset(16.dp, 16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Switch camera"
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            IconButton(
                                onClick = {
                                    viewModel.onEvent(
                                        NicknameAddEvent.TakePicture(
                                            controller,
                                            context
                                        )
                                    )
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Take photo"
                                )
                            }
                        }
                    }
                })
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(
        rememberNavController(), pat = 1,
        viewModel = NicknameAddViewModel(
            NicknameUseCases(
                AddNickName(FakeNicknamesRepository()),
                GetNicknames(FakeNicknamesRepository())
            )
        )
    )
}

class FakeNicknamesRepository : NicknameRepository {
    override fun getNicknames(): Flow<List<Nickname>> {
        return flowOf(emptyList<Nickname>())
    }

    override suspend fun getNicknameById(id: Int): Nickname? {
        return null
    }

    override suspend fun getNicknameByNickname(nickname: String): Nickname? {
        return null
    }

    override suspend fun insertNickname(nickname: Nickname) {
        // do nothing
    }

    override suspend fun deleteNickname(nickname: Nickname) {
        // do nothing
    }
}