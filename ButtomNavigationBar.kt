package com.example.loacalpoint

import android.widget.Toast
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import android.content.Context
import android.content.Intent
import com.example.loacalpoint.activity.UserActivity

@Composable
fun BottomNavigationBar(){
    val bottomMenuItemList= prepareBottomMenu()
    val context = LocalContext.current
    var selectedItem by remember { mutableStateOf("Home") }

    BottomAppBar() {
        bottomMenuItemList.forEachIndexed { index, bottomMenuItem ->
            BottomNavigationItem(
                selected = (selectedItem == bottomMenuItem.label),
                onClick = {
                    selectedItem = bottomMenuItem.label
                    if (bottomMenuItem.label == "Profile") {
                        // jump to UserActivity
                        val ctx = context
                        ctx.startActivity(Intent(ctx, UserActivity::class.java))
                    } else {
                        Toast.makeText(context, bottomMenuItem.label, Toast.LENGTH_SHORT).show()
                    }
                },
                icon = {
                    Icon(
                        painter = bottomMenuItem.icon,
                        contentDescription = bottomMenuItem.label,
                        modifier = Modifier
                            .height(28.dp)
                            .width(28.dp)
                    )
                },
                label = {
                    Text(
                        text = bottomMenuItem.label,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                },
                alwaysShowLabel = true,
                enabled = true
            )
        }
    }
}

data class BottomMenuItem(
    val label:String,val icon:Painter
)

@Composable
fun prepareBottomMenu():List<BottomMenuItem>{
    return listOf(
        BottomMenuItem(
            label ="Home",
            icon = painterResource(id=R.drawable.btn_1)
        ),
        BottomMenuItem(
            label ="Profile",
            icon = painterResource(id=R.drawable.btn_3)
        ),
        BottomMenuItem(
            label ="Support",
            icon = painterResource(id=R.drawable.btn_3)
        ),
        BottomMenuItem(
            label ="Settings",
            icon = painterResource(id=R.drawable.btn4)
        ),
    )
}