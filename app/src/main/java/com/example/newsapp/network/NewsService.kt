package com.example.newsapp.network

import com.example.newsapp.models.TopNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("top-headlines")
    fun getArticles(@Query("country") country: String): Call<TopNewsResponse>

    @GET("top-headlines")
    fun getArticlesByCategory(@Query("category") category: String): Call<TopNewsResponse>
}