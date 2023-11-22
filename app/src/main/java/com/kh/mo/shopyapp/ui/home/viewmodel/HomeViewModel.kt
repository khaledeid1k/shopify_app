package com.kh.mo.shopyapp.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.response.barnds.SmartCollection
import com.kh.mo.shopyapp.model.ui.AdModel
import com.kh.mo.shopyapp.model.ui.allproducts.Products
import com.kh.mo.shopyapp.model.ui.maincategory.CustomCollection
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import com.kh.mo.shopyapp.repo.maper.convertToAllProducts
import com.kh.mo.shopyapp.repo.maper.convertToCustomCollection
import com.kh.mo.shopyapp.repo.maper.convertToSmartCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.Result.Companion.success

class HomeViewModel(private var _irepo: Repo) : ViewModel() {
    private val TAG = "TAG HomeViewModel"

    private val _brands = MutableStateFlow<ApiState<List<SmartCollection>>>(ApiState.Loading)
    val brands: StateFlow<ApiState<List<SmartCollection>>> = _brands

    private val _mainCategories = MutableStateFlow<ApiState<List<CustomCollection>>>(ApiState.Loading)
    val mainCategories: StateFlow<ApiState<List<CustomCollection>>> = _mainCategories

    private val _productsBrand = MutableStateFlow<ApiState<List<Products>>>(ApiState.Loading)
    val productsBrand: StateFlow<ApiState<List<Products>>> = _productsBrand

    init{
        getAllBrands()
        getMainCategories()

    }

    fun getAllBrands() {
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.getAllBrands().collect {
                when (it) {
                    is ApiState.Failure -> {
                        Log.i("ss0", "brands:Fail")
                    }
                    is ApiState.Loading -> {
                        _brands.value = ApiState.Loading
                        Log.i("ss0", "brands:Loading")
                    }
                    is ApiState.Success -> {
                        success(it.data)
                        _brands.value = ApiState.Success(it.data.convertToSmartCollection())
                    }
                }


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
                        _mainCategories.value = ApiState.Success(it.data.convertToCustomCollection())
                        Log.i("ss0", "main:Success")


                    }
                }


            }
        }
    }

    fun getProductsOfSpecificBrand(brandName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.getProductsOfSpecificBrand(brandName).collect {
                when (it) {
                    is ApiState.Failure -> {
                        Log.i("ss0", "productBrand: Fail")
                    }
                    is ApiState.Loading -> {
                        _productsBrand.value = ApiState.Loading
                        Log.i("ss0", "productBrand:Loading")
                    }
                    is ApiState.Success -> {
                        success(it.data)
                        _productsBrand.value = ApiState.Success(it.data.convertToAllProducts())
                        Log.i("ss0", "productBrand:Success")

                    }
                }
            }
        }
    }

    fun getCoupon(adItem: AdModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.getDiscountCode(adItem.priceRuleId, adItem.discountCodeId).collectLatest {
                when (it) {
                    is ApiState.Failure -> Log.i(TAG, "getCoupon: ${it.msg}")
                    ApiState.Loading -> Log.i(TAG, "getCoupon: $it")
                    is ApiState.Success -> Log.i(TAG, "getCoupon: ${it.data.discountCode?.code}")
                }
            }
        }
    }
}


