package com.example.medicapp.screens.bottomnav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.medicapp.graphs.BottomNavigationGraph
import com.example.medicapp.navigation.BottomNavigationSealed
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun BottomNavigationScreen() {

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = Color.White, darkIcons = true)
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(navController = navController)}
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            BottomNavigationGraph(navController = navController)
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf(
        BottomNavigationSealed.AnaliseScreen,
        BottomNavigationSealed.ResultScreen,
        BottomNavigationSealed.SupportScreen,
        BottomNavigationSealed.ProfileScreen,
    )

}