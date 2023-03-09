package com.example.medicapp.screens.bottomnav.analise

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicapp.R
import com.example.medicapp.models.CatalogModel
import com.example.medicapp.ui.theme.ButtonEnabledColor
import com.example.medicapp.ui.theme.GrayTextOnBoarding
import com.example.medicapp.ui.theme.LatoRegular
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetContent(item: CatalogModel, scope: CoroutineScope, state: ModalBottomSheetState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 24.dp, bottom = 40.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                modifier = Modifier.fillMaxWidth(0.8f),
                text = item.name,
                fontSize = 20.sp,
                fontFamily = LatoRegular,
                fontWeight = FontWeight.Bold
            )
            Icon(
                painter = painterResource(id = R.drawable.close_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        scope.launch {
                            state.hide()
                        }
                    }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Описание",
            color = GrayTextOnBoarding,
            fontFamily = LatoRegular,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = item.description, fontFamily = LatoRegular, fontSize = 15.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Подготовка",
            color = GrayTextOnBoarding,
            fontFamily = LatoRegular,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = item.preparation, fontFamily = LatoRegular, fontSize = 15.sp)
        Spacer(modifier = Modifier.height(57.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth(0.6f)) {
                Text(
                    text = "Результаты через:",
                    fontFamily = LatoRegular,
                    fontSize = 14.sp,
                    color = GrayTextOnBoarding
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.time_result,
                    fontFamily = LatoRegular,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column {
                Text(
                    text = "Биоматериал:",
                    fontFamily = LatoRegular,
                    fontSize = 14.sp,
                    color = GrayTextOnBoarding
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.bio,
                    fontFamily = LatoRegular,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(19.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ButtonEnabledColor,
                contentColor = Color.White
            ),
            onClick = { /*TODO*/ },
        ) {
            Text(
                text = "Добавить за ${item.price} ₽",
                fontFamily = LatoRegular,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
        }
    }
}