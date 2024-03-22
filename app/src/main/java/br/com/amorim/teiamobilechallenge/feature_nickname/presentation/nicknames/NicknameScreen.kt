package br.com.amorim.teiamobilechallenge.feature_nickname.presentation.nicknames

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.amorim.teiamobilechallenge.R
import br.com.amorim.teiamobilechallenge.ui.theme.TEIAMobileChallengeTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: NicknamesViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val nicknameState = viewModel.nickname.value
    val context = LocalContext.current
    val errorState = viewModel.isError
    val errorMessageState = viewModel.errorText

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is NicknamesViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is NicknamesViewModel.UiEvent.SavedNickname -> {
                    snackbarHostState.showSnackbar(
                        message = context.getString(R.string.nickname_saved)
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
                    OutlinedTextField(
                        label = { Text(stringResource(R.string.label_nickname)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        value = nicknameState,
                        onValueChange = {
                            viewModel.onEvent(NicknamesEvent.NicknameChanged(it))
                        },
                        isError = errorState.value,
                        supportingText = { Text(errorMessageState.value) }
                    )

                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            viewModel.onEvent(NicknamesEvent.SaveNickname)
                        }) {
                        Text(text = stringResource(R.string.button_home_save))
                    }
                }
            })
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}