package com.example.newsapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
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
import com.example.newsapp.R
import com.example.newsapp.models.TopNewsArticle
import com.example.newsapp.ui.theme.Slate500
import com.example.newsapp.ui.theme.Slate700
import com.skydoves.landscapist.coil.CoilImage

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen(scrollState: ScrollState, article: TopNewsArticle, navController: NavController) {
    Scaffold(
        topBar = { DetailTopBar(onBackPressed = { navController.popBackStack() }) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = article.title ?: "|| No title provided ||",
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 18.sp,
                color = Slate700,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 6.dp)
            )

            CoilImage(
                imageModel = article.urlToImage,
                contentScale = ContentScale.Crop,
                error = ImageBitmap.imageResource(R.drawable.breaking_news),
                placeHolder = ImageBitmap.imageResource(R.drawable.breaking_news)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoWithIcon(
                    icon = Icons.Default.Edit,
                    info = article.author ?: "Not Available"
                )
                InfoWithIcon(
                    icon = Icons.Default.DateRange,
                    info = MockData.stringToDate(article.publishedAt!!).getTimeAgo()
                )
            }

            Divider(color = Slate700, modifier = Modifier.padding(bottom = 16.dp))

            Text(
                text = article.description ?: "Article Missing",
                textAlign = TextAlign.Justify,
                color = Slate700,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            )
        }
    }
}

@Composable
fun DetailTopBar(onBackPressed: () -> Unit = {}) {
    TopAppBar(
        title = { Text(text = "News Article", fontWeight = FontWeight.SemiBold) },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Back button"
                )
            }
        }
    )
}

@Composable
fun InfoWithIcon(icon: ImageVector, info: String) {
    Row {
        Icon(
            icon,
            contentDescription = "Author",
            modifier = Modifier
                .padding(8.dp),
            colorResource(id = R.color.slate500)
        )

        Text(
            text = info,
            color = Slate500,
            fontSize = 14.sp,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShowDetailScreen() {
    DetailScreen(
        rememberScrollState(),
        TopNewsArticle(
            author = "Namita Singh",
            title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
            description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
            publishedAt = "2021-11-04T04:42:40Z"
        ),
        rememberNavController()
    )
}