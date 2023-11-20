package com.kh.mo.shopyapp.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.response.barnds.SmartCollection
import com.kh.mo.shopyapp.model.ui.maincategory.CustomCollection
import com.kh.mo.shopyapp.remote.ApiSate
import com.kh.mo.shopyapp.repo.Repo
import com.kh.mo.shopyapp.repo.maper.convertToCustomCollection
import com.kh.mo.shopyapp.repo.maper.convertToSmartCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.Result.Companion.success

class HomeViewModel(private var _irepo: Repo) : ViewModel() {

    private val _barnds = MutableStateFlow<ApiSate<List<SmartCollection>>>(ApiSate.Loading)
    val barnds: StateFlow<ApiSate<List<SmartCollection>>> = _barnds

    private val _mainCategories = MutableStateFlow<ApiSate<List<CustomCollection>>>(ApiSate.Loading)
    val mainCategories: StateFlow<ApiSate<List<CustomCollection>>> = _mainCategories


    fun getAllBrands() {
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.getAllBrands()
                .collect {
                    when (it) {
                        is ApiSate.Failure -> {
                            Log.i("ss0", "Fail")
                        }
                        is ApiSate.Loading -> {
                            _barnds.value = ApiSate.Loading
                            Log.i("ss0", "Loading")
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
            _irepo.getMainCategories()
                .collect {
                    when (it) {
                        is ApiSate.Failure -> {
                            Log.i("ss0", "Fail")
                        }
                        is ApiSate.Loading -> {
                            _mainCategories.value = ApiSate.Loading
                            Log.i("ss0", "Loading")
                        }
                        is ApiSate.Success -> {
                            success(it.data)
                            _mainCategories.value =
                                ApiSate.Success(it.data.convertToCustomCollection())
                        }
                    }


                }
        }
    }
}


