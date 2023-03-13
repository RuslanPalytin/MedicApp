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
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombination = listOf(
            name,
            "${name.first()}"
        )
        return matchingCombination.any {
            it.contains(query, ignoreCase = true)
        }
    }
}
