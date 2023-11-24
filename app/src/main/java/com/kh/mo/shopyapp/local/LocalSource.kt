package com.kh.mo.shopyapp.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kh.mo.shopyapp.model.entity.FavoriteEntity
import com.kh.mo.shopyapp.model.entity.LineItemEntity
import com.kh.mo.shopyapp.model.entity.Validation
import kotlinx.coroutines.flow.Flow

interface LocalSource {

    fun validatePassword(password: String): Validation
    fun validateConfirmPassword(password: String, rePassword: String): Validation
    fun validateEmail(email: String): Validation
    fun validateUserName(userName: String): Validation

    suspend fun getAllLinetItems(): List<LineItemEntity>
    suspend fun deleteLinetItems(productId:Long)
    suspend fun saveLinetItems(lineItemEntity: LineItemEntity)


    suspend fun getAllFavorites(): List<FavoriteEntity>?
    suspend fun deleteFavorite(productId:Long)
    suspend fun saveFavorite(favoriteEntity: FavoriteEntity)
    suspend fun checkProductInFavorite(productId: Long): Int





    fun saveFavoriteDraftId(draftId:Long )
    fun saveCustomerId(customerId:Long )
    fun  getFavoriteDraftId( ):Long
    fun getCustomerId( ):Long


}