package com.example.medicapp.navigation

sealed class AnaliseScreenSealed(val route: String) {
    object SearchScreen : AnaliseScreenSealed(route = "analise_sealed_screen")
    object ShoppingCardScreen: AnaliseScreenSealed(route = "shopping_card_screen")
}
