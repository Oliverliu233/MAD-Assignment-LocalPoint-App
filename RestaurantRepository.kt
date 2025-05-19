package com.example.loacalpoint.repository

import com.example.loacalpoint.domain.Restaurant
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RestaurantRepository {
    // 数据库节点引用（假设根节点下有 "restaurants" 列表）
    private val databaseRef = FirebaseDatabase.getInstance().reference.child("restaurants")

    // 从 Firebase 获取餐厅列表（通过 Flow 持续监听数据变化）
    fun getRestaurantsFlow(): Flow<List<Restaurant>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Restaurant>()
                // 遍历每个子节点并映射为 Restaurant 对象
                snapshot.children.forEach { child ->
                    val restaurant = child.getValue(Restaurant::class.java)
                    // 确保 isOpenNow 字段正确解析
                    restaurant?.let { 
                        // 如果 isOpenNow 为 null 但在数据中存在，尝试手动获取
                        if (restaurant.isOpenNow == null && child.hasChild("isOpenNow")) {
                            val isOpen = child.child("isOpenNow").getValue(Boolean::class.java)
                            list.add(restaurant.copy(isOpenNow = isOpen))
                        } else {
                            list.add(it) 
                        }
                    }
                }
                // 发送列表给 Flow
                trySend(list)
            }

            override fun onCancelled(error: DatabaseError) {
                // 读取失败时关闭通道并报告异常
                close(error.toException())
            }
        }
        // 开始监听
        databaseRef.addValueEventListener(listener)
        // 等待取消时移除监听
        awaitClose { databaseRef.removeEventListener(listener) }
    }
}