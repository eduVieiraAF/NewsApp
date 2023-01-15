package com.example.newsapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.MockData
import com.example.newsapp.NewsData
import com.example.newsapp.R


@Composable
fun TopNews(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Top News",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(MockData.topNewsList) { newsData ->
                TopNewsItem(newsData = newsData)
            }
        }
    }
}

@Composable
fun TopNewsItem(newsData: NewsData) {
    Box(
        modifier = Modifier
            .height(230.dp)
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = newsData.image),
            contentDescription = newsData.title,
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = newsData.publishedAt, color
                = Color.White,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = newsData.title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowTopNews() {
    TopNews(rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun ShowNewsItem() {
    TopNewsItem(
        newsData = NewsData(
            7,
            R.drawable.thomas,
            author = "Thomas Barrabi",
            title = "Sen. Murkowski slams Dems over 'show votes' on federal election bills - Fox News",
            description = "Sen. Lisa Murkowski, R-Alaska, slammed Senate Democrats for pursuing “show votes” on federal election bills on Wednesday as Republicans used the filibuster to block consideration the John Lewis Voting Rights Advancement Act.",
            publishedAt = "2021-11-04T01:57:36Z"
        )
    )
}