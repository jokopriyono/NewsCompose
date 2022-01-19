package com.jokopriyono.newscompose.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jokopriyono.newscompose.BottomMenuScreen
import com.jokopriyono.newscompose.components.BottomMenu
import com.jokopriyono.newscompose.models.TopNewsArticle
import com.jokopriyono.newscompose.network.NewsManager
import com.jokopriyono.newscompose.ui.screen.Categories
import com.jokopriyono.newscompose.ui.screen.DetailScreen
import com.jokopriyono.newscompose.ui.screen.Sources
import com.jokopriyono.newscompose.ui.screen.TopNews

@Composable
fun NewsApp() {
    val scrollState = rememberScrollState()
    val navController = rememberNavController()
    MainScreen(navController = navController, scrollState)
}

@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState) {
    Scaffold(bottomBar = {
        BottomMenu(navController = navController)
    }) {
        Navigation(navController = navController, scrollState = scrollState, paddingValues = it)
    }
}


@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    newsManager: NewsManager = NewsManager(),
    paddingValues: PaddingValues
) {
    val articles = newsManager.newsResponse.value.articles
    articles?.let {
        NavHost(
            navController = navController,
            startDestination = BottomMenuScreen.TopNews.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            bottomNavigation(
                navController = navController,
                articles = it,
                newsManager = newsManager
            )
            composable("Detail/{index}",
                arguments = listOf(
                    navArgument("index") { type = NavType.IntType }
                )) { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getInt("index")
                id?.let {
                    val article = articles[it]
                    DetailScreen(article, scrollState, navController)
                }
            }
        }
    }
}

fun NavGraphBuilder.bottomNavigation(
    navController: NavController,
    articles: List<TopNewsArticle>,
    newsManager: NewsManager
) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(navController = navController, articles = articles)
    }
    composable(BottomMenuScreen.Categories.route) {
        Categories(newsManager = newsManager, onFetchCategory = {
            newsManager.onSelectedCategoryChanged(it)
        })
    }
    composable(BottomMenuScreen.Sources.route) {
        Sources()
    }
}