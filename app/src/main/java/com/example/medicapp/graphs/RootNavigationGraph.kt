package com.example.medicapp.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.medicapp.screens.bottomnav.BottomNavigationScreen

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT_GRAPH,
        startDestination = Graph.ON_BOARDING_GRAPH
    ) {
        onBoardingGraph(navController)
        composable(route = Graph.HOME_GRAPH) {
            BottomNavigationScreen()
        }
    }
}

object Graph {
    const val ROOT_GRAPH = "root_graph"
    const val ON_BOARDING_GRAPH = "on_boarding_graph"
    const val HOME_GRAPH = "home_graph"
}