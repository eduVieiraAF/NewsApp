package com.example.newsapp.network

import android.util.Log
import androidx.compose.runtime.*
import com.example.newsapp.models.ArticleCategory
import com.example.newsapp.models.TopNewsResponse
import com.example.newsapp.models.getArticleCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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

    suspend fun getArticlesByCategory(category: String): TopNewsResponse = withContext(Dispatchers.IO) {
        service.getArticlesByCategory(category)
    }

    fun getArticlesBySource() {
        val service = Api.retrofitService.getArticlesBySources(sourceName.value)
        service.enqueue(object : Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if (response.isSuccessful) {
                    _getArticleBySource.value = response.body()!!
                    Log.d("Source", "${_getArticleBySource.value}")
                }
                else Log.e("source", "${response.code()}")
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.e("ERROR", "${t.printStackTrace()}")
            }
        })
    }

    fun getArticlesByQuery(query: String) {
        val service = Api.retrofitService.getArticlesByQuery(query)
        service.enqueue(object : Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if (response.isSuccessful) {
                    _getArticleByQuery.value = response.body()!!
                    Log.d("searchQuery", "${_getArticleByQuery.value}")
                }
                else Log.e("searchQuery", "${response.code()}")
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.e("QUERY_ERROR", "${t.printStackTrace()}")
            }
        })
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getArticleCategory(category)

        selectedCategory.value = newCategory
    }
}