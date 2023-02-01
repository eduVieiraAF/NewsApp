package com.example.newsapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.newsapp.R
import com.example.newsapp.models.getAllArticleCategories
import com.example.newsapp.network.NewsManager

@Composable
fun Categories(onFetchCategory: (String) -> Unit = {}, newsManager: NewsManager) {
    val tabsItems = getAllArticleCategories()

    Row {
        LazyColumn {
            items(tabsItems.size) {
                val category = tabsItems[it]

                CategoryTab(
                    category = category.categoryName,
                    onFetchCategory = onFetchCategory,
                    isSelected = newsManager.selectedCategory.value == category
                )
            }
        }
    }
}

@Composable
fun CategoryTab(
    category: String,
    isSelected: Boolean = false,
    onFetchCategory: (String) -> Unit
) {
    val background =
        if (isSelected) colorResource(id = R.color.black) else colorResource(id = R.color.slate700)

    Surface(
        modifier = Modifier
            .padding(horizontal = 14.dp, vertical = 4.dp)
            .clickable {
                onFetchCategory(category)
            },
        shape = MaterialTheme.shapes.medium,
        color = background
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.h5,
            color = Color.White,
            modifier = Modifier
                .padding(18.dp)

        )
    }
}