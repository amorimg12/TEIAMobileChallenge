package br.com.amorim.teiamobilechallenge.ui.navigation

class NavigationStructure {

    object RootGraph {
        const val ROOT = "home_graph"
    }

    object AppGraph {
        val home = HomeGraph
    }

    object HomeGraph {
        const val ROOT = "home"
        const val POSTS = "posts"
    }
}