package com.example.medicapp.screens.bottomnav.profile

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicapp.R
import com.example.medicapp.api.ApiService
import com.example.medicapp.models.CreateUserModelFromApi
import com.example.medicapp.models.CreateUserModelInApi
import com.example.medicapp.storage.DbHandler
import com.example.medicapp.storage.SharedPreference
import com.example.medicapp.ui.theme.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ProfileScreen(navController: NavController) {

    val context = LocalContext.current
    val db = DbHandler(context).getUserDate()
    val firstName = remember { mutableStateOf("") }
    val middleName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val dateBirth = remember { mutableStateOf("") }
    val gender = remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    var isActiveButton by remember { mutableStateOf(false) }

    isActiveButton = firstName.value != "" || middleName.value != "" ||
            lastName.value != "" || dateBirth.value != "" ||
            gender.value != ""

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 32.dp)
            .verticalScroll(state = scrollState, enabled = true),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (db.firstname == "" || db.lastname == "" || db.middlename == "" || db.bith == "" || db.pol == "") {
            Text(
                modifier = Modifier.fillMaxWidth(0.6f).align(Start),
                text = "Создание карты пациента",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = LatoRegular
            )
        } else {

            firstName.value = db.firstname
            middleName.value = db.middlename
            lastName.value = db.lastname
            dateBirth.value = db.bith
            gender.value = db.pol

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Карта пациента",
                    fontFamily = LatoRegular,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(7.dp))
                Box(
                    modifier = Modifier
                        .width(148.dp)
                        .height(123.dp)
                        .clip(shape = RoundedCornerShape(60.dp))
                        .background(color = BorderColorTextField),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.photo_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .width(53.dp)
                            .height(48.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = "Без карты пациента вы не сможете заказать анализы. " +
                    "В картах пациентов будут храниться результаты анализов " +
                    "вас и ваших близких.",
            fontFamily = LatoRegular,
            fontSize = 14.sp,
            color = GrayTextOnBoarding,
            textAlign = TextAlign.Justify,
            lineHeight = 20.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Spacer(modifier = Modifier.height(32.dp))
        EditText(placeholder = "Имя", enterText = firstName)
        Spacer(modifier = Modifier.height(24.dp))
        EditText(placeholder = "Отчество", enterText = middleName)
        Spacer(modifier = Modifier.height(24.dp))
        EditText(placeholder = "Фамилия", enterText = lastName)
        Spacer(modifier = Modifier.height(24.dp))
        EditText(placeholder = "Дата рождения", enterText = dateBirth)
        Spacer(modifier = Modifier.height(24.dp))
        EditText(
            placeholder = "Пол",
            enterText = gender,
            icon = R.drawable.dpor_down_menu_icon
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clip(shape = RoundedCornerShape(10.dp)),
            enabled = isActiveButton,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ButtonEnabledColor,
                disabledBackgroundColor = ButtonDisabledColor
            ),
            onClick = {
                val _db = DbHandler(context)
                val oldUserDate = _db.getUserDate()
                val userCreateModel = CreateUserModelInApi(
                    id = 1,
                    firstname = firstName.value,
                    lastname = lastName.value,
                    middlename = middleName.value,
                    bith = dateBirth.value,
                    pol = gender.value,
                    image = "",
                )

                updateProfileUser(
                    userCardModel = userCreateModel,
                    context = context,
                    navController = navController
                )
                _db.updateUserData(oldModel = oldUserDate, newModel = userCreateModel)
            },
            content = {
                Text(
                    text = "Сохранить",
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
private fun EditText(
    placeholder: String,
    enterText: MutableState<String>,
    @DrawableRes icon: Int? = null,
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
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
            ),
            enabled = icon == null,
            trailingIcon = {
                if (icon != null) {
                    Icon(
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { expanded = true },
                        painter = painterResource(id = icon),
                        contentDescription = null
                    )
                }
            }
        )
        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 20.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            Text(
                text = "Мужской",
                fontSize = 18.sp,
                fontFamily = LatoRegular,
                modifier = Modifier.clickable {
                    enterText.value = "Мужской"
                    expanded = false
                }
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Женский",
                fontSize = 18.sp,
                fontFamily = LatoRegular,
                modifier = Modifier.clickable {
                    enterText.value = "Женский"
                    expanded = false
                }
            )
        }
    }


}

private fun updateProfileUser(
    userCardModel: CreateUserModelInApi,
    context: Context,
    navController: NavController
) {
    val token = SharedPreference(context).readToken()

    ApiService.retrofit.updateCardUser("Bearer $token", userCardModel)
        .enqueue(object : Callback<CreateUserModelFromApi> {
            override fun onResponse(
                call: Call<CreateUserModelFromApi>,
                response: Response<CreateUserModelFromApi>
            ) {
                when (response.code()) {
                    200 -> {
                        Toast.makeText(context, "Данные сохранены!", Toast.LENGTH_SHORT).show()
                    }
                    403 -> Toast.makeText(context, "Не авторизованы", Toast.LENGTH_SHORT).show()
                    422 -> Toast.makeText(context, "Ошибку возвращает сервер", Toast.LENGTH_SHORT)
                        .show()
                    else -> throw IllegalStateException()
                }
            }

            override fun onFailure(call: Call<CreateUserModelFromApi>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

        })
}