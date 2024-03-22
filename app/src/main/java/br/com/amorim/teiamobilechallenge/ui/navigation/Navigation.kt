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
import androidx.navigation.navDeepLink
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.amorim.teiamobilechallenge.feature_nickname.presentation.nicknameAdd.HomeScreen
import br.com.amorim.teiamobilechallenge.feature_nickname.presentation.nicknameList.NicknameListScreen
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
                composable(
                    deepLinks = listOf(navDeepLink {
                        uriPattern = "caixa.gov.br://meupat?pat={pat}"
                    }),
                    route = AppGraph.home.ROOT
                ) { backStackEntry ->
                    val pat = backStackEntry.arguments?.getString("pat")?.toInt() ?: 0
                    HomeScreen(navController = navController, pat = pat)
                }
                composable(route = AppGraph.home.POSTS) {
                    val viewModel = hiltViewModel<PostViewModel>()
                    val posts = viewModel.postPagingFlow.collectAsLazyPagingItems()
                    PostScreen(navController = navController, posts = posts)
                }
                composable(route = AppGraph.home.NickList) {
                    NicknameListScreen()
                }
            }
        }
    }
}