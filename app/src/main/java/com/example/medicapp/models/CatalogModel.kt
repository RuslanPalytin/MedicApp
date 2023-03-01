package com.example.medicapp.models

data class CatalogModel(
    val id: Int,
    val name: String,
    val description: String,
    val price: String,
    val category: String,
    val time_result: String,
    val preparation: String,
    val bio: String
)
