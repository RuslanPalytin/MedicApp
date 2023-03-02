package com.example.medicapp.screens

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicapp.R
import com.example.medicapp.api.ApiService
import com.example.medicapp.common.MainAlertDialog
import com.example.medicapp.common.isNetworkAvailable
import com.example.medicapp.models.AuthorizationUserModel
import com.example.medicapp.models.MessageModel
import com.example.medicapp.navigation.OnBoardingScreenSealed
import com.example.medicapp.storage.SharedPreference
import com.example.medicapp.ui.theme.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginAndRegistrationScreen(navController: NavController) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = Color.White, darkIcons = true)

    var email by remember { mutableStateOf("") }
    val code = remember { mutableStateOf(200) }
    val openDialog = remember { mutableStateOf(false) }
    var isActiveClick by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (code.value != 200) openDialog.value = true

    if (openDialog.value) {

        MainAlertDialog(
            openDialog = openDialog,
            responseCode = code,
            titleTextIf = "E-mail",
            titleTextElse = "Ошибка подключения",
            contentTextIf = "Ошибка ввода почты",
            contentTextElse = "Проверте подклчение к интернету"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(top = 100.dp)
            .padding(horizontal = 20.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.hello_icon),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Добро пожаловать!",
                fontFamily = LatoRegular,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(26.dp))
        Text(
            text = "Войдите, чтобы пользоваться функциями приложения",
            fontFamily = LatoRegular,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(64.dp))
        Text(
            text = "Вход по E-mail",
            color = GrayTextOnBoarding,
            fontFamily = LatoRegular,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp)),
            value = email,
            onValueChange = { newText -> email = newText },
            placeholder = {
                Text(
                    text = "example@mail.ru",
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
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(shape = RoundedCornerShape(10.dp)),
            enabled = isActiveClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ButtonEnabledColor,
                disabledBackgroundColor = ButtonDisabledColor
            ),
            onClick = {
                authUser(
                    userModel = AuthorizationUserModel(email = email),
                    context = context,
                    navController = navController,
                    code = code
                )
            },
            content = {
                Text(
                    text = "Далее",
                    fontFamily = LatoRegular,
                    fontSize = 16.sp,
                    color = Color.White
                )
                isActiveClick = Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = "Или войдите с помощью",
                fontFamily = LatoRegular,
                fontSize = 14.sp,
                color = GrayTextOnBoarding
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .border(width = 1.dp, color = BorderColorTextField),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                onClick = { /*TODO*/ },
                content = {
                    Text(
                        text = "Войти с Яндекс",
                        fontWeight = FontWeight.Bold,
                        fontFamily = LatoRegular,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
            )
        }
    }
}

fun authUser(
    userModel: AuthorizationUserModel,
    context: Context,
    navController: NavController? = null,
    code: MutableState<Int> = mutableStateOf(0),
) {
    if (isNetworkAvailable(context)) {
        ApiService.retrofit.sendCode(userModel.email).enqueue(object : Callback<MessageModel> {
            override fun onResponse(call: Call<MessageModel>, response: Response<MessageModel>) {

                if (response.isSuccessful) {
                    navController?.navigate(
                        OnBoardingScreenSealed.CodeFromEmailScreen.passEmail(
                            email = userModel.email
                        )
                    )
                    code.value = response.code()
                } else {
                    code.value = response.code()
                }
            }

            override fun onFailure(call: Call<MessageModel>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    } else {
        code.value = 204
    }
}