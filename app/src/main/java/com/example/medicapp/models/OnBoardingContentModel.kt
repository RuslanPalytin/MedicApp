package com.example.medicapp.models

import androidx.annotation.DrawableRes

data class OnBoardingContentModel(
    val title: String,
    val content: String,
    @DrawableRes val icon: Int
)
