package com.example.medicapp.screens.bottomnav.analise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicapp.models.CatalogModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class SearchViewModel(private val analiseList: List<CatalogModel>) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _analise =  MutableStateFlow(analiseList)

    val analise = searchText
        .debounce(1000L)
        .onEach { _isSearching.value = true }
        .combine(_analise) { text, analis ->
            if(text.isBlank()) {
                analis
            } else {
                delay(2000)
                analis.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .onEach { _isSearching.value = false }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _analise.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}