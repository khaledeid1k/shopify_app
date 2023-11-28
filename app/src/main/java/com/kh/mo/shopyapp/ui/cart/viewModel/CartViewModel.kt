package com.kh.mo.shopyapp.ui.cart.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.ui.Cart
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CartViewModel(private val repo: Repo) : ViewModel() {
    private val TAG = "TAG CartViewModel"

    private val _productList = MutableStateFlow<ApiState<List<Cart>>>(ApiState.Loading)
    val productList: StateFlow<ApiState<List<Cart>>>
        get() = _productList

    fun checkIsUserLogin() = repo.checkIsUserLogin()

    fun getAllProductsInCart() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllProductsInCart().collectLatest { state ->
                _productList.value = state
            }
        }
    }

    fun deleteItem(item: Cart) {
        val list: MutableList<Cart> =
            _productList.value.toData()?.toMutableList() ?: mutableListOf()
        _productList.value = ApiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val updatedList = list.filter { it != item }
            Log.i(TAG, "deleteItem: updatedList: $updatedList")
            repo.updateCartItems(updatedList).collectLatest {
                _productList.value = it
            }
        }
    }

    fun addOneToItem(item: Cart) {
        val list: MutableList<Cart> =
            _productList.value.toData()?.toMutableList() ?: mutableListOf()
        _productList.value = ApiState.Loading
        val result = list.map {
            if (it == item)
                it.copy(quantity = it.quantity?.inc())
            else
                it
        }
        _productList.value = ApiState.Success(result)
    }

    fun subOneFromItem(item: Cart) {
        val list: MutableList<Cart> =
            _productList.value.toData()?.toMutableList() ?: mutableListOf()
        _productList.value = ApiState.Loading
        val result = list.map {
            if (it == item)
                it.copy(quantity = it.quantity?.dec())
            else
                it
        }
        _productList.value = ApiState.Success(result)
    }
}