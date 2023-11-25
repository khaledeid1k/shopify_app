package com.kh.mo.shopyapp.ui.order.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.ui.order.Image
import com.kh.mo.shopyapp.model.ui.order.Order
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import com.kh.mo.shopyapp.repo.mapper.convertToImage
import com.kh.mo.shopyapp.repo.mapper.convertToOrders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderViewModel(private var _irepo: Repo) : ViewModel() {
    private val TAG = "TAG OrderViewModel"
    private val _orders = MutableStateFlow<ApiState<List<Order>>>(ApiState.Loading)
    val orders: StateFlow<ApiState<List<Order>>> = _orders

    private val _imageOrders = MutableStateFlow<ApiState<List<Image>>>(ApiState.Loading)
    val imageOrders: StateFlow<ApiState<List<Image>>> = _imageOrders




    init {


    }

    fun getOrders(customerId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.getOrdersByCustomerID(customerId).collect {
                when (it) {
                    is ApiState.Failure -> {
                        Log.i(TAG, "Failure")
                    }
                    is ApiState.Loading -> {
                        Log.i(TAG, "Loading")
                    }
                    is ApiState.Success -> {
                        Log.i(TAG, "Success:${it.data.orders?.size}")
                        _orders.value = ApiState.Success(it.data.convertToOrders())
                    }
                }
            }
        }
    }

    fun getImageOrder(productId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.getImageOrders(productId).collect {
                when (it) {
                    is ApiState.Failure -> {
                        Log.i(TAG, "Failure")
                    }
                    is ApiState.Loading -> {
                        Log.i(TAG, "Loading")
                    }
                    is ApiState.Success -> {
                        Log.i(TAG, "Success:${it.data.images.get(0).src}")
                        _imageOrders.value = ApiState.Success(it.data.convertToImage())
                    }
                }
            }
        }
    }


}