package com.example.rickandmortywiki.presentation.locationScreen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmortywiki.api.LocationItem

@Composable
fun LocationScreen(locationViewModel: LocationViewModel) {
    val pagingData: LazyPagingItems<LocationItem> =
        locationViewModel.pagingData.collectAsLazyPagingItems()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2b2d30))
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(pagingData.itemCount) { index ->
                val locationItem = pagingData[index]
                if (locationItem != null) {
                    LocationItemCard(locationItem = locationItem)
                }
            }
        }
    }
}

@Composable
fun LocationItemCard(locationItem: LocationItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = Color(0xFF424242),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Name: ${locationItem.name}", style = TextStyle(color = Color.White))
            Text(text = "Type: ${locationItem.type}", style = TextStyle(color = Color.White))
            Text(
                text = "Dimension: ${locationItem.dimension}",
                style = TextStyle(color = Color.White)
            )
            Text(
                text = "Residents: ${locationItem.residents.size}",
                style = TextStyle(color = Color.White)
            )
            Text(
                text = "Was created: ${locationItem.created.take(10)}",
                style = TextStyle(color = Color.White)
            )
        }
    }
}
