package com.example.newsapp.ui

import android.annotation.SuppressLint
import android.util.Log
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
import com.example.newsapp.components.BottomMenu
import com.example.newsapp.models.TopNewsArticles
import com.example.newsapp.network.NewsManager
import com.example.newsapp.ui.screen.*

@Composable
fun NewsApp() {
    val scrollState = rememberScrollState()
    val navController = rememberNavController()
    MainScreen(navController = navController, scrollState = scrollState)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState) {
    Scaffold(bottomBar = { BottomMenu(navController = navController) }) {
        Navigation(navController, scrollState, paddingValues = it)
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    newsManager: NewsManager = NewsManager(),
    paddingValues: PaddingValues
) {
    val articles = mutableListOf(TopNewsArticles())
    articles
        .addAll(newsManager.getArticleByQuery.value.articles ?: listOf(TopNewsArticles()))

    Log.d("News", "$articles")

    articles.let {
        NavHost(
            navController = navController,
            startDestination = BottomMenuScreen.TopNews.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            bottomNavigation(navController, articles, newsManager)

            composable(
                "Detail/{index}",
                arguments = listOf(navArgument("index") { type = NavType.IntType })
            ) { navBackStackEntry ->
                val index = navBackStackEntry.arguments?.getInt("index")
                index?.let {
                    if (newsManager.query.value.isNotEmpty()) {
                        articles.clear()
                        articles
                            .addAll(newsManager.getArticleByQuery.value.articles ?: listOf())
                    } else {
                        articles.clear()
                        articles
                            .addAll(newsManager.getArticleByQuery.value.articles ?: listOf())
                    }

                    val article = articles[index]

                    DetailScreen(scrollState, article, navController)
                }
            }
        }
    }
}

fun NavGraphBuilder.bottomNavigation(
    navController: NavController,
    articles: List<TopNewsArticles>,
    newsManager: NewsManager
) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(navController = navController, articles, newsManager.query, newsManager)
    }

    composable(BottomMenuScreen.Categories.route) {
        Categories(
            newsManager = newsManager,
            onFetchCategory = {
                newsManager.getArticlesByCategory("business")
                newsManager.onSelectedCategoryChanged("business")

                newsManager.onSelectedCategoryChanged(it)
                newsManager.getArticlesByCategory(it)
            }
        )
    }

    composable(BottomMenuScreen.Sources.route) {
        Sources(newsManager)
    }
}