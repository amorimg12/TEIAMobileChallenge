package br.com.amorim.teiamobilechallenge.feature_nickname.presentation.nicknameList.components

import android.text.format.DateFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.model.Nickname
import br.com.amorim.teiamobilechallenge.ui.theme.TEIAMobileChallengeTheme

@Composable
fun NicknameItem(modifier: Modifier = Modifier, nickname: Nickname){

    val context = LocalContext.current
    val dateFormat = DateFormat.getDateFormat(context)
    val hourFormat = DateFormat.getTimeFormat(context)
    TEIAMobileChallengeTheme {
        Card(modifier = modifier) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "PAT: ${nickname.pat}")
                Text(text = "Apelido: ${nickname.nickname}")
                Text(text = "Registrado em: ${dateFormat.format(nickname.timestamp)
                        + " " + hourFormat.format(nickname.timestamp)}")
            }
        }
    }
}

@Composable
@Preview
fun PreviewNicknameItem(){
    NicknameItem(nickname = Nickname(1, "Nickname", timestamp = 1515151L, id = null))
}