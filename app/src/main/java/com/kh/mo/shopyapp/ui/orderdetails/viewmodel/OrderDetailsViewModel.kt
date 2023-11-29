package com.kh.mo.shopyapp.ui.orderdetails.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.ui.allproducts.Product
import com.kh.mo.shopyapp.model.ui.orderdetails.LineItem
import com.kh.mo.shopyapp.model.ui.orderdetails.Order
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import com.kh.mo.shopyapp.repo.mapper.convertToOrders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderDetailsViewModel(private var _irepo: Repo) : ViewModel() {
    private val TAG = "TAG OrderDetailsViewModel"

    private val _orderList = MutableStateFlow<ApiState<List<LineItem>>>(ApiState.Loading)
    val orderList: StateFlow<ApiState<List<LineItem>>> = _orderList

    private val _orders = MutableStateFlow<ApiState<List<Order>>>(ApiState.Loading)
    val orders: StateFlow<ApiState<List<Order>>> = _orders

    private val _product = MutableStateFlow<ApiState<Product>>(ApiState.Loading)
    val product: StateFlow<ApiState<Product>> = _product

    init {
        getOrders()

    }

    fun getOrdersById(orderID: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.getOrderById(orderID).collect {
                _orderList.value = it
            }
        }
    }

    fun getOrders() {

        viewModelScope.launch(Dispatchers.IO) {
            _irepo.getOrdersByCustomerID(_irepo.getCustomerId()).collect {
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

    fun getProduct(productId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.getSingleProduct(productId).collect {
                _product.value = it
            }
        }
    }


}
