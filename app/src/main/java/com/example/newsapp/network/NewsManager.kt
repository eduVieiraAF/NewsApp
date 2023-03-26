package com.example.newsapp.network

import androidx.compose.runtime.*
import com.example.newsapp.models.ArticleCategory
import com.example.newsapp.models.TopNewsResponse
import com.example.newsapp.models.getArticleCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class NewsManager(private val service: NewsService) {
    private val _newsResponse = mutableStateOf(TopNewsResponse())
    val newsResponse: State<TopNewsResponse>
        @Composable get() = remember {
            _newsResponse
        }

    val sourceName = mutableStateOf("abc-news")

    private val _getArticleBySource = mutableStateOf(TopNewsResponse())
    val getArticleBySource: MutableState<TopNewsResponse>
        @Composable get() = remember {
            _getArticleBySource
        }


    val query = mutableStateOf("")

    private val _getArticleByQuery = mutableStateOf(TopNewsResponse())
    val getArticleByQuery: MutableState<TopNewsResponse>
        @Composable get() = remember {
            _getArticleByQuery
        }

    val selectedCategory: MutableState<ArticleCategory?> = mutableStateOf(null)

    init {

    }

    suspend fun getArticles(country: String): TopNewsResponse = withContext(Dispatchers.IO) {
        service.getArticles(country)
    }

    suspend fun getArticlesByCategory(category: String): TopNewsResponse =
        withContext(Dispatchers.IO) {
            service.getArticlesByCategory(category)
        }

    suspend fun getArticlesBySource(): TopNewsResponse = withContext(Dispatchers.IO) {
        service.getArticlesBySources(sourceName.value)
    }

    suspend fun getArticlesByQuery(query: String): TopNewsResponse = withContext(Dispatchers.IO) {
        service.getArticlesByQuery(query)
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getArticleCategory(category)

        selectedCategory.value = newCategory
    }
}