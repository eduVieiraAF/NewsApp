package com.example.newsapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.newsapp.MockData
import com.example.newsapp.MockData.getTimeAgo
import com.example.newsapp.R
import com.example.newsapp.components.SearchBar
import com.example.newsapp.models.TopNewsArticles
import com.example.newsapp.network.NewsManager
import com.example.newsapp.ui.theme.Slate500
import com.example.newsapp.ui.theme.Slate700
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun TopNews(
    navController: NavController,
    articles: List<TopNewsArticles>,
    query: MutableState<String>,
    newsManager: NewsManager
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /* Text(
            text = "Top News",
            fontWeight = FontWeight.Bold,
            fontSize = 38.sp,
            color = Slate700,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        ) */

        SearchBar(query, newsManager)

        val resultList = mutableListOf<TopNewsArticles>()
        val searchedText = query.value

        if (searchedText.isNotEmpty()) {
            resultList.addAll(newsManager.getArticleByQuery.value.articles ?: articles)
        } else resultList.addAll(articles)

        Divider(color = Slate500, modifier = Modifier.padding(top = 8.dp))
        Divider(color = Slate500, modifier = Modifier.padding(top = 2.dp, bottom = 2.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(resultList.size) { index ->
                TopNewsItem(
                    article = resultList[index],
                    onNewsClicked = {
                        navController.navigate("Detail/${index}")
                    }
                )
            }
        }
    }
}

@Composable
fun TopNewsItem(article: TopNewsArticles, onNewsClicked: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .height(230.dp)
            .padding(8.dp)
            .fillMaxSize()
            .clickable { onNewsClicked() }
    ) {
        CoilImage(
            imageModel = article.urlToImage,
            contentScale = ContentScale.Crop,
            error = ImageBitmap.imageResource(R.drawable.breaking_news),
            placeHolder = ImageBitmap.imageResource(R.drawable.loading)
        )

    }

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(18.dp)
            .clickable { onNewsClicked() },
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        article.publishedAt?.let {
            Text(
                text = MockData.stringToDate(article.publishedAt).getTimeAgo(),
                color = Slate700,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
            )
        }
        article.title?.let {
            Text(
                text = article.title,
                color = Slate700,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
            )
        }

        Divider(color = Slate500, modifier = Modifier.padding(vertical = 2.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun ShowTopNews() {
    TopNewsItem(
        TopNewsArticles(
            author = "Namita Singh",
            title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
            description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
            publishedAt = "2021-11-04T04:42:40Z"
        )
    )
}
