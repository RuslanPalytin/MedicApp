package com.example.medicapp.graphs

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.medicapp.navigation.OnBoardingScreenSealed
import com.example.medicapp.screens.*
import kotlin.reflect.typeOf

const val EMAIL = "email"

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
        composable(
            route = OnBoardingScreenSealed.CodeFromEmailScreen.route,
            arguments = listOf(
                navArgument(name = EMAIL, builder = { type = NavType.StringType })
            )
        ) { backStackEntry ->
            CodeFromEmailScreen(
                navController = navController,
                email = backStackEntry.arguments?.getString(EMAIL)!!
            )
        }
        composable(OnBoardingScreenSealed.CreatePasswordScreen.route) {
            CreatePasswordScreen(navController)
        }
        composable(OnBoardingScreenSealed.CreateCardScreen.route) {
            CreateCardScreen(navController)
        }
    }
}