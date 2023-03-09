package com.example.medicapp.screens.bottomnav.analise.uiitems

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicapp.R
import com.example.medicapp.ui.theme.BackgroundTextField
import com.example.medicapp.ui.theme.LatoRegular

@Composable
fun ShoppingCartItem(name: String, price: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 1.dp,
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = name,
                    fontFamily = LatoRegular,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(0.7f)
                )
                Image(
                    painter = painterResource(id = R.drawable.cancel_icon),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(modifier = Modifier.fillMaxWidth(0.5f)) {
                    Text(text = "$price ₽", fontFamily = LatoRegular, fontSize = 17.sp)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "1 пациент", fontFamily = LatoRegular, fontSize = 15.sp)
                    Spacer(modifier = Modifier.width(16.dp))
                    Card(
                        modifier = Modifier
                            .width(64.dp)
                            .height(32.dp),
                        backgroundColor = BackgroundTextField,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.minus_icon),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )

                            Image(
                                painter = painterResource(id = R.drawable.plus_icon),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}