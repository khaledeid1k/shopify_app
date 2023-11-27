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






    init {
        getOrders()

    }

    fun getOrders() {

        viewModelScope.launch(Dispatchers.IO) {
            _irepo.getOrdersByCustomerID( _irepo.getCustomerId()).collect {
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




}