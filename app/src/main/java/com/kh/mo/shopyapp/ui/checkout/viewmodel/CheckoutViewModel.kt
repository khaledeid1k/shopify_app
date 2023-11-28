package com.kh.mo.shopyapp.ui.checkout.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.ui.Address
import com.kh.mo.shopyapp.model.ui.Cart
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CheckoutViewModel(private val repo: Repo) : ViewModel() {
    private val TAG = "TAG CheckoutViewModel"
    private val _productList = MutableStateFlow<ApiState<List<Cart>>>(ApiState.Loading)
    val productList: StateFlow<ApiState<List<Cart>>>
        get() = _productList

    private val _userAddress: MutableStateFlow<ApiState<List<Address>>> =
        MutableStateFlow(ApiState.Loading)
    val userAddress: StateFlow<ApiState<List<Address>>>
        get() = _userAddress

    fun getAllProductsInCart() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllProductsInCart().collectLatest { state ->
                _productList.value = state
            }
        }
    }

    fun getAddress() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.i(TAG, "getAddresses of user ${repo.getCustomerId()}")
            repo.getAddressesOfCustomer(repo.getCustomerId()).collectLatest { addressResponse ->
                if (addressResponse is ApiState.Success) {
                    _userAddress.value =
                        ApiState.Success(
                            addressResponse.data
                                .filter { address ->
                                    address.default == true
                                }
                        )
                }
            }
        }
    }
}