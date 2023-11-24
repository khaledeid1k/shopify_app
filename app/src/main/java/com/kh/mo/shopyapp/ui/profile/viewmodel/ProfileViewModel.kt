package com.kh.mo.shopyapp.ui.profile.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.request.DraftOrderRequest
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import com.kh.mo.shopyapp.repo.mapper.convertFavoritesEntityToDraftOrderRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileViewModel(private val repo: Repo) : ViewModel() {
    private val TAG = "TAG ProfileViewModel"
    private val _userData: MutableStateFlow<UserData?> =
        MutableStateFlow(UserData(7588056039708, "Moaaz AbdEl-salam", "moaaz_1@gmail.com"))
    val userData: StateFlow<UserData?>
        get() = _userData

    private fun getCustomerId() = repo.getCustomerId()
    private fun getFavoriteDraftId() = repo.getFavoriteDraftId()

    private fun getAllFavorites(success: (DraftOrderRequest) -> Unit) {
        viewModelScope.launch {
            repo.getAllFavorites().collect {
                if (it is ApiState.Success) {
                    success(it.data.convertFavoritesEntityToDraftOrderRequest(getCustomerId()))

                }

            }
        }
    }

    fun backUpDraftFavorite() {
        getAllFavorites {
            viewModelScope.launch {
                Log.d(TAG, "backUpDraftFavorite:${it}.${getFavoriteDraftId()} ")
                repo.backUpDraftFavorite(it, getFavoriteDraftId()).collect {
                    when (it) {
                        is ApiState.Failure -> {}
                        ApiState.Loading -> {}
                        is ApiState.Success -> {}
                    }
                }
            }
        }

    }


}