package br.com.amorim.teiamobilechallenge.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.amorim.teiamobilechallenge.feature_nickname.presentation.nicknames.HomeScreen
import br.com.amorim.teiamobilechallenge.feature_posts.presentation.PostScreen
import br.com.amorim.teiamobilechallenge.feature_posts.presentation.PostViewModel
import br.com.amorim.teiamobilechallenge.ui.components.BottomBar
import br.com.amorim.teiamobilechallenge.ui.navigation.NavigationStructure.AppGraph
import br.com.amorim.teiamobilechallenge.ui.navigation.NavigationStructure.RootGraph

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            NavHost(
                navController = navController,
                route = RootGraph.ROOT,
                startDestination = AppGraph.home.ROOT
            ) {
                composable(route = AppGraph.home.ROOT) {
                    HomeScreen(navController = navController)
                }
                composable(route = AppGraph.home.POSTS) {
                    val viewModel = hiltViewModel<PostViewModel>()
                    val posts = viewModel.postPagingFlow.collectAsLazyPagingItems()
                    PostScreen(navController = navController, posts = posts)
                }
            }
        }
    }
}