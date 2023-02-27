package com.example.medicapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicapp.R
import com.example.medicapp.navigation.OnBoardingScreenSealed
import com.example.medicapp.ui.theme.Blue1
import com.example.medicapp.ui.theme.Blue2
import kotlinx.coroutines.delay

@Composable
fun LaunchScreen(navController: NavController) {

    val brushColors = listOf(Blue1, Blue2, Blue1)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = brushColors,
                    start = Offset.Infinite,
                    end = Offset.Zero
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Смартлаб", fontWeight = FontWeight.Bold, fontSize = 52.sp, color = Color.White)
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                painter = painterResource(id = R.drawable.add_rentagle_icon),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(52.dp)
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 20.dp,
                            bottomEnd = 20.dp
                        )
                    )
            )
        }
    }
    LaunchedEffect(key1 = true) {
        delay(2000)
        navController.navigate(OnBoardingScreenSealed.OnBoardingScreen.route)
    }
}