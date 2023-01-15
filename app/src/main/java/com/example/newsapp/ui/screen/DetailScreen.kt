package com.example.newsapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.NewsData
import com.example.newsapp.R

@Composable
fun DetailScreen(navController: NavController, newsData: NewsData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Detail Screen",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )

        OutlinedButton(
            onClick = {
                // ? navController.navigate("TopNews")
                navController.popBackStack()
            },
        ) {
            Text(text = "Go to Top News + ${newsData.author}", color = Color.DarkGray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowDetailScreen() {
    DetailScreen(
        rememberNavController(), NewsData(
            4,
            R.drawable.michael,
            author = "Mike Florio",
            title = "Aaron Rodgers violated COVID protocol by doing maskless indoor press conferences - NBC Sports",
            description = "Packers quarterback Aaron Rodgers has been conducting in-person press conferences in the Green Bay facility without wearing a mask. Because he was secretly unvaccinated, Rodgers violated the rules.",
            publishedAt = "2021-11-04T03:21:00Z"
        )
    )
}