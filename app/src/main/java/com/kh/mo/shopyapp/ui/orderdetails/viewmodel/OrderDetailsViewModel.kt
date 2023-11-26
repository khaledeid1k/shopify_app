package com.kh.mo.shopyapp.ui.orderdetails.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.ui.order.Image
import com.kh.mo.shopyapp.model.ui.order.LineItem
import com.kh.mo.shopyapp.model.ui.order.Order
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import com.kh.mo.shopyapp.repo.mapper.convertToImage
import com.kh.mo.shopyapp.repo.mapper.convertToLineItem
import com.kh.mo.shopyapp.repo.mapper.convertToOrders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderDetailsViewModel(private var _irepo: Repo): ViewModel() {
    private val TAG = "TAG OrderDetailsViewModel"

    private val _orderList = MutableStateFlow<ApiState<List<LineItem>>>(ApiState.Loading)
    val orderList: StateFlow<ApiState<List<LineItem>>> = _orderList

    private val _imageOrders = MutableStateFlow<ApiState<List<Image>>>(ApiState.Loading)
    val imageOrders: StateFlow<ApiState<List<Image>>> = _imageOrders

    fun getOrdersById(orderID: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.getOrderById(orderID).collect {
                when (it) {
                    is ApiState.Failure -> {
                        Log.i(TAG, "Failure")
                    }
                    is ApiState.Loading -> {
                        Log.i(TAG, "llLoading")
                    }
                    is ApiState.Success -> {
                       // Log.i(TAG, "Success:${it.data.lineItems?.size}")

                        _orderList.value = ApiState.Success(it.data.convertToLineItem())
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
                        Log.i(TAG, "Image Failure")
                    }
                    is ApiState.Loading -> {
                        Log.i(TAG, "Image Loading")
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
