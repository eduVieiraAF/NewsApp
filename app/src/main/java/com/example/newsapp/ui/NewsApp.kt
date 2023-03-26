package com.example.newsapp.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.components.BottomMenu
import com.example.newsapp.models.TopNewsArticles
import com.example.newsapp.ui.screen.*

@Composable
fun NewsApp(mainViewModel: MainViewModel) {
    val scrollState = rememberScrollState()
    val navController = rememberNavController()
    MainScreen(
        navController = navController,
        scrollState = scrollState,
        mainViewModel = mainViewModel
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    scrollState: ScrollState,
    mainViewModel: MainViewModel
) {
    Scaffold(bottomBar = { BottomMenu(navController = navController) }) {
        Navigation(navController, scrollState, paddingValues = it, viewModel = mainViewModel)
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    paddingValues: PaddingValues,
    viewModel: MainViewModel
) {
    val articles = mutableListOf(TopNewsArticles())
    val topArticles = viewModel.newsResponse.collectAsState().value.articles
    articles.addAll(topArticles ?: listOf())

    Log.d("News", "$articles")

    NavHost(
        navController = navController,
        startDestination = BottomMenuScreen.TopNews.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        val queryState = mutableStateOf(viewModel.query.value)

        bottomNavigation(navController, articles, queryState, viewModel)

        composable(
            "Detail/{index}",
            arguments = listOf(navArgument("index") { type = NavType.IntType })
        ) { navBackStackEntry ->
            val index = navBackStackEntry.arguments?.getInt("index")
            index?.let {
                if (queryState.value != "") {
                    articles.clear()
                    articles
                        .addAll(viewModel.getArticlesByQuery.value.articles ?: listOf())
                } else {
                    articles.clear()
                    articles
                        .addAll(viewModel.newsResponse.value.articles ?: listOf())
                }

                val article = articles[index]

                DetailScreen(scrollState, article, navController)
            }
        }
    }
}

fun NavGraphBuilder.bottomNavigation(
    navController: NavController,
    articles: List<TopNewsArticles>,
    query: MutableState<String>,
    viewModel: MainViewModel
) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(navController = navController, articles, query, viewModel)
    }

    composable(BottomMenuScreen.Categories.route) {
        viewModel.getArticleByCategory("business")
        viewModel.onSelectedCategoryChanged("business")

        Categories(
            viewModel = viewModel,
            onFetchCategory = {
                viewModel.onSelectedCategoryChanged(it)
                viewModel.getArticleByCategory(it)
            }
        )
    }

    composable(BottomMenuScreen.Sources.route) {
        Sources(viewModel)
    }
}