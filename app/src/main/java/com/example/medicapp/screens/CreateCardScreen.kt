package com.example.medicapp.screens

import android.widget.Spinner
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicapp.ui.theme.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun CreateCardScreen(navController: NavController) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = Color.White, darkIcons = true)

    val name = remember { mutableStateOf("") }
    val patronymic = remember { mutableStateOf("") }
    val surname = remember { mutableStateOf("") }
    val dateBirth = remember { mutableStateOf("") }
    val gender = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp)
            .padding(top = 48.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.fillMaxWidth(0.6f),
                text = "Создание карты пациента",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = LatoRegular
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                text = "Пропустить",
                fontSize = 15.sp,
                color = BlueTextOnBoarding,
                fontFamily = LatoRegular
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Без карты пациента вы не сможете заказать анализы.",
            fontSize = 14.sp,
            color = GrayTextOnBoarding,
            fontFamily = LatoRegular
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "В картах пациентов будут храниться результаты анализов вас и ваших близких.",
            fontSize = 14.sp,
            color = GrayTextOnBoarding,
            fontFamily = LatoRegular
        )
        Spacer(modifier = Modifier.height(32.dp))
        EditText(placeholder = "Имя", enterText = name)
        Spacer(modifier = Modifier.height(24.dp))
        EditText(placeholder = "Отчество", enterText = patronymic)
        Spacer(modifier = Modifier.height(24.dp))
        EditText(placeholder = "Фамилия", enterText = surname)
        Spacer(modifier = Modifier.height(24.dp))
        EditText(placeholder = "Дата рождения", enterText = dateBirth)
        Spacer(modifier = Modifier.height(24.dp))
        EditText(placeholder = "Пол", enterText = gender)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clip(shape = RoundedCornerShape(10.dp)),
            colors = ButtonDefaults.buttonColors(backgroundColor = ButtonEnabledColor),
            onClick = { /*TODO*/ },
            content = {
                Text(
                    text = "Создать",
                    fontWeight = FontWeight.Bold,
                    fontFamily = LatoRegular,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        )
    }
}

@Composable
private fun EditText(placeholder: String, enterText: MutableState<String>) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp)),
        value = enterText.value,
        onValueChange = { newText -> enterText.value = newText },
        placeholder = {
            Text(
                text = placeholder,
                fontFamily = LatoRegular,
                fontSize = 14.sp
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
}