package com.example.newsapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.newsapp.MockData.getTimeAgo
import com.example.newsapp.NewsData
import com.example.newsapp.ui.theme.Slate500
import com.example.newsapp.ui.theme.Slate700

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
            fontSize = 38.sp,
            color = Slate700,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )

        Divider(color = Slate500, modifier = Modifier.padding(top = 8.dp))
        Divider(color = Slate500, modifier = Modifier.padding(top = 2.dp, bottom = 2.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(MockData.topNewsList) { newsData ->
                TopNewsItem(
                    newsData = newsData,
                    onNewsClicked = {
                        navController.navigate("Detail/${newsData.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun TopNewsItem(newsData: NewsData, onNewsClicked: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .height(130.dp)
            .padding(8.dp)
            .clickable { onNewsClicked() }
    ) {
        Image(
            painter = painterResource(id = newsData.image),
            contentDescription = newsData.title,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .padding(horizontal = 70.dp)
                .border(1.dp, color = Slate700)
        )
    }

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(18.dp)
            .clickable { onNewsClicked() },
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Text(
            text = newsData.title,
            color = Slate700,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        )

        Text(
            text = "|| ${MockData.stringToDate(newsData.publishedAt).getTimeAgo()} ||",
            color = Slate700,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(21.dp)
                .fillMaxWidth(),
        )

        Divider(color = Slate500, modifier = Modifier.padding(vertical = 6.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun ShowTopNews() {
    TopNews(rememberNavController())
}
