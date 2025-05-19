package com.example.loacalpoint.domain

data class Restaurant(
    val id: String = "", // 添加id字段
    val name: String = "",
    val location: String = "",
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val imageUrl: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val isOpenNow: Boolean? = null,  // 保持为可空类型，但确保 Firebase 能正确解析
    val priceLevel: Int = 0,
    val distanceKm: Double = 0.0
)