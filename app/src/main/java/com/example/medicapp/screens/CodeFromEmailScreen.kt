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
import com.example.medicapp.models.AuthorizationUserModel
import com.example.medicapp.models.TokenModel
import com.example.medicapp.navigation.OnBoardingScreenSealed
import com.example.medicapp.storage.SharedPreference
import com.example.medicapp.ui.theme.BackgroundTextField
import com.example.medicapp.ui.theme.BorderColorTextField
import com.example.medicapp.ui.theme.GrayTextOnBoarding
import com.example.medicapp.ui.theme.LatoRegular
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CodeFromEmailScreen(navController: NavController) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = Color.White, darkIcons = true)

    var time by remember { mutableStateOf(60) }
    val numbersList = listOf(
        remember { mutableStateOf("") },
        remember { mutableStateOf("") },
        remember { mutableStateOf("") },
        remember { mutableStateOf("") }
    )
    val context = LocalContext.current

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

            Row() {
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
                text = "Отправить код повторно можно будет через $time секунд",
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                color = GrayTextOnBoarding,
                fontFamily = LatoRegular,
            )
            LaunchedEffect(key1 = time) {
                while (true) {
                    delay(1000)
                    time--
                    if (time == 0) {
                        authUser(
                            userModel = AuthorizationUserModel(
                                email = SharedPreference(context).readEmail(),
                            ),
                            context = context
                        )

                        time = 60
                    }
                    if (numbersList.last().value != "") {
                        var code = ""
                        numbersList.forEach { number -> code += number.value }
                        signIn(
                            userModel = AuthorizationUserModel(
                                email = SharedPreference(context).readEmail(),
                                code = code
                            ),
                            context = context,
                            navController = navController
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
    navController: NavController? = null
) {
    ApiService.retrofit.signIn(email = userModel.email, code = userModel.code)
        .enqueue(object : Callback<TokenModel> {
            override fun onResponse(call: Call<TokenModel>, response: Response<TokenModel>) {
                when (response.code()) {
                    200 -> {
                        SharedPreference(context).saveToken(response.body()?.token.toString())
                        navController?.navigate(OnBoardingScreenSealed.CreatePasswordScreen.route)
                    }
                    422 -> Toast.makeText(context, response.body()?.errors, Toast.LENGTH_SHORT)
                        .show()
                    else -> throw IllegalStateException()
                }
            }

            override fun onFailure(call: Call<TokenModel>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

        })
}