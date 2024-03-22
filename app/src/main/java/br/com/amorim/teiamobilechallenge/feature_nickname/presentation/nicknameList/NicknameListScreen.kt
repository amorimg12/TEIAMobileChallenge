package br.com.amorim.teiamobilechallenge.feature_nickname.presentation.nicknameList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.amorim.teiamobilechallenge.feature_nickname.presentation.nicknameList.components.NicknameItem
import br.com.amorim.teiamobilechallenge.ui.theme.TEIAMobileChallengeTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NicknameListScreen(
    viewModel: NickNameListViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(NicknameListEvent.LoadNicknames)
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                NickNameListViewModel.UiEvent.LoadedNickNames -> {
                    snackbarHostState.showSnackbar(
                        message = "Apelidos carregados"
                    )
                }

                is NickNameListViewModel.UiEvent.ShowSnackbar -> {
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
        ) { padding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(padding)) {
                if (viewModel.isLoading.value) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {
                    LazyColumn(
                    ) {
                        items(viewModel.nicknamesList.value) { nickname ->
                            NicknameItem(
                                nickname = nickname,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewNicknameListScreen() {
    NicknameListScreen()
}