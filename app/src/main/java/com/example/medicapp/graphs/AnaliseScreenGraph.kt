package com.example.medicapp.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.medicapp.navigation.AnaliseScreenSealed
import com.example.medicapp.screens.bottomnav.analise.MakingOrderScreen
import com.example.medicapp.screens.bottomnav.analise.SearchScreen
import com.example.medicapp.screens.bottomnav.analise.ShoppingCartScreen

fun NavGraphBuilder.analiseScreen(navController: NavHostController) {
    navigation(
        route = Graph.ANALISE_GRAPH,
        startDestination = AnaliseScreenSealed.SearchScreen.route
    ) {
        composable(route = AnaliseScreenSealed.SearchScreen.route) {
            SearchScreen(navController = navController)
        }
        composable(route = AnaliseScreenSealed.ShoppingCardScreen.route) {
            ShoppingCartScreen(navController = navController)
        }
        composable(route = AnaliseScreenSealed.MakingOrderScreen.route) {
            MakingOrderScreen(navController = navController)
        }
    }
}