package br.com.amorim.teiamobilechallenge.feature_nickname.presentation.nicknames

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.model.Nickname
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.repository.NicknameRepository
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.use_case.AddNickName
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.use_case.NicknameUseCases
import br.com.amorim.teiamobilechallenge.ui.theme.TEIAMobileChallengeTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: NicknamesViewModel = hiltViewModel(),
    pat: Int,
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val nicknameState = viewModel.nickname.value
    val context = LocalContext.current
    val errorState = viewModel.isError
    val errorMessageState = viewModel.errorText
    val patError = viewModel.patError

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
                    if (pat != 0) {
                        Text(text = "PAT: $pat", modifier = Modifier.padding(start = 16.dp))
                    }
                    else{
                        var patColor = MaterialTheme.colorScheme.primary
                        if(patError.value) {
                            patColor = MaterialTheme.colorScheme.error
                        }
                        Text(text = "PAT: Não há um PAT válido",
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
                            viewModel.onEvent(
                                NicknamesEvent.SaveNickname(
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
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(
        rememberNavController(), pat = 1,
        viewModel = NicknamesViewModel(
            NicknameUseCases(AddNickName(FakeNicknamesRepository()))
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