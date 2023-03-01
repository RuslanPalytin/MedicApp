package com.example.medicapp.screens.bottomnav.analise.uiitems

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.medicapp.models.StockAndNewsModel
import com.example.medicapp.ui.theme.*

@Composable
fun StockAndNewsItem(item: StockAndNewsModel, count: Int) {

    val brushOne = listOf(AnaliseNewsColorTwo, AnaliseNewsColorOne)
    val brushTwo = listOf(AnaliseNewsColorFour, AnaliseNewsColorThree)

    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(12.dp))
            .width(270.dp)
            .height(152.dp)
            .background(brush = Brush.linearGradient(colors = if (count % 2 == 0) brushOne else brushTwo))

    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .padding(top = 16.dp, start = 16.dp)
            ) {
                Text(
                    text = item.name,
                    color = Color.White,
                    fontFamily = LatoRegular,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = item.description,
                    color = Color.White,
                    fontFamily = LatoRegular,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = item.price + " ₽",
                    color = Color.White,
                    fontFamily = LatoRegular,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                AsyncImage(
                    modifier = Modifier.background(Color.Transparent),
                    model = item.image,
                    contentDescription = null
                )
            }

        }
    }
}