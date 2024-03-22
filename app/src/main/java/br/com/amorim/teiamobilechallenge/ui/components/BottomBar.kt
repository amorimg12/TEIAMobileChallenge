package br.com.amorim.teiamobilechallenge.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import br.com.amorim.teiamobilechallenge.R
import br.com.amorim.teiamobilechallenge.ui.navigation.NavigationStructure
import androidx.navigation.compose.currentBackStackEntryAsState

//****************************************************************
//  Configuração dos itens da barra de navegação
//****************************************************************
sealed class BottomBarItemConfig(
    val route: String,
    @StringRes val title: Int,
    val icon: ImageVector
) {
    data object Home : BottomBarItemConfig(
        route = NavigationStructure.AppGraph.home.ROOT,
        icon = Icons.Default.Home,
        title = R.string.home
    )
    data object Posts : BottomBarItemConfig(
        route = NavigationStructure.AppGraph.home.POSTS,
        icon = Icons.AutoMirrored.Filled.List,
        title =R.string.posts
    )
}

// ****************************************************************
// | Lista de todas a configurações de rotas da barra de navegação  |
// ****************************************************************
object BottomBarConfigList {
    val all = listOf(
        BottomBarItemConfig.Home,
        BottomBarItemConfig.Posts
    )
}

@Composable
fun BottomBar(navController: NavHostController) {
    // Destinação atual
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showNavigationItems = BottomBarConfigList.all.any { it.route == currentDestination?.route }



    if (showNavigationItems) {
        NavigationBar {
            // | Exibir os itens da barra de navegação   |
            BottomBarConfigList.all.forEach { itemConfig ->
                AddItem(
                    itemConfig = itemConfig,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    itemConfig: BottomBarItemConfig,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        label = {
            Text(text = stringResource(itemConfig.title))
        },
        icon = {
            Icon(
                imageVector = itemConfig.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any { it.route == itemConfig.route } == true,
        onClick = {
            navController.navigate(itemConfig.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}