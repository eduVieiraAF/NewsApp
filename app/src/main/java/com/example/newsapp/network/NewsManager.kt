package com.example.newsapp.network

import android.util.Log
import androidx.compose.runtime.*
import com.example.newsapp.models.ArticleCategory
import com.example.newsapp.models.TopNewsResponse
import com.example.newsapp.models.getArticleCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsManager {
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

    private val _getArticleByCategory = mutableStateOf(TopNewsResponse())
    val getArticleByCategory: MutableState<TopNewsResponse>
        @Composable get() = remember {
            _getArticleByCategory
        }

    val query = mutableStateOf("")

    private val _getArticleByQuery = mutableStateOf(TopNewsResponse())
    val getArticleByQuery: MutableState<TopNewsResponse>
        @Composable get() = remember {
            _getArticleByQuery
        }

    val selectedCategory: MutableState<ArticleCategory?> = mutableStateOf(null)

    init {
        getArticles()
    }

    private fun getArticles() {
        val service = Api.retrofitService.getArticles("us")
        service.enqueue(object : Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if (response.isSuccessful) _newsResponse.value = response.body()!!
                else Log.e("NewsE", "${_newsResponse.value}")
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.e("ERROR", "${t.printStackTrace()}")
            }
        })
    }

    fun getArticlesByCategory(category: String) {
        val service = Api.retrofitService.getArticlesByCategory(category)
        service.enqueue(object : Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if (response.isSuccessful) _getArticleByCategory.value = response.body()!!
                else Log.e("category", "${response.code()}")
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.e("ERROR", "${t.printStackTrace()}")
            }
        })
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