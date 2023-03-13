package com.example.medicapp.screens.bottomnav.analise.uiitems

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicapp.models.CatalogModel
import com.example.medicapp.storage.DbHandlerAnalise
import com.example.medicapp.ui.theme.ButtonEnabledColor
import com.example.medicapp.ui.theme.GrayTextOnBoarding
import com.example.medicapp.ui.theme.LatoRegular
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CatalogItem(
    item: CatalogModel,
    scope: CoroutineScope,
    modalSheetState: ModalBottomSheetState,
    selectedItem: MutableState<CatalogModel?>,
    price: MutableState<Int>
) {
    val context = LocalContext.current
    val db = DbHandlerAnalise(context)
    val isButtonStyle = remember { mutableStateOf(true) }
    val items = db.getItems()

    Card(
        elevation = 3.dp,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 3.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable {
                    selectedItem.value = item
                    scope.launch {
                        modalSheetState.show()
                    }
                }
        ) {
            Text(
                text = item.name,
                fontSize = 16.sp,
                fontFamily = LatoRegular,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = item.time_result,
                        fontSize = 14.sp,
                        color = GrayTextOnBoarding,
                        fontFamily = LatoRegular
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.price + "₽",
                        fontFamily = LatoRegular,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                //TODO Доделать
                items.forEach {
                    ShowButton(enabled = it.name == item.name, item = item, price = price, isButtonStyle = isButtonStyle)
                }
            }
        }
    }
}

@Composable
fun ShowButton(
    enabled: Boolean,
    item: CatalogModel,
    price: MutableState<Int>,
    isButtonStyle: MutableState<Boolean>
) {
    val context = LocalContext.current
    val db = DbHandlerAnalise(context)

    Button(
        modifier = Modifier
            .height(46.dp)
            .width(112.dp),
        onClick = {
            isButtonStyle.value = !isButtonStyle.value
            if (!isButtonStyle.value) {
                price.value += item.price.toInt()
                db.addItem(name = item.name, price = item.price)
            } else {
                price.value -= item.price.toInt()
                db.deleteItem(itemName = item.price)
            }
        },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = if (enabled) ButtonEnabledColor else Color.White),
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (enabled) Color.White else ButtonEnabledColor
        )
    ) {
        Text(
            text = if (enabled) "Добавить" else "Убрать",
            fontFamily = LatoRegular,
            color = if (enabled) Color.White else ButtonEnabledColor,
            fontSize = 14.sp
        )

    }
}