package com.example.loacalpoint.domain

import java.util.Date

data class Review(
    val restaurantName: String = "",
    val imageUrl: String = "",
    val rating: Int = 0,
    val content: String = "",
    val timeAgo: String = "",
    val date: Date = Date() // 添加日期字段用于排序
)