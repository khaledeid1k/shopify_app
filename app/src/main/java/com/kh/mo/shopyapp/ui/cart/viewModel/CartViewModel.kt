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

    fun getDraftCartId() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getDraftCartId(repo.getCustomerId().toString()).collectLatest { state ->
                when (state) {
                    is ApiState.Failure -> {
                        if (state.msg == Constants.NO_CART_MESSAGE) {
                            Log.i(TAG, "getDraftCartId: not created or ordered")
                            _draftCartId.value = ApiState.Loading
                            createCartDraft()
                        } else {
                            _draftCartId.value = state
                            Log.i(TAG, "getDraftCartId: failure${state.msg}")
                        }
                    }

                    is ApiState.Loading -> {
                        _draftCartId.value = state
                    }

                    is ApiState.Success -> {
                        Log.i(TAG, "getDraftCartId: success: ${state.data}")
                        _draftCartId.value = state.data?.let {
                            ApiState.Success(state.data)
                        } ?: ApiState.Failure("null response")
                    }
                }
            }

        }
    }

    private fun createCartDraft() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.createFavoriteDraft(
                DraftOrderRequest(
                    DraftOrderDetailsRequest(
                        customer = CustomerDraftRequest(repo.getCustomerId())
                    )
                )
            ).collectLatest { state: ApiState<DraftOrder> ->
                when (state) {
                    is ApiState.Failure -> {
                        _draftCartId.value = state
                    }

                    is ApiState.Loading -> {
                        _draftCartId.value = state
                    }

                    is ApiState.Success -> {
                        _draftCartId.value = ApiState.Success(state.data.draftId.toString())
                        saveCartDraftIdInFireBase(state.data.customerID, state.data.draftId)
                        saveCartDraftIdInPreferences(state.data.draftId)
                    }
                }
            }
        }
    }

    private fun saveCartDraftIdInFireBase(customerId: Long, draftId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.saveCartDraftIdInFireBase(customerId, draftId).collect {
                when (it) {
                    is ApiState.Failure -> {}
                    is ApiState.Loading -> {}
                    is ApiState.Success -> {
                        Log.i(TAG, "saveCartDraftIdInFireBase: success: ${it.data}")
                    }
                }
            }
        }
    }

    private fun saveCartDraftIdInPreferences(draftId: Long) {
        viewModelScope.launch(Dispatchers.IO) { repo.saveCartDraftId(draftId) }
    }

    fun getAllProductsInCart(cartId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllProductsInCart(cartId).collectLatest {state ->
                _productList.value = state
            }
        }
    }
}