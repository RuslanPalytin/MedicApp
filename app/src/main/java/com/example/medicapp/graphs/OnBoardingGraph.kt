package com.example.medicapp.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.medicapp.navigation.OnBoardingScreenSealed
import com.example.medicapp.screens.*

fun NavGraphBuilder.onBoardingGraph(navController: NavHostController) {
    navigation(
        route = Graph.ON_BOARDING_GRAPH,
        startDestination = OnBoardingScreenSealed.SplashScreen.route
    ) {
        composable(OnBoardingScreenSealed.SplashScreen.route) {
            LaunchScreen(navController)
        }
        composable(OnBoardingScreenSealed.OnBoardingScreen.route) {
            OnBoardingScreen(navController)
        }
        composable(OnBoardingScreenSealed.LoginAndRegistrationScreen.route) {
            LoginAndRegistrationScreen(navController)
        }
        composable(OnBoardingScreenSealed.CodeFromEmailScreen.route) {
            CodeFromEmailScreen(navController)
        }
        composable(OnBoardingScreenSealed.CreatePasswordScreen.route) {
            CreatePasswordScreen(navController)
        }
        composable(OnBoardingScreenSealed.CreateCardScreen.route) {
            CreateCardScreen(navController)
        }
    }
}