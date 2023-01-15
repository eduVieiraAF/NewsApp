package com.example.newsapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
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
        
        OutlinedButton(onClick = {
            navController.navigate("Detail")
        },
        ) {
            Text(text = "Go to Detail Screen", color = Color.DarkGray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowTopNews() {
    TopNews(rememberNavController())
}