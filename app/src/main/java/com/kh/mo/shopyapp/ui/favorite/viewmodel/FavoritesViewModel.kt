package com.kh.mo.shopyapp.ui.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.entity.FavoriteEntity
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.model.ui.Favorite
import com.kh.mo.shopyapp.model.ui.allproducts.Product
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import com.kh.mo.shopyapp.repo.mapper.convertFavoriteEntityToProduct
import com.kh.mo.shopyapp.repo.mapper.convertFavoritesEntityToDraftOrderRequest
import com.kh.mo.shopyapp.repo.mapper.convertFavoritesEntityToFavorites
import com.kh.mo.shopyapp.ui.favorite.view.FavoritesAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repo:Repo):ViewModel(),
    FavoritesAdapter.FavoritesAdapterListener {

    private val _favorites = MutableStateFlow<List<Favorite>>(emptyList())
    val favorites: StateFlow<List<Favorite>> = _favorites

    private val _singleFavorites = MutableSharedFlow<Product>()
    val singleFavorites: SharedFlow<Product> = _singleFavorites

    private val _deleteFavorites = MutableSharedFlow<Long>()
    val deleteFavorites: SharedFlow<Long> = _deleteFavorites

    init {
        getAllFavorites()

    }
    private fun getAllFavorites(){
        viewModelScope.launch {
            repo.getAllFavorites().collect{
                        _favorites.value=it.convertFavoritesEntityToFavorites()
                }


            }

    }
     fun getSingleFavorite(productId: Long){
          viewModelScope.launch {
             repo.getSingleFavorite(productId).collect {
                 it?.let {fav->
                     _singleFavorites.emit(fav.convertFavoriteEntityToProduct())
                 }
                 }

         }
     }

    override fun deleteFavoriteListener(productId: Long) {
        viewModelScope.launch {
        _deleteFavorites.emit(productId)
        }

    }

    fun deleteFavoriteById(productId: Long){
        viewModelScope.launch {
            repo.deleteFavorite(productId)
        }
    }

}