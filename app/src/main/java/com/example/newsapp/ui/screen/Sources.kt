package com.example.newsapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.newsapp.network.NewsManager

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Sources(newsManager: NewsManager) {
    val items = listOf(
        "TechInt" to "tech_int",
        "SportsTalk" to "sTalks",
        "Business Insider" to "inside-biz",
        "Político" to "politico",
        "The Verger" to "the-verge"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "${newsManager.sourceName.value} • Source") },
                actions = {
                    var menuExpanded by remember { mutableStateOf(false) }
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = newsManager.sourceName.value
                        )
                    }

                    MaterialTheme(
                        shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(20.dp))
                    ) {
                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            items.forEach {
                                DropdownMenuItem(onClick = {
                                    newsManager.sourceName.value = it.second
                                    menuExpanded = false
                                }) {
                                    Text(text = it.first)
                                }
                            }
                        }
                    }
                }
            )
        }
    ) {

    }
}