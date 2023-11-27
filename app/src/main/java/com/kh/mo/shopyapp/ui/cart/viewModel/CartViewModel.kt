package com.kh.mo.shopyapp.ui.cart.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.request.CustomerDraftRequest
import com.kh.mo.shopyapp.model.request.DraftOrderDetailsRequest
import com.kh.mo.shopyapp.model.request.DraftOrderRequest
import com.kh.mo.shopyapp.model.ui.Cart
import com.kh.mo.shopyapp.model.ui.DraftOrder
import com.kh.mo.shopyapp.model.ui.order.LineItem
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import com.kh.mo.shopyapp.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CartViewModel(private val repo: Repo) : ViewModel() {
    private val TAG = "TAG CartViewModel"
    private val _draftCartId = MutableStateFlow<ApiState<String>>(ApiState.Loading)
    val draftCartId: StateFlow<ApiState<String>> = _draftCartId

    private val _productList = MutableStateFlow<ApiState<List<Cart>>>(ApiState.Loading)
    val productList: StateFlow<ApiState<List<Cart>>>
        get() = _productList

    fun checkIsUserLogin() = repo.checkIsUserLogin()

    fun getAllProductsInCart() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllProductsInCart().collectLatest {state ->
                _productList.value = state
            }
        }
    }
}