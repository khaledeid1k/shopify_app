package com.kh.mo.shopyapp.ui.profile.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.entity.FavoriteEntity
import com.kh.mo.shopyapp.model.request.DraftOrderRequest
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.model.ui.orderdetails.Order
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import com.kh.mo.shopyapp.repo.mapper.convertFavoritesEntityToDraftOrderRequest
import com.kh.mo.shopyapp.repo.mapper.convertToOrders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val repo: Repo) : ViewModel() {
    private val TAG = "TAG ProfileViewModel"

    private val _backUpDraftFavorite = MutableStateFlow<ApiState<String>>(ApiState.Loading)
    val backUpDraftFavorite: StateFlow<ApiState<String>> = _backUpDraftFavorite

    private val _retrieveDraftFavorite =
        MutableStateFlow<ApiState<List<FavoriteEntity>>>(ApiState.Loading)
    val retrieveDraftFavorite: StateFlow<ApiState<List<FavoriteEntity>>> = _retrieveDraftFavorite

    private val _currencyPreference = MutableStateFlow("")
    val currencyPreference: StateFlow<String> = _currencyPreference

    private val _languagePreference = MutableStateFlow("")
    val languagePreference: StateFlow<String> = _languagePreference

    private val _orders = MutableStateFlow<ApiState<List<Order>>>(ApiState.Loading)
    val orders: StateFlow<ApiState<List<Order>>> = _orders

    init {
        getOrders()

    }

    fun getCustomerId() = repo.getCustomerId()
    private fun getFavoriteDraftId() = repo.getFavoriteDraftId()
    fun checkIsUserLogin() = repo.checkIsUserLogin()
    fun getCustomerUserName() = repo.getCustomerUserName()
    fun getCustomerEmail() = repo.getCustomerEmail()

    private fun getAllFavorites(
        success: (DraftOrderRequest) -> Unit,
        failure: () -> Unit
    ) {
        viewModelScope.launch {
            repo.getAllFavorites().collect {
                if (it.isNotEmpty()) {
                    success(it.convertFavoritesEntityToDraftOrderRequest(getCustomerId()))
                } else {
                    failure()
                }

            }
        }
    }

    fun backUpDraftFavorite() {
        getAllFavorites(success =  {
            viewModelScope.launch {
                repo.backUpDraftFavorite(it, getFavoriteDraftId()).collect {
                    _backUpDraftFavorite.value = it
                }
            }
        }, failure = {
            _backUpDraftFavorite.value = ApiState.Failure("No Data To Upload")
        }
        )

    }


    fun saveProducts(favoritesEntity: List<FavoriteEntity>, isSuccessfully: (Long) -> Unit) {
        viewModelScope.launch {
            favoritesEntity.map {
                val result = repo.saveFavorite(it)

                isSuccessfully(result)

            }

        }
    }

    private fun getListOfSpecificProductsByIds(productsIds: List<Long>) {
        viewModelScope.launch {
            repo.getListOfSpecificProductsIds(productsIds.joinToString(",")).collect {
                _retrieveDraftFavorite.value = it
            }
        }

    }


    fun retrieveDraftFavorite() {
        viewModelScope.launch {
            repo.getProductsIdForDraftFavorite(getFavoriteDraftId()).collect {
                if (it is ApiState.Success) {
                    getListOfSpecificProductsByIds(it.data)
                    Log.d(TAG, "retrieveDraftFavorite: ${it.data}")

                }
            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            repo.logout()
        }
    }

    fun getCurrencyPreference() {
        viewModelScope.launch(Dispatchers.IO) {
            _currencyPreference.value = repo.getCurrencyUnit()
        }
    }

    fun updateCurrencyPreference(unit: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _currencyPreference.value = repo.setCurrencyUnit(unit)
        }
    }

    fun setLanguagePreference(language: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.setLanguage(language)
        }
    }

    fun getCurrentLanguage() {
        viewModelScope.launch(Dispatchers.IO) {
            _languagePreference.value = repo.getCurrentLanguage()
        }
    }

    fun getOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getOrdersByCustomerID(repo.getCustomerId()).collect {
                if (it is ApiState.Success) {
                    _orders.value = ApiState.Success(it.data.convertToOrders())
                }
            }
        }
    }
}
