package com.example.loacalpoint.activity

 
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
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
import com.example.loacalpoint.ui.theme.LoacalPointTheme

class restaurantDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val imageUrl = intent.getStringExtra("imageUrl") ?: ""
        val restaurantName = intent.getStringExtra("restaurantName") ?: "未知餐厅"
        val restaurantRating = intent.getDoubleExtra("restaurantRating", 0.0)
        val restaurantReviewCount = intent.getIntExtra("restaurantReviewCount", 0)
        val restaurantDistance = intent.getDoubleExtra("restaurantDistance", 0.0)
        // TODO: Retrieve other details if you added them in MainActivity
        
        setContent {
            LoacalPointTheme {
                RestaurantDetailScreen(
                    imageUrl = imageUrl,
                    restaurantName = restaurantName,
                    restaurantRating = restaurantRating,
                    restaurantReviewCount = restaurantReviewCount,
                    restaurantDistance = restaurantDistance,
                    onBack = { finish() }
                )
            }
        }
    }
}

@Composable
fun RestaurantDetailScreen(
    imageUrl: String,
    restaurantName: String,
    restaurantRating: Double,
    restaurantReviewCount: Int,
    restaurantDistance: Double,
    // TODO: Add other parameters for restaurant details
    onBack: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 顶部大图和返回、分享、收藏按钮
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Restaurant Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back), // 示例返回图标
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0x66000000))
                        .clickable { onBack() }
                        .padding(6.dp)
                )
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.share), // 示例分享图标
                        contentDescription = "Share",
                        tint = Color.White,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0x66000000))
                            .padding(6.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.heart), // 示例收藏图标
                        contentDescription = "Favorite",
                        tint = Color.White,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0x66000000))
                            .padding(6.dp)
                    )
                }
            }
        }
        // 餐厅信息
        Column(modifier = Modifier.padding(16.dp)) {
            Text(restaurantName, fontWeight = FontWeight.Bold, fontSize = 28.sp) // 使用传递的餐厅名称
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.location), // 示例定位图标
                    contentDescription = "Location",
                    tint = Color.Gray,
                    modifier = Modifier.size(18.dp)
                )
                // TODO: Use actual restaurant address if passed
                Text("Surry Hills", color = Color.Gray, fontSize = 15.sp) // 示例地址，如果传递了实际地址请替换
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.clock), // 示例时钟图标
                    contentDescription = "Time",
                    tint = Color.Gray,
                    modifier = Modifier.size(18.dp)
                )
                // TODO: Use actual opening hours if passed
                Text("12PM to 23PM - ", color = Color.Gray, fontSize = 15.sp) // 示例时间，如果传递了请替换
                Text("Open now", color = Color(0xFF4CAF50), fontSize = 15.sp) // 示例状态，如果传递了请替换
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.star), // 示例星星图标
                    contentDescription = "Star",
                    tint = Color.Red,
                    modifier = Modifier.size(18.dp)
                )
                Text("${"%.1f".format(restaurantRating)}", color = Color.Red, fontSize = 15.sp) // 使用传递的评分
                Text(" (${restaurantReviewCount} reviews)", color = Color.Black, fontSize = 15.sp) // 使用传递的评论数
            }
        }
        // Featured Items
        Text("Featured Items", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(start = 16.dp))
        Row(modifier = Modifier.padding(16.dp)) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
                    .padding(end = 8.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = 4.dp
            ) {
                Column {
                    AsyncImage(
                        model = "https://res.cloudinary.com/dlm4rzzgo/image/upload/v1747574553/92e91455-85a9-43d2-ae26-d0784e5fa9c2_xgw07u.jpg", // 示例菜品图片
                        contentScale = ContentScale.Crop,
                        contentDescription = "Vegetables cream",
                        modifier = Modifier.height(70.dp).fillMaxWidth()
                    )
                    Text("Vegetables cream", fontSize = 15.sp, modifier = Modifier.padding(8.dp))
                    Text("$10.90", color = Color.Gray, fontSize = 13.sp, modifier = Modifier.padding(start = 8.dp, bottom = 8.dp))
                }
            }
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = 4.dp
            ) {
                Column {
                    AsyncImage(
                        model = "https://res.cloudinary.com/dlm4rzzgo/image/upload/v1747574553/f38381ff-84ca-4592-a54e-ad677103990f_wxro8z.jpg", // 示例菜品图片
                        contentScale = ContentScale.Crop,
                        contentDescription = "Rice with prawns",
                        modifier = Modifier.height(70.dp).fillMaxWidth()
                    )
                    Text("Rice with prawns", fontSize = 15.sp, modifier = Modifier.padding(8.dp))
                    Text("$17.90", color = Color.Gray, fontSize = 13.sp, modifier = Modifier.padding(start = 8.dp, bottom = 8.dp))
                }
            }
        }
        // Reviews
        Text("Reviews", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(start = 16.dp))
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = "https://res.cloudinary.com/dlm4rzzgo/image/upload/v1747591897/people_zzijea.jpg", // 示例头像
                contentScale = ContentScale.Crop,
                contentDescription = "avatar",
                modifier = Modifier.size(36.dp).clip(CircleShape)
            )
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text("Jenny", fontWeight = FontWeight.Bold)
                Row {
                    repeat(5) {
                        Icon(
                            painter = painterResource(id = R.drawable.star), // 示例星星
                            contentDescription = "star",
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                Text("Great Food! Incredible experience and fantastic Real italian food.", fontSize = 14.sp)
                AsyncImage(
                    model = imageUrl,// 示例评论图片
                    contentScale = ContentScale.Crop,
                    contentDescription = "review food",
                    modifier = Modifier.size(60.dp).clip(RoundedCornerShape(8.dp))
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text("1 months ago", color = Color.Gray, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.weight(1f))
        // 底部按钮
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    // 跳转到 reviewActivity，并传递更多信息
                    val intent = Intent(context, reviewActivity::class.java)
                    intent.putExtra("imageUrl", imageUrl) // 传递图片URL
                    intent.putExtra("restaurantName", restaurantName) // 传递餐厅名称
                    // TODO: Pass actual restaurant address if available
                    intent.putExtra("restaurantAddress", "Surry Hills") // 示例地址，如果传递了实际地址请替换
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.edit), // 示例留言图标
                    contentDescription = "Leave a review",
                    tint = Color(0xFFFF9800),
                    modifier = Modifier.size(20.dp)
                )
                Text("Leave a review", color = Color(0xFF141417), modifier = Modifier.padding(start = 8.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { /* 拨打电话逻辑 */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.phone), // 示例电话图标
                    contentDescription = "Call",
                    tint = Color(0xFFFF9800),
                    modifier = Modifier.size(20.dp)
                )
                Text("Call", color = Color(0xFF0C0C0C), modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}
 