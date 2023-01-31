package com.example.newsapp.network

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.newsapp.models.TopNewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsManager {
    private val _newsResponse = mutableStateOf(TopNewsResponse())
    val newsResponse: State<TopNewsResponse>
        @Composable get() = remember {
            _newsResponse
        }

    init {
        getArticles()
    }

    private fun getArticles() {
        val service = Api.retrofitService.getArticles("us", Api.API_KEY)
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
}