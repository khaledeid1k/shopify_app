package com.kh.mo.shopyapp.ui.profile.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.entity.FavoriteEntity
import com.kh.mo.shopyapp.model.request.DraftOrderRequest
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import com.kh.mo.shopyapp.repo.mapper.convertFavoritesEntityToDraftOrderRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val repo: Repo) : ViewModel() {
    private val TAG = "TAG ProfileViewModel"
    private val _userData: MutableStateFlow<UserData?> =
        MutableStateFlow(UserData(7588056039708, "Moaaz AbdEl-salam", "moaaz_1@gmail.com"))
    val userData: StateFlow<UserData?>
        get() = _userData

    private val _backUpDraftFavorite = MutableStateFlow<ApiState<String>>(ApiState.Loading)
    val backUpDraftFavorite: StateFlow<ApiState<String>> = _backUpDraftFavorite

    private val _retrieveDraftFavorite = MutableStateFlow<ApiState<String>>(ApiState.Loading)
    val retrieveDraftFavorite: StateFlow<ApiState<String>> = _retrieveDraftFavorite

    private val _currencyPreference = MutableStateFlow("")
    val currencyPreference: StateFlow<String> = _currencyPreference

    private fun getCustomerId() = repo.getCustomerId()
    private fun getFavoriteDraftId() = repo.getFavoriteDraftId()
    private fun getAllFavorites(success: (DraftOrderRequest) -> Unit) {
        viewModelScope.launch {
            repo.getAllFavorites().collect {
                success(it.convertFavoritesEntityToDraftOrderRequest(getCustomerId()))


            }
        }
    }

    fun backUpDraftFavorite() {
        getAllFavorites {
            viewModelScope.launch {
                repo.backUpDraftFavorite(it, getFavoriteDraftId()).collect {
                    when (it) {
                        is ApiState.Failure -> {
                            _backUpDraftFavorite.value = ApiState.Failure("Failure")

                        }

                        ApiState.Loading -> {
                            _backUpDraftFavorite.value = ApiState.Loading
                        }

                        is ApiState.Success -> {
                            _retrieveDraftFavorite.value = ApiState.Success(it.data)

                        }
                    }
                }
            }
        }

    }


    private fun saveProducts(
        favoritesEntity: List<FavoriteEntity>,
        isSuccessfully: (Long) -> Unit
    ) {
        viewModelScope.launch {
            favoritesEntity.map {
                Log.d(TAG, "saveProductsaaaaaaaaaa:$it")
                val result = repo.saveFavorite(it)

                isSuccessfully(result)

            }

        }
    }

    private fun getListOfSpecificProductsByIds(productsIds: List<Long>) {
        viewModelScope.launch {
            repo.getListOfSpecificProductsIds(productsIds).collect {
                when (it) {
                    is ApiState.Failure -> {
                        _retrieveDraftFavorite.value = ApiState.Failure("Failure")
                    }

                    ApiState.Loading -> {
                        _retrieveDraftFavorite.value = ApiState.Loading
                    }

                    is ApiState.Success -> {
                        saveProducts(it.data) { result ->
                            if (result > 0)
                                _retrieveDraftFavorite.value = ApiState.Success("Done")
                            Log.d(TAG, "getListOfSpecificProductsIds: saveProducts Done${result}")
                        }

                    }
                }
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
}