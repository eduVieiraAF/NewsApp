package com.example.newsapp.data

import com.example.newsapp.network.NewsManager

class Repository(private val manager: NewsManager) {
    suspend fun getArticles() = manager.getArticles("US")
    suspend fun getArticlesByCategory(category: String) = manager.getArticlesByCategory(category)
    suspend fun getArticlesBySource(source: String) = manager.getArticlesByCategory(source)
    suspend fun getArticlesByQuery(query: String) = manager.getArticlesByQuery(query)


}