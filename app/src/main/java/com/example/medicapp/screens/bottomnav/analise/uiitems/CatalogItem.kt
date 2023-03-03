package com.example.medicapp.screens.bottomnav.analise.uiitems

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicapp.models.CatalogModel
import com.example.medicapp.ui.theme.ButtonEnabledColor
import com.example.medicapp.ui.theme.GrayTextOnBoarding
import com.example.medicapp.ui.theme.LatoRegular

@Composable
fun CatalogItem(item: CatalogModel) {
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
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = ButtonEnabledColor),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(vertical = 10.dp),
                        text = "Добавить",
                        fontFamily = LatoRegular,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}