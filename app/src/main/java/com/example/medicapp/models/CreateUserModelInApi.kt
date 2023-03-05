package com.example.medicapp.models

data class CreateUserModelInApi(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val middlename: String,
    var bith: String,
    val pol: String,
    val image: String,
)
