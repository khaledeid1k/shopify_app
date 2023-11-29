package com.kh.mo.shopyapp.ui.product.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.ui.allproducts.Product
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import com.kh.mo.shopyapp.repo.mapper.convertProductToFavoriteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.log

class ProductViewModel(val repo: Repo):ViewModel() {

    private val _productState = MutableSharedFlow<Boolean>()
    val productState: SharedFlow<Boolean> = _productState
    private val _addToCartState = MutableSharedFlow<ApiState<Boolean>>()
    val addToCartState: SharedFlow<ApiState<Boolean>> = _addToCartState
     fun saveFavorite(product: Product) {
        viewModelScope.launch {
            repo.saveFavorite(product.convertProductToFavoriteEntity(repo.getCustomerId()))
        }
    }

     fun deleteFavorite(productId: Long) {
        viewModelScope.launch {
            repo.deleteFavorite(productId)
        }
    }
     fun checkProductInFavorite(productId: Long) {
        viewModelScope.launch {
            repo.checkProductInFavorite(productId).collect {
             when(it){
                 is ApiState.Failure -> {}
                 is ApiState.Loading ->{}
                 is ApiState.Success -> {
                     if (it.data != 0) {
                         Log.d("TAG", "1checkProductInFavorite: ")
                         _productState.emit(true)
                     } else {
                         Log.d("TAG", "2checkProductInFavorite: ")

                         _productState.emit(false)
                     }
                 }
             }
            }

        }
    }
    fun checkCustomerId() = repo.getCustomerId() != 0L

    fun addToCart(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addProductToCart(product).collectLatest {
                _addToCartState.emit(it)
            }
        }
    }
}