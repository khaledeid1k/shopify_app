package com.kh.mo.shopyapp.ui.category.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.response.allproducts.AllProductsResponse
import com.kh.mo.shopyapp.model.response.barnds.SmartCollection
import com.kh.mo.shopyapp.model.ui.allproducts.Products
import com.kh.mo.shopyapp.model.ui.productsofbrand.Product
import com.kh.mo.shopyapp.remote.ApiSate
import com.kh.mo.shopyapp.repo.Repo
import com.kh.mo.shopyapp.repo.maper.convertToAllProducts
import com.kh.mo.shopyapp.repo.maper.convertToProduct
import com.kh.mo.shopyapp.repo.maper.convertToSmartCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(private var _irepo: Repo) : ViewModel() {
    private val TAG = "TAG CategoryViewModel"
    private val _products = MutableStateFlow<ApiSate<List<Products>>>(ApiSate.Loading)
    val products: StateFlow<ApiSate<List<Products>>> = _products

    init {
        getSubCategories()
    }
    fun getSubCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.getAllProducts().collect {
                when (it) {
                    is ApiSate.Failure -> {
                        Log.i(TAG, "products:Fail")
                    }
                    is ApiSate.Loading -> {
                        _products.value = ApiSate.Loading
                        Log.i(TAG, "products:Loading")
                    }
                    is ApiSate.Success -> {
                        Result.success(it.data)
                        _products.value = ApiSate.Success(it.data.convertToAllProducts())
                    }
                }


            }
        }
    }
}