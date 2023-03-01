package com.example.medicapp.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.medicapp.navigation.BottomNavigationSealed
import com.example.medicapp.screens.bottomnav.analise.AnaliseScreen
import com.example.medicapp.screens.bottomnav.profile.ProfileScreen
import com.example.medicapp.screens.bottomnav.result.ResultScreen
import com.example.medicapp.screens.bottomnav.support.SupportScreen

@Composable
fun BottomNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME_GRAPH,
        startDestination = BottomNavigationSealed.AnaliseScreen.route
    ) {
        composable(route = BottomNavigationSealed.AnaliseScreen.route) {
            AnaliseScreen(navController)
        }
        composable(route = BottomNavigationSealed.ResultScreen.route) {
            ResultScreen(navController)
        }
        composable(route = BottomNavigationSealed.SupportScreen.route) {
            SupportScreen(navController)
        }
        composable(route = BottomNavigationSealed.ProfileScreen.route) {
            ProfileScreen(navController)
        }
    }
}