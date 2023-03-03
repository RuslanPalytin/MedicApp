package com.example.medicapp.screens.bottomnav.analise

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicapp.R
import com.example.medicapp.ui.theme.*

@Composable
fun SearchScreen() {

    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
            .padding(horizontal = 20.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .clip(shape = RoundedCornerShape(10.dp)),
                value = searchText,
                onValueChange = { newText -> searchText = newText },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.cancel_icon),
                        contentDescription = null
                    )
                },
                placeholder = {
                    Text(
                        text = "Искать анализы",
                        fontFamily = LatoRegular,
                        fontSize = 16.sp
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = GrayTextOnBoarding,
                    backgroundColor = BackgroundTextField,
                    placeholderColor = GrayTextOnBoarding,
                    focusedIndicatorColor = BorderColorTextField,
                    unfocusedIndicatorColor = BorderColorTextField
                )
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Отменить",
                fontFamily = LatoRegular,
                color = BlueTextOnBoarding,
                fontSize = 14.sp
            )
        }
    }
}