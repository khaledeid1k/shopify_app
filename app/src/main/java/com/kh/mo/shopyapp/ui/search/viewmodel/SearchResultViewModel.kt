package com.kh.mo.shopyapp.ui.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.ui.allproducts.Product
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchResultViewModel(private val repo: Repo) : ViewModel() {
    private val _products = MutableStateFlow<ApiState<List<Product>>>(ApiState.Loading)
    val products: StateFlow<ApiState<List<Product>>> = _products

    private fun getSearchResult() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllProducts().collect {
                _products.value = it
            }
        }
    }

    init {
        getSearchResult()
    }
}

