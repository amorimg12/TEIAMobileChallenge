package br.com.amorim.teiamobilechallenge.feature_posts.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import br.com.amorim.teiamobilechallenge.feature_posts.domain.model.Post
import br.com.amorim.teiamobilechallenge.feature_posts.presentation.components.PostItem

@Composable
fun PostScreen(
    navController: NavHostController,
    posts: LazyPagingItems<Post>
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = posts.loadState) {
        if(posts.loadState.refresh is LoadState.Error) {
            Toast.makeText(context,
                "Error: ${(posts.loadState.refresh as LoadState.Error).error.message.toString()}"
                , Toast.LENGTH_LONG).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()){
        if(posts.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ){
                 items(posts.itemCount,
                     key = posts.itemKey{it.id}) { index ->
                     val item = posts[index]
                     item?.let {
                         PostItem(post = item)
                     }
                 }
                item{
                    if(posts.loadState.append is LoadState.Loading) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}