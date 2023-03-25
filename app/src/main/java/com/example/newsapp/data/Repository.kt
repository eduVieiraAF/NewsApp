package com.example.newsapp.data

import com.example.newsapp.network.NewsManager

class Repository(val manager: NewsManager) {
    suspend fun getArticles() = manager.getArticles("US")
    suspend fun getArticlesByCategory(category: String) = manager.getArticlesByCategory(category)


}