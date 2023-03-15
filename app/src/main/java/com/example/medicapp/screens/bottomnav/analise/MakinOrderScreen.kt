package com.example.medicapp.screens.bottomnav.analise

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medicapp.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MakingOrderScreen(navController: NavController) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = Color.White, darkIcons = true)

    val analiseNumber = remember { mutableStateOf(0) }
    val analiseSum = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 88.dp)
    ) {

    }

    Image(
        painter = painterResource(id = R.drawable.back_button_icon),
        contentDescription = null,
        modifier = Modifier.padding(top = 32.dp, start = 20.dp) .clickable { navController.popBackStack() })
}