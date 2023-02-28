package com.example.medicapp.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicapp.api.ApiService
import com.example.medicapp.models.CreateUserModelFromApi
import com.example.medicapp.models.CreateUserModelInApi
import com.example.medicapp.storage.SharedPreference
import com.example.medicapp.ui.theme.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CreateCardScreen(navController: NavController) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = Color.White, darkIcons = true)

    val context = LocalContext.current
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
            onClick = {
                val userCreateModel = CreateUserModelInApi(
                    id = 1,
                    firstname = name.value,
                    lastname = surname.value,
                    middlename = patronymic.value,
                    bith = dateBirth.value,
                    pol = gender.value,
                    image = "",
                )
                createCardUser(userCardModel = userCreateModel, context = context, navController = navController)
            },
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

private fun createCardUser(
    userCardModel: CreateUserModelInApi,
    context: Context,
    navController: NavController
) {

    val token = SharedPreference(context).readToken()

    ApiService.retrofit.createCardUser(token = "Bearer $token", createUserModel = userCardModel)
        .enqueue(object : Callback<CreateUserModelFromApi> {
            override fun onResponse(
                call: Call<CreateUserModelFromApi>,
                response: Response<CreateUserModelFromApi>
            ) {
                when(response.code()) {
                    200 -> {
                        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
                    }
                    403 -> Toast.makeText(context, "Не авторизованы", Toast.LENGTH_SHORT).show()
                    422 -> Toast.makeText(context, response.body()?.errors, Toast.LENGTH_SHORT).show()
                    else -> throw IllegalStateException()
                }
            }

            override fun onFailure(call: Call<CreateUserModelFromApi>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

        })
}