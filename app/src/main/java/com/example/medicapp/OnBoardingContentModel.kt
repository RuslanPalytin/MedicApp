package com.example.medicapp

import androidx.annotation.DrawableRes

data class OnBoardingContentModel(
    val title: String,
    val content: String,
    @DrawableRes val icon: Int
)
