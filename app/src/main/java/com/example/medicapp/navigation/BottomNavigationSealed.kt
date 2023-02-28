package com.example.medicapp.navigation

import androidx.annotation.DrawableRes
import com.example.medicapp.R

sealed class BottomNavigationSealed(val route: String, val title: String, @DrawableRes val icon: Int) {
    object AnaliseScreen: BottomNavigationSealed(route = "analise_route", title = "Анализы", icon = R.drawable.analiz_icon)
    object ResultScreen: BottomNavigationSealed(route = "result_route", title = "Результаты", icon = R.drawable.result_icon)
    object SupportScreen: BottomNavigationSealed(route = "support_route", title = "Поддержка", icon = R.drawable.support_icon)
    object ProfileScreen: BottomNavigationSealed(route = "profile_route", title = "Профиль", icon = R.drawable.profile_icon)
}
