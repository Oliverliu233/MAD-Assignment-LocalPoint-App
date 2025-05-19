package com.example.loacalpoint.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.loacalpoint.R
import com.example.loacalpoint.domain.Restaurant
import com.example.loacalpoint.ui.theme.LoacalPointTheme
import com.example.loacalpoint.viewModel.RestaurantViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch

class searchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoacalPointTheme {
                SearchScreen(viewModel = RestaurantViewModel())
            }
        }
    }
}

@Composable
fun SearchScreen(viewModel: RestaurantViewModel) {
    val query by viewModel.query.collectAsState()
    val restaurants by viewModel.restaurantList.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(-33.8688, 151.2093),
            10f
        )
    }

    val context = LocalContext.current
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(70.dp)
                    .background(Color.White, shape = RoundedCornerShape(24.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "search",
                    tint = Color(0xFF38029A),
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(30.dp)
                        .clickable {
                            (context as? AppCompatActivity)?.finish()
                        }
                )
                TextField(
                    value = query,
                    onValueChange = { viewModel.onQueryChange(it) },
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Transparent)
                        .padding(horizontal = 8.dp),
                    placeholder = { Text("Sydney food", color = Color(0xFFBDBDBD)) },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
                Icon(
                    painter = painterResource(id = R.drawable.fold),
                    contentDescription = "filter",
                    tint = Color(0xFF38029A),
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(30.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
           
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                GoogleMap(
                    modifier = Modifier.matchParentSize(),
                    cameraPositionState = cameraPositionState,
                    properties = MapProperties(isMyLocationEnabled = true),
                    uiSettings = MapUiSettings(zoomControlsEnabled = false)
                ) {
                    restaurants.forEach { restaurant ->
                        Marker(
                            state = rememberMarkerState(
                                position = LatLng(restaurant.latitude, restaurant.longitude)
                            ),
                            title = restaurant.name,
                            snippet = "Score: ${restaurant.rating}, " + when (restaurant.isOpenNow) {
                                true -> "Operating"
                                false -> "Resting"
                                null -> "unknown"
                            },
                            onClick = { false }
                        )
                    }
                }
                Button(
                    onClick = {
                        val pos = cameraPositionState.position.target
                        viewModel.onCenterChanged(pos.latitude, pos.longitude)
                        viewModel.searchInThisArea()
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
                    Text("Search in this area")
                }
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.wire),
                    contentDescription = "search",
                    tint = Color(0xFF38029A),
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(30.dp)
                )
                FilterChip(text = "Sort", onClick = { /* TODO */ })
                FilterChip(text = "Price", onClick = { viewModel.toggleSortByPrice() })
                FilterChip(text = "Open Now", onClick = { viewModel.toggleOpenNow() })
                FilterChip(text = "Nearby", onClick = { /* TODO */ })
            }


            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(restaurants) { restaurant ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .clickable {  },
                        elevation = 2.dp,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            AsyncImage(
                                model = restaurant.imageUrl,
                                contentDescription = restaurant.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .align(Alignment.CenterVertically)
                            ) {
                                Text(
                                    text = restaurant.name,
                                    style = MaterialTheme.typography.h6,
                                    color = Color.Black
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    repeat(5) { i ->
                                        Icon(
                                            painter = painterResource(id = R.drawable.star),
                                            contentDescription = null,
                                            tint = if (i < restaurant.rating.toInt()) Color(0xFFFFC107) else Color(0xFFE0E0E0),
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                    Text(
                                        text = " ${restaurant.rating} ",
                                        color = Color(0xFFE53935),
                                        style = MaterialTheme.typography.body2
                                    )
                                    Text(
                                        text = "${restaurant.reviewCount} reviews",
                                        color = Color(0xFFE53935),
                                        style = MaterialTheme.typography.body2
                                    )
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = restaurant.location,
                                        color = Color.Gray,
                                        style = MaterialTheme.typography.body2
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = when (restaurant.isOpenNow) {
                                            true -> "Open"
                                            false -> "Closed"
                                            null -> "Unknown"
                                        },
                                        color = when (restaurant.isOpenNow) {
                                            true -> Color(0xFF43A047)
                                            false -> Color(0xFFE53935)
                                            null -> Color.Gray
                                        },
                                        style = MaterialTheme.typography.body2
                                    )

                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "${"%.1f".format(restaurant.distanceKm)}KM",
                                        color = Color(0xFFE53935),
                                        style = MaterialTheme.typography.body2
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Surface(
                                        color = Color(0xFFE53935),
                                        shape = RoundedCornerShape(16.dp),
                                        modifier = Modifier
                                            .height(28.dp)
                                            .clickable {  }
                                    ) {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier.padding(horizontal = 12.dp)
                                        ) {
                                            Text(
                                                text = "Call",
                                                color = Color.White,
                                                style = MaterialTheme.typography.body2
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FilterChip(text: String, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        elevation = 1.dp,
        modifier = Modifier
            .height(32.dp)
            .clickable { onClick() }
            .border(width = 1.dp, color = Color(0xFF3F51B5), shape = RoundedCornerShape(16.dp))
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(text = text, color = Color(0xFF3F51B5), style = MaterialTheme.typography.body2)
        }
    }
}