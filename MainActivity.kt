package com.example.loacalpoint.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.loacalpoint.*
import com.example.loacalpoint.R
import com.example.loacalpoint.domain.Restaurant
import com.example.loacalpoint.ui.theme.LoacalPointTheme
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 动态申请定位权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100)
        }
        enableEdgeToEdge()
        setContent {
            LoacalPointTheme {
                MainScreen(
                    onSearchClick = {
                        startActivity(Intent(this, searchActivity::class.java))
                    }
                )
            }
        }
    }
}

@Composable
fun MainScreen(onSearchClick: () -> Unit) {
    // 模拟餐厅数据
    val pastaRestaurants = remember {
        listOf(
            Restaurant(
                name = "Hot Pot World",
                rating = 4.5,
                reviewCount = 47,
                imageUrl = "https://res.cloudinary.com/dlm4rzzgo/image/upload/v1747574559/bc7701ae-ce02-4aff-be8f-0a340272fb95_qoh57c.jpg",
                distanceKm = 1.2,
                isOpenNow = true
            ),
            Restaurant(
                name = "Bills Surry Hills",
                rating = 4.7,
                reviewCount = 78,
                imageUrl = "https://res.cloudinary.com/dlm4rzzgo/image/upload/v1747574553/pasta-3547078_rtiklf.jpg",
                distanceKm = 0.8,
                isOpenNow = true
            ),
            Restaurant(
                name = "Chat Thai",
                rating = 6.7,
                reviewCount = 40,
                imageUrl = "https://res.cloudinary.com/dlm4rzzgo/image/upload/v1747574553/f38381ff-84ca-4592-a54e-ad677103990f_wxro8z.jpg",
                distanceKm = 0.8,
                isOpenNow = true
            ),
            Restaurant(
                name = "Icebergs Dining Room",
                rating = 3.2,
                reviewCount = 90,
                imageUrl = "https://res.cloudinary.com/dlm4rzzgo/image/upload/v1747574550/47ee1bb2-a9fa-434b-8316-e4446c71bf21_ucwfbe.jpg",
                distanceKm = 0.8,
                isOpenNow = true
            )
        )
    }
    
    val veganRestaurants = remember {
        listOf(
            Restaurant(
                name = "Sunny days",
                rating = 12.3,
                reviewCount = 42,
                imageUrl = "https://res.cloudinary.com/dlm4rzzgo/image/upload/v1747633588/vegan1_wi1qut.jpg",
                distanceKm = 1.7,
                isOpenNow = true
            ),
            Restaurant(
                name = "Olympia",
                rating = 8.6,
                reviewCount = 65,
                imageUrl = "https://res.cloudinary.com/dlm4rzzgo/image/upload/v1747633587/vegan4_chmc5d.jpg",
                distanceKm = 2.1,
                isOpenNow = false
            ),
            Restaurant(
                name = "Mr. Wong",
                rating = 4.3,
                reviewCount = 65,
                imageUrl = "https://res.cloudinary.com/dlm4rzzgo/image/upload/v1747633586/vegan3_vrt1hx.jpg",
                distanceKm = 4.1,
                isOpenNow = false
            ),
            Restaurant(
                name = "Cafe Sydney",
                rating = 4.6,
                reviewCount = 55,
                imageUrl = "https://res.cloudinary.com/dlm4rzzgo/image/upload/v1747633586/vegan2_jf15vn.jpg",
                distanceKm = 2.1,
                isOpenNow = false
            )
        )
    }
    
    val asianRestaurants = remember {
        listOf(
            Restaurant(
                name = "Sushi Master",
                rating = 4.8,
                reviewCount = 92,
                imageUrl = "https://res.cloudinary.com/dlm4rzzgo/image/upload/v1747633119/asian6_xp2q7o.jpg",
                distanceKm = 1.7,
                isOpenNow = true
            ),
            Restaurant(
                name = "Pho Delight",
                rating = 4.4,
                reviewCount = 53,
                imageUrl = "https://res.cloudinary.com/dlm4rzzgo/image/upload/v1747633119/asian4_itd1z2.jpg",
                distanceKm = 0.9,
                isOpenNow = true
            )
            ,
            Restaurant(
                name = "Cafe Sydney",
                rating = 6.6,
                reviewCount = 63,
                imageUrl = "https://res.cloudinary.com/dlm4rzzgo/image/upload/v1747633119/asian1_mrvzxw.jpg",
                distanceKm = 1.1,
                isOpenNow = false
            ),
            Restaurant(
                name = "Spice Alley",
                rating = 4.6,
                reviewCount = 95,
                imageUrl = "https://res.cloudinary.com/dlm4rzzgo/image/upload/v1747633119/asian5_ff8isp.jpg",
                distanceKm = 2.8,
                isOpenNow = false
            )
        )
    }
    
    val fastFoodRestaurants = remember {
        listOf(
            Restaurant(
                name = "Burger Joint",
                rating = 4.2,
                reviewCount = 41,
                imageUrl = "https://res.cloudinary.com/dlm4rzzgo/image/upload/v1747574553/ceefa726-7434-4e57-8180-10c791c6b63d_jpfzo4.jpg",
                distanceKm = 0.5,
                isOpenNow = true
            ),
            Restaurant(
                name = "Pizza Express",
                rating = 4.0,
                reviewCount = 38,
                imageUrl = "https://res.cloudinary.com/dlm4rzzgo/image/upload/v1747574550/pork-1690696_srpq3w.jpg",
                distanceKm = 1.1,
                isOpenNow = false
            )
            ,
            Restaurant(
                name = "Firedoor",
                rating = 6.6,
                reviewCount = 23,
                imageUrl = "https://res.cloudinary.com/dlm4rzzgo/image/upload/v1747633893/pexels-foodie-factor-162291-551991_tbga1u.jpg",
                distanceKm = 7.1,
                isOpenNow = false
            ),
            Restaurant(
                name = "Doyles on the Beach",
                rating = 9.6,
                reviewCount = 95,
                imageUrl = "https://res.cloudinary.com/dlm4rzzgo/image/upload/v1747633893/pexels-enginakyurt-2725744_ypdiu9.jpg",
                distanceKm = 6.8,
                isOpenNow = false
            )
        )
    }
    
    var selectedCategory by remember { mutableStateOf("Pasta") }
    
    Scaffold(
        bottomBar = { BottomNavigationBar() },
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White // 设置为白色背景
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // 1. 顶部菜单栏和标题
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.list), // 替换为你的菜单图标资源
                    contentDescription = "Menu",
                    tint = Color(0xFFE53935),
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Discover Local, Taste Authentic!",
                    color = Color(0xFFE53935),
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    modifier = Modifier.weight(1f)
                )
            }

          
            Box(
                modifier = Modifier
                    .padding(top = 8.dp)
            ) {
                SearchBar(hint = "Search", onClick = onSearchClick)
            }
            
            // 3. 分类标题
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Categories",
                    color = Color(0xFF3F51B5),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                
                Text(
                    text = "Show all >",
                    color = Color(0xFF3F51B5),
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { /* 显示所有分类 */ }
                )
            }
            
            // 4. 分类图标行
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(listOf("Pasta", "Vegan", "Asian", "Fast Food")) { category ->
                    CategoryItem(
                        category = category,
                        isSelected = category == selectedCategory,
                        onClick = { selectedCategory = category }
                    )
                }
            }
            
            // 5. 根据选择的分类显示餐厅
            when (selectedCategory) {
                "Pasta" -> Pasta(pastaRestaurants)
                "Vegan" -> Vagan(veganRestaurants)
                "Asian" -> Asign(asianRestaurants)
                "Fast Food" -> FastFood(fastFoodRestaurants)
            }
        }
    }
}

@Composable
fun CategoryItem(category: String, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
            .clickable(onClick = onClick)
    ) {
        // 分类图标
        val iconRes = when (category) {
            "Pasta" -> R.drawable.noodel
            "Vegan" -> R.drawable.leave
            "Asian" -> R.drawable.fox
            "Fast Food" -> R.drawable.huanbaoger
            else -> R.drawable.btn_1
        }
        
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(if (isSelected) Color(0xFFE0E0E0) else Color(0xFFF5F5F5))
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = category,
                tint = Color(0xFF3F51B5),
                modifier = Modifier.size(30.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        // 分类名称
        Text(
            text = category,
            color = if (isSelected) Color(0xFF3F51B5) else Color.Gray,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}