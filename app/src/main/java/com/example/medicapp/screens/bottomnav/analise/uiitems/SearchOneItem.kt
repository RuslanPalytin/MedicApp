package com.example.medicapp.screens.bottomnav.analise.uiitems

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicapp.models.CatalogModel
import com.example.medicapp.ui.theme.LatoRegular
import com.example.medicapp.ui.theme.StrokeSearchColor

@Composable
fun SearchOneItem(item: CatalogModel) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 12.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(0.6f),
                text = item.name,
                fontFamily = LatoRegular,
                fontSize = 15.sp
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${item.price} P", fontSize = 17.sp, fontFamily = LatoRegular)
                Text(text = item.time_result, fontSize = 14.sp, fontFamily = LatoRegular)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = StrokeSearchColor)
        )
    }
}