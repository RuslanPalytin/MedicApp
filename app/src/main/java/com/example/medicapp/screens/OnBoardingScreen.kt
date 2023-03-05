package com.example.medicapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicapp.models.OnBoardingContentModel
import com.example.medicapp.R
import com.example.medicapp.navigation.OnBoardingScreenSealed
import com.example.medicapp.ui.theme.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingScreen(navController: NavController) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = Color.White, darkIcons = true)

    val contentList = listOf(
        OnBoardingContentModel(
            title = "Анализы",
            content = "Экспресс сбор и получение проб",
            icon = R.drawable.on_boarding_one_icon
        ),
        OnBoardingContentModel(
            title = "Уведомления",
            content = "Вы быстро узнаете о результатах",
            icon = R.drawable.on_boarding_two_icon
        ),
        OnBoardingContentModel(
            title = "Мониторинг",
            content = "Наши врачи всегда наблюдают за вашими показателями здоровья",
            icon = R.drawable.on_boarding_three_icon
        )
    )
    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(top = 32.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .align(alignment = Top)
                    .padding(start = 30.dp)
                    .clickable {
                        navController.navigate(OnBoardingScreenSealed.LoginAndRegistrationScreen.route)
                    },
                text = if(pagerState.currentPage == 2) "Завершить" else "Пропустить" ,
                color = BlueTextOnBoarding,
                fontSize = 20.sp,
                fontFamily = LatoRegular
            )
            Image(
                modifier = Modifier.size(185.dp),
                painter = painterResource(id = R.drawable.on_boarding__plus_icon),
                contentDescription = null,
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = contentList[pagerState.currentPage].title,
            modifier = Modifier.align(CenterHorizontally),
            color = GreenTextOnBoarding,
            fontFamily = LatoRegular,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = contentList[pagerState.currentPage].content,
            modifier = Modifier.align(CenterHorizontally),
            textAlign = TextAlign.Center,
            color = GrayTextOnBoarding,
            fontFamily = LatoRegular,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(62.dp))
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(CenterHorizontally),
            activeColor = ActiveColorIndicationOnBoarding,
            inactiveColor = Color.White,
            indicatorHeight = 12.dp,
            indicatorWidth = 12.dp,
        )
        Spacer(modifier = Modifier.height(106.dp))
        HorizontalPager(
            count = 3,
            state = pagerState,
        ) { page ->

            Image(
                painter = painterResource(id = contentList[page].icon),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                alignment = Center
            )
        }
    }
}