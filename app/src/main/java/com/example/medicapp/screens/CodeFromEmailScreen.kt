package com.example.medicapp.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicapp.R
import com.example.medicapp.api.ApiService
import com.example.medicapp.common.MainAlertDialog
import com.example.medicapp.common.isNetworkAvailable
import com.example.medicapp.models.AuthorizationUserModel
import com.example.medicapp.models.TokenModel
import com.example.medicapp.navigation.OnBoardingScreenSealed
import com.example.medicapp.storage.SharedPreference
import com.example.medicapp.ui.theme.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CodeFromEmailScreen(navController: NavController, email: String) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = Color.White, darkIcons = true)

    val time = remember { mutableStateOf(30) }
    val numbersList = listOf(
        remember { mutableStateOf("") },
        remember { mutableStateOf("") },
        remember { mutableStateOf("") },
        remember { mutableStateOf("") }
    )
    val responseCode = remember { mutableStateOf(200) }
    val openDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (responseCode.value != 200) openDialog.value = true

    if (openDialog.value) {
        MainAlertDialog(
            openDialog = openDialog,
            responseCode = responseCode,
            titleTextIf = "Код",
            titleTextElse = "Ошибка подключения",
            contentTextIf = "Введен неверный код, проверьте написание кода, или дождитесь полчение нового кода",
            contentTextElse = "Проверте подклчение к интернету"
        )

        numbersList.forEach { number ->
            number.value = mutableStateOf("").value
        }
    }

    if (openDialog.value && time.value == 0) {

        MainAlertDialog(
            openDialog = openDialog,
            responseCode = responseCode,
            time = time,
            titleTextIf = "Ошибка в почте или пароле",
            titleTextElse = "Ошибка подключения",
            contentTextIf = "Проверьте правильность написания пароля и почты",
            contentTextElse = "Проверте подклчение к интернету"
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White), contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Введите код из E-mail",
                fontSize = 17.sp,
                fontFamily = LatoRegular,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(24.dp))

            Row {
                EditTextCode(number = numbersList[0])
                Spacer(modifier = Modifier.width(16.dp))
                EditTextCode(number = numbersList[1])
                Spacer(modifier = Modifier.width(16.dp))
                EditTextCode(number = numbersList[2])
                Spacer(modifier = Modifier.width(16.dp))
                EditTextCode(number = numbersList[3])
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Отправить код повторно можно будет через ${time.value} секунд",
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                color = GrayTextOnBoarding,
                fontFamily = LatoRegular,
            )
            LaunchedEffect(key1 = time.value) {
                while (true) {
                    delay(1000)
                    time.value--
                    if (time.value == 0) {
                        authUser(
                            userModel = AuthorizationUserModel(
                                email = email,
                            ),
                            context = context,
                            code = responseCode
                        )
                        if (responseCode.value == 200 || responseCode.value == 204) {
                            time.value = 30
                        }
                    }
                    if (numbersList.last().value != "") {
                        var code = ""
                        numbersList.forEach { number -> code += number.value }
                        signIn(
                            userModel = AuthorizationUserModel(
                                email = email,
                                code = code
                            ),
                            context = context,
                            navController = navController,
                            code = responseCode
                        )
                    }
                }
            }
        }
    }
    Image(
        painter = painterResource(id = R.drawable.back_button_icon),
        contentDescription = null,
        modifier = Modifier
            .padding(top = 42.dp, start = 26.dp)
            .size(32.dp)
            .clickable {
                navController.popBackStack()
            }
    )
}

@Composable
private fun EditTextCode(number: MutableState<String>) {
    OutlinedTextField(
        modifier = Modifier
            .padding(start = 8.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .size(52.dp),
        value = number.value,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = { newText -> if (newText.length <= 1) number.value = newText },
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

private fun signIn(
    userModel: AuthorizationUserModel,
    context: Context,
    navController: NavController? = null,
    code: MutableState<Int> = mutableStateOf(0)
) {
    if (isNetworkAvailable(context)) {
        ApiService.retrofit.signIn(email = userModel.email, code = userModel.code)
            .enqueue(object : Callback<TokenModel> {
                override fun onResponse(call: Call<TokenModel>, response: Response<TokenModel>) {

                    if (response.isSuccessful) {
                        SharedPreference(context).saveToken(response.body()?.token.toString())
                        navController?.navigate(OnBoardingScreenSealed.CreatePasswordScreen.route)
                        code.value = response.code()
                    } else {
                        code.value = response.code()
                    }
                }

                override fun onFailure(call: Call<TokenModel>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }

            })
    } else {
        code.value = 204
    }
}