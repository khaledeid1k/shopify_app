package com.kh.mo.shopyapp.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.response.barnds.SmartCollection
import com.kh.mo.shopyapp.model.ui.AdModel
import com.kh.mo.shopyapp.model.ui.maincategory.CustomCollection
import com.kh.mo.shopyapp.model.ui.productsofbrand.Product
import com.kh.mo.shopyapp.remote.ApiSate
import com.kh.mo.shopyapp.repo.Repo
import com.kh.mo.shopyapp.repo.maper.convertToCustomCollection
import com.kh.mo.shopyapp.repo.maper.convertToProduct
import com.kh.mo.shopyapp.repo.maper.convertToSmartCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.Result.Companion.success

class HomeViewModel(private var _irepo: Repo) : ViewModel() {
    private val TAG = "TAG HomeViewModel"

    private val _barnds = MutableStateFlow<ApiSate<List<SmartCollection>>>(ApiSate.Loading)
    val barnds: StateFlow<ApiSate<List<SmartCollection>>> = _barnds

    private val _mainCategories = MutableStateFlow<ApiSate<List<CustomCollection>>>(ApiSate.Loading)
    val mainCategories: StateFlow<ApiSate<List<CustomCollection>>> = _mainCategories

    private val _productsBrand = MutableStateFlow<ApiSate<List<Product>>>(ApiSate.Loading)
    val productsBrand: StateFlow<ApiSate<List<Product>>> = _productsBrand


    fun getAllBrands() {
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.getAllBrands().collect {
                when (it) {
                    is ApiSate.Failure -> {
                        Log.i("ss0", "brands:Fail")
                    }
                    is ApiSate.Loading -> {
                        _barnds.value = ApiSate.Loading
                        Log.i("ss0", "brands:Loading")
                    }
                    is ApiSate.Success -> {
                        success(it.data)
                        _barnds.value = ApiSate.Success(it.data.convertToSmartCollection())
                    }
                }


            }
        }
    }

    fun getMainCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.getMainCategories().collect {
                when (it) {
                    is ApiSate.Failure -> {
                        Log.i("ss0", "main: Fail")
                    }
                    is ApiSate.Loading -> {
                        _mainCategories.value = ApiSate.Loading
                        Log.i("ss0", "main:Loading")
                    }
                    is ApiSate.Success -> {
                        success(it.data)
                        _mainCategories.value = ApiSate.Success(it.data.convertToCustomCollection())
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
                    is ApiSate.Failure -> {
                        Log.i("ss0", "productBrand: Fail")
                    }
                    is ApiSate.Loading -> {
                        _productsBrand.value = ApiSate.Loading
                        Log.i("ss0", "productBrand:Loading")
                    }
                    is ApiSate.Success -> {
                        success(it.data)
                        _productsBrand.value = ApiSate.Success(it.data.convertToProduct())
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
                    is ApiSate.Failure -> Log.i(TAG, "getCoupon: ${it.msg}")
                    ApiSate.Loading -> Log.i(TAG, "getCoupon: $it")
                    is ApiSate.Success -> Log.i(TAG, "getCoupon: ${it.data.discountCode?.code}")
                }
            }
        }
    }
}


