package com.kh.mo.shopyapp.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.response.ads.DiscountCodeResponse
import com.kh.mo.shopyapp.model.ui.AdModel
import com.kh.mo.shopyapp.model.ui.allproducts.Product
import com.kh.mo.shopyapp.model.ui.maincategory.CustomCollection
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import com.kh.mo.shopyapp.repo.mapper.convertToCustomCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.Result.Companion.success

class HomeViewModel(private var _irepo: Repo) : ViewModel() {
    private val TAG = "TAG HomeViewModel"

    private val _brands = MutableStateFlow<ApiState<List<Product>>>(ApiState.Loading)
    val brands: StateFlow<ApiState<List<Product>>> = _brands

    private val _mainCategories =
        MutableStateFlow<ApiState<List<CustomCollection>>>(ApiState.Loading)
    val mainCategories: StateFlow<ApiState<List<CustomCollection>>> = _mainCategories

    private val _productBrand = MutableStateFlow<ApiState<List<Product>>>(ApiState.Loading)
    val productBrand: StateFlow<ApiState<List<Product>>> = _productBrand

    private val _couponState = MutableStateFlow<ApiState<DiscountCodeResponse>>(ApiState.Loading)
    val couponState: StateFlow<ApiState<DiscountCodeResponse>> = _couponState

    init {
        getAllBrands()
        getMainCategories()
    }

    fun getAllBrands() {
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.getAllBrands().collect {
                _brands.value = it
            }
        }
    }

    fun getMainCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.getMainCategories().collect {
                when (it) {
                    is ApiState.Failure -> {
                        Log.i("ss0", "main: Fail")
                    }
                    is ApiState.Loading -> {
                        _mainCategories.value = ApiState.Loading
                        Log.i("ss0", "main:Loading")
                    }
                    is ApiState.Success -> {
                        success(it.data)
                        _mainCategories.value =
                            ApiState.Success(it.data.convertToCustomCollection())
                        Log.i("ss0", "main:Success")


                    }
                }


            }
        }
    }

    fun getProductsOfSpecificBrand(brandName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.getProductsOfSpecificBrand(brandName).collect {
                _productBrand.value = it
                Log.i("ss0", "productBrand:Success")

            }
        }
    }

    fun getCoupon(adItem: AdModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.getDiscountCode(adItem.priceRuleId, adItem.discountCodeId).collectLatest {
                _couponState.value = it
            }
        }
    }
}


