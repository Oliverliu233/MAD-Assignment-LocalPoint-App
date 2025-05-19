package com.example.loacalpoint

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchBar(hint:String="", onClick: (() -> Unit)? = null){
    Row (
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(50.dp)
            .background(
                color= Color(0x20ffffff),
                shape = RoundedCornerShape(50.dp)
            )
            .padding(horizontal = 16.dp)
            .clickable(enabled = onClick != null) { onClick?.invoke() },
            verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter= painterResource(id=R.drawable.search),
            contentDescription = null,
            tint = Color(0xFF38029A),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = hint,
            color = Color(0xffbdbdbd),
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            painter= painterResource(id=R.drawable.fold),
            contentDescription = null,
            tint = Color(0xFF3F51B5),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            painter= painterResource(id=R.drawable.wire),
            contentDescription = null,
            tint = Color(0xFF3F51B5),
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview
@Composable
fun SearchBarPreview() {
    SearchBar(hint = "搜索餐厅")
}