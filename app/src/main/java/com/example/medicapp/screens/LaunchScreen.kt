package com.example.medicapp.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.decode.ImageSource
import com.example.medicapp.R
import com.example.medicapp.graphs.Graph
import com.example.medicapp.navigation.OnBoardingScreenSealed
import com.example.medicapp.storage.SharedPreference
import com.example.medicapp.ui.theme.Blue1
import com.example.medicapp.ui.theme.Blue2
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

@Composable
fun LaunchScreen(navController: NavController) {

    val systemUiController = rememberSystemUiController()
    systemUiController.isSystemBarsVisible = false
    val context = LocalContext.current
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
        Image(
            painter = painterResource(id = R.drawable.logo_app_icon),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 56.dp)
        )
    }
    LaunchedEffect(key1 = true) {
        delay(1000)
        val token = SharedPreference(context).readToken()
        if(token == "") {
            navController.navigate(OnBoardingScreenSealed.OnBoardingScreen.route)
        } else {
            navController.navigate(Graph.HOME_GRAPH)
        }
    }
}