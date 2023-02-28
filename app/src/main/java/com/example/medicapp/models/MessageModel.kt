package com.example.medicapp.models

data class MessageModel(
    val message: String = "",
    val errors: List<String> = listOf()
)
