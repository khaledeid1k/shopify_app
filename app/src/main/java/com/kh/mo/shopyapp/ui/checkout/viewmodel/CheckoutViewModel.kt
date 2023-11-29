package com.kh.mo.shopyapp.ui.checkout.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.request.CustomerDraftRequest
import com.kh.mo.shopyapp.model.request.DraftOrderDetailsRequest
import com.kh.mo.shopyapp.model.request.DraftOrderRequest
import com.kh.mo.shopyapp.model.request.order.BillingAddress
import com.kh.mo.shopyapp.model.request.order.CreateOrderRequest
import com.kh.mo.shopyapp.model.request.order.LineItem
import com.kh.mo.shopyapp.model.request.order.Order
import com.kh.mo.shopyapp.model.request.order.OrderDiscountCode
import com.kh.mo.shopyapp.model.request.order.ShippingAddress
import com.kh.mo.shopyapp.model.response.ads.DiscountCode
import com.kh.mo.shopyapp.model.response.ads.PriceRuleResponse
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

    private val _priceRule: MutableStateFlow<ApiState<PriceRuleResponse>> =
        MutableStateFlow(ApiState.Loading)
    val priceRule: StateFlow<ApiState<PriceRuleResponse>>
        get() = _priceRule
    private val _orderCreated = MutableStateFlow<ApiState<Boolean?>>(ApiState.Loading)
    val orderCreated: StateFlow<ApiState<Boolean?>> = _orderCreated


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

    fun getPriceRule(priceRuleId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getPriceRule(priceRuleId).collectLatest {
                _priceRule.value = it
            }
        }
    }

    fun createOrder(discountCode: DiscountCode?) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.createOrder(createOrderRequestObject(discountCode)).collectLatest {
                when(it) {
                    is ApiState.Failure -> {
                        Log.i(TAG, "createOrder: failure: ${it.msg}")
                        _orderCreated.value = ApiState.Failure(it.msg)
                    }
                    ApiState.Loading -> {_orderCreated.value = ApiState.Loading}
                    is ApiState.Success -> {
                        repo.clearDraftCart(
                            DraftOrderRequest(
                                DraftOrderDetailsRequest(
                                    customer= CustomerDraftRequest(
                                        repo.getCustomerId()
                                    )
                                )
                            )
                        ).collectLatest {clearResult ->
                            when (clearResult) {
                                is ApiState.Failure -> {
                                    Log.i(TAG, "clearDraft: failure: ${clearResult.msg}")
                                    _orderCreated.value = ApiState.Failure(clearResult.msg)
                                }
                                ApiState.Loading -> {_orderCreated.value = ApiState.Loading}
                                is ApiState.Success -> {
                                    if (clearResult.data == "cleared") {
                                        _productList.value = ApiState.Success(emptyList())
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun createOrderRequestObject(discountCode: DiscountCode?): CreateOrderRequest {
        val address = _userAddress.value.toData()!![0]
        val orders = _productList.value.toData()!!
        val priceRule = _priceRule.value.toData()
        return CreateOrderRequest(
            Order(
                BillingAddress(
                    address.address,
                    address.city,
                    address.country,
                    repo.getCustomerUserName(),
                    "",
                    address.phone,
                    address.state,
                    address.zip.toString()
                ),
                listOf(
                    OrderDiscountCode(
                        (priceRule?.priceRule?.value?.toDouble()?.times(-1))?.toString(),
                        discountCode?.code,
                        priceRule?.priceRule?.valueType
                    )
                ),
                repo.getCustomerEmail(),
                "paid",
                orders.map { LineItem(it.quantity, it.variantId) },
                phone = address.phone,
                ShippingAddress(
                    address.address,
                    address.city,
                    address.country,
                    repo.getCustomerUserName(),
                    "",
                    address.phone,
                    address.state,
                    address.zip.toString()
                )
            )
        )
    }
}