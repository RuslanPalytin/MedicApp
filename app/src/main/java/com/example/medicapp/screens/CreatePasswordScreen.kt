package com.example.medicapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.WhitePoint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicapp.R
import com.example.medicapp.navigation.OnBoardingScreenSealed
import com.example.medicapp.ui.theme.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun CreatePasswordScreen(navController: NavController) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = Color.White, darkIcons = true)

    val countClick = remember { mutableStateOf(0) }
    val whiteColor = remember { mutableStateOf(Color.White) }
    val blueColor = remember { mutableStateOf(ActiveColorIndicationOnBoarding) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(top = 54.dp)
    ) {
        Text(
            text = "Пропустить",
            fontFamily = LatoRegular,
            fontSize = 15.sp,
            color = BlueTextOnBoarding,
            modifier = Modifier
                .padding(end = 20.dp)
                .align(End)
                .clickable {
                    navController.navigate(OnBoardingScreenSealed.CreateCardScreen.route)
                }
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Создайте пароль",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = LatoRegular,
            modifier = Modifier.align(CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Для защиты ваших персональных данных",
            color = GrayTextOnBoarding,
            fontSize = 15.sp,
            fontFamily = LatoRegular,
            modifier = Modifier.align(CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(56.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .align(CenterHorizontally), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OneCirclePassword(background = if (countClick.value < 1) whiteColor else blueColor)
            OneCirclePassword(background = if (countClick.value < 2) whiteColor else blueColor)
            OneCirclePassword(background = if (countClick.value < 3) whiteColor else blueColor)
            OneCirclePassword(background = if (countClick.value < 4) whiteColor else blueColor)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp)
            .padding(horizontal = 44.dp),
        contentAlignment = BottomCenter
    ) {
        Column {
            NumberButtonsRow(
                firstNumber = 1,
                secondNumber = 2,
                thirdNumber = 3,
                click = countClick
            )
            Spacer(modifier = Modifier.height(24.dp))
            NumberButtonsRow(
                firstNumber = 4,
                secondNumber = 5,
                thirdNumber = 6,
                click = countClick
            )
            Spacer(modifier = Modifier.height(24.dp))
            NumberButtonsRow(
                firstNumber = 7,
                secondNumber = 8,
                thirdNumber = 9,
                click = countClick
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 84.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NumericKeypad(number = 0, click = countClick)
                Spacer(modifier = Modifier.width(46.dp))
                Image(
                    modifier = Modifier
                        .width(35.dp)
                        .height(24.dp)
                        .clickable(countClick.value != 0) {
                            countClick.value--
                        },
                    painter = painterResource(id = R.drawable.create_password_delete_icon),
                    contentDescription = null
                )
            }
        }
    }

    if(countClick.value == 4) {
        navController.navigate(OnBoardingScreenSealed.CreateCardScreen.route)
    }
}

@Composable
private fun NumberButtonsRow(
    firstNumber: Int,
    secondNumber: Int,
    thirdNumber: Int,
    click: MutableState<Int>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        NumericKeypad(number = firstNumber, click = click)
        Spacer(modifier = Modifier.width(24.dp))
        NumericKeypad(number = secondNumber, click = click)
        Spacer(modifier = Modifier.width(24.dp))
        NumericKeypad(number = thirdNumber, click = click)
    }
}

@Composable
private fun NumericKeypad(number: Int, click: MutableState<Int>) {
    FloatingActionButton(
        modifier = Modifier
            .size(80.dp),
        onClick = { click.value++ },
        backgroundColor = BackgroundTextField,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 4.dp
        ),
    ) {
        Text(text = number.toString(), fontSize = 24.sp)
    }
}

@Composable
private fun OneCirclePassword(background: MutableState<Color>) {
    Card(
        shape = CircleShape,
        modifier = Modifier.size(16.dp),
        border = BorderStroke(width = 1.dp, color = ActiveColorIndicationOnBoarding),
        backgroundColor = background.value,
        content = {

        }
    )
}