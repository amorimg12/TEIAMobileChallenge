package br.com.amorim.teiamobilechallenge.feature_posts.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.amorim.teiamobilechallenge.feature_posts.domain.model.Post
import br.com.amorim.teiamobilechallenge.ui.theme.TEIAMobileChallengeTheme

@Composable
fun PostItem(
    post: Post,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Post: ${post.id}", style = MaterialTheme.typography.labelMedium)
            Text(text = "User Id: ${post.userId}", style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = post.body,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
fun PreviewPostItem() {
    TEIAMobileChallengeTheme {
        PostItem(
            post = Post(
                id = 1,
                userId = 1,
                title = "Title of the post",
                body = "Body of the post"
            )
        )
    }
}
