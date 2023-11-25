package com.kh.mo.shopyapp.ui.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.model.ui.Favorite
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import com.kh.mo.shopyapp.repo.mapper.convertFavoritesEntityToDraftOrderRequest
import com.kh.mo.shopyapp.repo.mapper.convertFavoritesEntityToFavorites
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repo:Repo):ViewModel() {
    private val _favorites = MutableStateFlow<List<Favorite>>(emptyList())
    val favorites: StateFlow<List<Favorite>> = _favorites

    init {
        getAllFavorites()

    }
    private fun getAllFavorites(){
        viewModelScope.launch {
            repo.getAllFavorites().collect{
                when(it){
                    is ApiState.Failure -> {}
                    ApiState.Loading -> {}
                    is ApiState.Success -> {
                        _favorites.value=it.data.convertFavoritesEntityToFavorites()
                    }
                }



            }
        }
    }
}