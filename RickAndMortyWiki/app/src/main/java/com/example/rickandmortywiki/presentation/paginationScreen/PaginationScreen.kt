package com.example.rickandmortywiki.presentation.paginationScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyColumn
import com.example.rickandmortywiki.api.Character
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Card
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue


@Composable
fun PaginationScreen(
    viewModel: PaginationViewModel,
    onItemClick: (Character) -> Unit,
) {
    val pagingData: LazyPagingItems<Character> = viewModel.pagingData.collectAsLazyPagingItems()
    val throwable by viewModel.throwable.observeAsState()
    val context = LocalContext.current
    var screenKey by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2b2d30))
    ) {
        if (throwable != null) {
            Button(
                onClick = {
                    screenKey++
                    viewModel.setThrowable()
                },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
            ) {
                Text(
                    text = "ПЕРЕЗАГРУЗИТЬ",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            LaunchedEffect(throwable) {
                Toast.makeText(
                    context,
                    "Ошибка: ${throwable?.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        else{
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(pagingData.itemCount) { index ->
                val character = pagingData[index]
                if (character != null) {
                    CharacterItem(character = character, onItemClick = onItemClick)
                }
            }
        }
    }}
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterItem(character: Character, onItemClick: (Character) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(Color(0xFF424242), shape = RoundedCornerShape(16.dp))
            .clickable { onItemClick(character) }
    ) {
        Card(
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.CenterStart),
            shape = RoundedCornerShape(16.dp)
        ) {
            GlideImage(
                model = character.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 160.dp)
                .align(Alignment.CenterStart)
        ) {
            Text(
                text = character.name,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            shape = CircleShape,
                            color = if (character.status == "Alive") Color.Green else Color.Red
                        )
                )
                Text(
                    text = character.status,
                    color = Color.White,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(start = 7.dp)
                )
                Text(
                    text = "-",
                    color = Color.White,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(start = 7.dp)
                )
                Text(
                    text = character.species,
                    color = Color.White,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(start = 7.dp)
                )
            }

            Text(
                text = "Last known location:",
                color = Color(0xFF949494),
                fontSize = 13.sp,
                modifier = Modifier.padding(10.dp)
            )

            Text(
                text = character.location.name,
                color = Color.White,
                fontSize = 13.sp,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}
