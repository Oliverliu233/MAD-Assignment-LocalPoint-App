package com.example.loacalpoint.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.loacalpoint.R
import com.example.loacalpoint.domain.Review
import java.text.SimpleDateFormat
import java.util.*

class reviewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get passed image url
        val imageUrl = intent.getStringExtra("imageUrl") ?: "https://example.com/restaurant.jpg"
        val restaurantName = intent.getStringExtra("restaurantName") ?: "Vanto"
        val restaurantAddress = intent.getStringExtra("restaurantAddress") ?: "shop 44, 455 George St, Sydney"

        setContent {
            ReviewScreen(
                restaurantName = restaurantName,
                restaurantAddress = restaurantAddress,
                restaurantImage = imageUrl, // Use the passed image
                onClose = { finish() },
                onPost = { rating, reviewText ->
                    // Create Intent and pass review data
                    val intent = Intent(this, UserActivity::class.java)
                    intent.putExtra("review_restaurant_name", restaurantName)
                    intent.putExtra("review_image_url", imageUrl)
                    intent.putExtra("review_rating", rating)
                    intent.putExtra("review_content", reviewText)
                    intent.putExtra("review_time_ago", "Just now")
                    intent.putExtra("from_review", true)
                    startActivity(intent)
                    finish()
                }
            )
        }
    }
}

@Composable
fun ReviewScreen(
    restaurantName: String,
    restaurantAddress: String,
    restaurantImage: String,
    onClose: () -> Unit,
    onPost: (Int, String) -> Unit
) {
    var reviewText by remember { mutableStateOf("") }
    var selectedStars by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.White)
                .padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.error), // Replace with your error icon
                contentDescription = "Close",
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onClose() }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Leave a review",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )
        }

        // Restaurant info section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = restaurantImage,
                contentDescription = restaurantName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(restaurantName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(restaurantAddress, color = Color.Gray, fontSize = 14.sp)
            }
        }

        // Rating section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Rate your experience", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                repeat(5) { index ->
                    Icon(
                        painter = painterResource(id = R.drawable.star), // Replace with your star icon
                        contentDescription = "Star ${index + 1}",
                        tint = if (index < selectedStars) Color(0xFFFFC107) else Color(0xFFE0E0E0),
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { selectedStars = index + 1 }
                    )
                }
            }
        }

        // Review text field
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text("Write your review", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = reviewText,
                onValueChange = { reviewText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                placeholder = { Text("Share your experience...") },
                shape = RoundedCornerShape(8.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f)) // Push button to bottom

        // Post Review button
        Button(
            onClick = {
                if (selectedStars > 0) {
                    onPost(selectedStars, reviewText)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .height(52.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Post Review", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}