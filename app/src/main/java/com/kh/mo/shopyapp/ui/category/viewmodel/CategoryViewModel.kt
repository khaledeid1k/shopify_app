package com.kh.mo.shopyapp.ui.category.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.ui.allproducts.Products
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import com.kh.mo.shopyapp.repo.mapper.convertToAllProducts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(private var _irepo: Repo) : ViewModel() {
    private val TAG = "TAG CategoryViewModel"
    private val _products = MutableStateFlow<ApiState<List<Products>>>(ApiState.Loading)
    val products: StateFlow<ApiState<List<Products>>> = _products

    private val _productsCollection = MutableStateFlow<ApiState<List<Products>>>(ApiState.Loading)
    val productsCollection: StateFlow<ApiState<List<Products>>> = _productsCollection

    private val _filterProductsCollection = MutableStateFlow<ApiState<List<Products>>>(ApiState.Loading)
    val filterProductsCollection: StateFlow<ApiState<List<Products>>> = _filterProductsCollection

    init {
        getSubCategories()

    }

    fun getSubCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.getAllProducts().collect {
                when (it) {
                    is ApiState.Failure -> {
                        Log.i(TAG, "products:Fail")
                    }

                    is ApiState.Loading -> {
                        _products.value = ApiState.Loading
                        Log.i(TAG, "products:Loading")
                    }

                    is ApiState.Success -> {
                        Result.success(it.data)
                        _products.value = ApiState.Success(it.data.convertToAllProducts())
                    }
                }
            }
        }
    }

    fun getCollectionProducts(collectionId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.getProductsByCollection(collectionId).collect {
                when (it) {
                    is ApiState.Failure -> {
                        Log.i(TAG, "products:Fail")
                    }

                    is ApiState.Loading -> {
                        _productsCollection.value = ApiState.Loading
                        Log.i(TAG, "products:Loading")
                    }

                    is ApiState.Success -> {
                        Result.success(it.data)
                        _productsCollection.value = ApiState.Success(it.data.convertToAllProducts())
                    }
                }
            }
        }
    }


    fun filterProductsBySubCollection(collectionId: Long,productType:String) {
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.filterProductsBySubCollection(collectionId,productType).collect {
                when (it) {
                    is ApiState.Failure -> {
                        Log.i(TAG, "products:Fail")
                    }

                    is ApiState.Loading -> {
                        _filterProductsCollection.value = ApiState.Loading
                        Log.i(TAG, "products:Loading")
                    }

                    is ApiState.Success -> {
                        Result.success(it.data)
                        _filterProductsCollection.value = ApiState.Success(it.data.convertToAllProducts())

                    }
                }
            }
        }
    }
}