package com.kh.mo.shopyapp.ui.category.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.shopyapp.model.ui.allproducts.Product
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.repo.Repo
import com.kh.mo.shopyapp.repo.mapper.convertProductToFavoriteEntity
import com.kh.mo.shopyapp.ui.category.view.ProductsCategoryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(private var _irepo: Repo) : ViewModel(),
    ProductsCategoryAdapter.ProductsCategoryListener {
    private val TAG = "TAG CategoryViewModel"
    private val _product = MutableStateFlow<ApiState<List<Product>>>(ApiState.Loading)
    val product: StateFlow<ApiState<List<Product>>> = _product


    private val _productCollection = MutableStateFlow<ApiState<List<Product>>>(ApiState.Loading)
    val productCollection: StateFlow<ApiState<List<Product>>> = _productCollection

    private val _filterProductCollection =
        MutableStateFlow<ApiState<List<Product>>>(ApiState.Loading)
    val filterProductCollection: StateFlow<ApiState<List<Product>>> = _filterProductCollection


    init {
        getSubCategories()

    }

    private fun getSubCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.getAllProducts().collect {
                _product.value=it
            }
        }
    }

    fun getCollectionProducts(collectionId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.getProductsByCollection(collectionId).collect {
                _productCollection.value = it
            }
        }
    }


    fun filterProductsBySubCollection(collectionId: Long, productType: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.filterProductsBySubCollection(collectionId, productType).collect {
                _filterProductCollection.value = it
            }
        }
    }

    private fun saveFavorite(product: Product) {
        viewModelScope.launch {
            _irepo.saveFavorite(product.convertProductToFavoriteEntity(_irepo.getCustomerId()))
        }
    }

    private fun deleteFavorite(productId: Long) {
        viewModelScope.launch {
            _irepo.deleteFavorite(productId)
        }
    }

    override fun onClickFavouriteIcon(product: Product) {
        viewModelScope.launch {
            _irepo.checkProductInFavorite(product.id).collect {
                if (it is ApiState.Success) {
                    if (it.data != 0) {
                        deleteFavorite(product.id)

                    }else{
                        saveFavorite(product)
                    }
                }
            }
        }


    }


}