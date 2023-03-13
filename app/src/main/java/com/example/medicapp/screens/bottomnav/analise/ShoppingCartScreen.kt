package com.example.medicapp.screens.bottomnav.analise

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicapp.R
import com.example.medicapp.screens.bottomnav.analise.uiitems.ShoppingCartItem
import com.example.medicapp.storage.DbHandlerAnalise
import com.example.medicapp.ui.theme.LatoRegular

@Composable
fun ShoppingCartScreen(navController: NavController) {

    val context = LocalContext.current
    val db = DbHandlerAnalise(context)
    val items = db.getItems()
    val peopleNumbers: MutableList<MutableState<Int>> = mutableListOf()
    val sum = remember { mutableStateOf(0) }

    if (sum.value == 0) {
        items.forEach { catalogModel ->
            sum.value += catalogModel.price.toInt()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(top = 88.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Корзина",
                fontFamily = LatoRegular,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Image(
                painter = painterResource(id = R.drawable.delete_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        db.deleteAllItems(items)
                    }
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(count = items.size) { index ->

                peopleNumbers.add(mutableStateOf(1))

                ShoppingCartItem(
                    name = items[index].name,
                    price = items[index].price,
                    peopleNumber = peopleNumbers[index]
                )
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Сумма",
                fontFamily = LatoRegular,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${sum.value}",
                fontFamily = LatoRegular,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    Image(
        painter = painterResource(id = R.drawable.back_button_icon),
        contentDescription = null,
        modifier = Modifier
            .padding(top = 42.dp, start = 26.dp)
            .size(32.dp)
            .clickable {
                navController.popBackStack()
            }
    )
}