package com.example.newsapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.MockData
import com.example.newsapp.MockData.getTimeAgo
import com.example.newsapp.R
import com.example.newsapp.models.TopNewsArticles
import com.example.newsapp.models.getAllArticleCategories
import com.example.newsapp.network.NewsManager
import com.example.newsapp.ui.MainViewModel
import com.example.newsapp.ui.theme.Slate700
import com.skydoves.landscapist.coil.CoilImage

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Categories(onFetchCategory: (String) -> Unit = {}, viewModel: MainViewModel) {
    val tabsItems = getAllArticleCategories()

    Column {
        LazyRow {
            items(tabsItems.size) {
                val category = tabsItems[it]

                CategoryTab(
                    category = category.categoryName,
                    onFetchCategory = onFetchCategory,
                    isSelected = viewModel.selectedCategory.value == category
                )
            }
        }

        ArticleContent(articles = viewModel.getArticleByCategory.value.articles ?: listOf())
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
            .padding(horizontal = 4.dp, vertical = 12.dp)
            .clickable {
                onFetchCategory(category)
            },
        shape = MaterialTheme.shapes.medium,
        color = background
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.body2,
            color = Color.White,
            modifier = Modifier
                .padding(18.dp)

        )
    }
}

@Composable
fun ArticleContent(
    articles: List<TopNewsArticles>,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(articles) { article ->
            Card(
                modifier.padding(8.dp),
                border = BorderStroke(2.dp, color = Slate700)
            ) {
                Row(
                    modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    CoilImage(
                        imageModel = article.urlToImage,
                        modifier = Modifier.size(100.dp),
                        placeHolder = painterResource(id = R.drawable.loading),
                        error = painterResource(id = R.drawable.breaking_news)
                    )

                    Column(
                        modifier.padding(8.dp)
                    ) {
                        Text(
                            text = article.title!! ?: "Title missing",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )

                        Row(
                            modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = article.author ?: "Author not mentioned",
                                color = Slate700,
                            )

                            Text(
                                text = MockData.stringToDate(
                                    article.publishedAt ?: "2022-11-04T04:42:40Z"
                                ).getTimeAgo(),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowArticleContent() {
    ArticleContent(
        articles = listOf(
            TopNewsArticles(
                author = "Someone who wrote it",
                title = "This is some title",
                description = "Describes in detail whatever the title reads and informs the reader",
                publishedAt = "2022-11-04T04:42:40Z"
            )
        )
    )
}
