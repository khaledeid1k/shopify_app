package com.kh.mo.shopyapp.ui.category.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.entity.FavoriteEntity
import com.kh.mo.shopyapp.model.ui.allproducts.Product
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import com.kh.mo.shopyapp.repo.mapper.convertToAllProducts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(private var _irepo: Repo) : ViewModel() {
    private val TAG = "TAG CategoryViewModel"
    private val _product = MutableStateFlow<ApiState<List<Product>>>(ApiState.Loading)
    val product: StateFlow<ApiState<List<Product>>> = _product

    private val _productCollection = MutableStateFlow<ApiState<List<Product>>>(ApiState.Loading)
    val productCollection: StateFlow<ApiState<List<Product>>> = _productCollection

    private val _filterProductCollection = MutableStateFlow<ApiState<List<Product>>>(ApiState.Loading)
    val filterProductCollection: StateFlow<ApiState<List<Product>>> = _filterProductCollection



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
                        _product.value = ApiState.Loading
                        Log.i(TAG, "products:Loading")
                    }

                    is ApiState.Success -> {
                        Result.success(it.data)
                        _product.value = ApiState.Success(it.data.convertToAllProducts())
                    }
                }
            }
        }
    }

    fun getCollectionProducts(collectionId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.getProductsByCollection(collectionId).collect {
                _productCollection.value=it
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
                        _filterProductCollection.value = ApiState.Loading
                        Log.i(TAG, "products:Loading")
                    }

                    is ApiState.Success -> {
                        Result.success(it.data)
                        _filterProductCollection.value = ApiState.Success(it.data.convertToAllProducts())

                    }
                }
            }
        }
    }

    fun saveFavorite(product: Product){
        viewModelScope.launch {
            _irepo.saveFavorite(product)
        }
    }

    fun deleteFavorite(productId: Long){
        viewModelScope.launch {
            _irepo.deleteFavorite(productId)
        }
    }



}