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

@Composable
fun DetailScreen(navController: NavController) {
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
            Text(text = "Go to Top News", color = Color.DarkGray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowDetailScreen() {
    DetailScreen(rememberNavController())
}