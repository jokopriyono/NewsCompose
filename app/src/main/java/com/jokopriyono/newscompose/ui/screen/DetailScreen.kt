package com.jokopriyono.newscompose.ui.screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jokopriyono.newscompose.MockData
import com.jokopriyono.newscompose.MockData.getTimeAgo
import com.jokopriyono.newscompose.R
import com.jokopriyono.newscompose.models.TopNewsArticle
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun DetailScreen(article: TopNewsArticle, scrollState: ScrollState, navController: NavController) {
    Scaffold(topBar = {
        DetailTopAppBar(onBackPressed = { navController.popBackStack() })
    }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Detail Screen", fontWeight = FontWeight.SemiBold)
            CoilImage(
                imageModel = article.urlToImage,
                contentScale = ContentScale.FillBounds,
                error = ImageBitmap.imageResource(R.drawable.breaking_news),
                placeHolder = ImageBitmap.imageResource(R.drawable.breaking_news)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoWithIcon(icon = Icons.Default.Edit, info = article.author ?: "Not Available")
                InfoWithIcon(
                    icon = Icons.Default.DateRange,
                    info = MockData.stringToDate(article.publishedAt!!).getTimeAgo()
                )
            }

            Text(text = article.title ?: "Not Available", fontWeight = FontWeight.Bold)
            Text(
                text = article.description ?: "Not Available",
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }

}

@Composable
fun DetailTopAppBar(onBackPressed: () -> Unit = {}) {
    TopAppBar(
        title = { Text(text = "Detail Screen", fontWeight = FontWeight.SemiBold) },
        navigationIcon = {
            IconButton(
                onClick = { onBackPressed() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }
        })
}

@Composable
fun InfoWithIcon(icon: ImageVector, info: String) {
    Row {
        Icon(
            imageVector = icon,
            contentDescription = info,
            modifier = Modifier.padding(end = 8.dp),
            colorResource(
                id = R.color.purple_500
            )
        )
        Text(text = info)
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(
        TopNewsArticle(
            author = "Joko Priyono",
            title = "Lorem Ipsum!!",
            description = "Lorem Ipsum Dolor Sit Amet",
            publishedAt = "2022-11-01T04:42:40Z"
        ), rememberScrollState(), rememberNavController()
    )
}