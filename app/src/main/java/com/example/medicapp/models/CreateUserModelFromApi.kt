package com.example.medicapp.models

data class CreateUserModelFromApi(
    val user_id: Int,
    val firstname: String,
    val lastname: String,
    val middlename: String,
    val bith: String,
    val pol: String,
    val image: String,
    val updated_at: String,
    val created_at: String,
    val id: Int,

    val errors: String = "",
    val message: String = ""
)
