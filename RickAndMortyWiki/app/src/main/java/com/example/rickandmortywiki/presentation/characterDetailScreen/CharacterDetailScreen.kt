package com.example.rickandmortywiki.presentation.characterDetailScreen

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.api.Character


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterDetailScreen(character: Character) {
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF2b2d30))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            GlideImage(
                model = character.image,
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.align(Alignment.Top)
            ) {
                Text(
                    text = character.name,
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(stringResource(R.string.live_status), color = Color.White)
                Text(character.status, color = Color.White)

                Spacer(modifier = Modifier.height(6.dp))

                Text(stringResource(R.string.species_and_gender), color = Color.White)
                Text("${character.species} (${character.gender})", color = Color.White)

                Spacer(modifier = Modifier.height(6.dp))

                Text(stringResource(R.string.last_known_location), color = Color.White)
                Text(character.location.name, color = Color.White)

                Spacer(modifier = Modifier.height(6.dp))

                Text(stringResource(R.string.first_seen_in), color = Color.White)
                val episodeUrl = character.episode[0]
                val episodeNumber = episodeUrl.substringAfterLast("/")
                Text(stringResource(R.string.episode) +episodeNumber, color = Color.White)
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
                .padding(16.dp)
        ) {
            items(character.episode) { episodeUrl ->
                val episodeNumber = episodeUrl.substringAfterLast("/").toInt()
                Text(stringResource(R.string.episode) +episodeNumber, color = Color.White)
            }
        }
        Button(
            onClick = {
                onBackPressedDispatcher?.onBackPressedDispatcher?.onBackPressed()
            },
            colors = ButtonColors(
                containerColor = Color(0xFF999999),
                contentColor = Color(0xFF2b2d30),
                disabledContainerColor = Color(0xFF424242),
                disabledContentColor = Color(0xFF2b2d30)
            ),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(10.dp)
        ) {
            Text(stringResource(R.string.back))
        }
    }
}



