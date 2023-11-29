package com.kh.mo.shopyapp.datasource

import com.kh.mo.shopyapp.local.LocalSource
import com.kh.mo.shopyapp.model.entity.FavoriteEntity
import com.kh.mo.shopyapp.model.entity.Validation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocalSourceImp: LocalSource {
    override fun validatePassword(password: String): Validation {
        TODO("Not yet implemented")
    }

    override fun validateConfirmPassword(password: String, rePassword: String): Validation {
        TODO("Not yet implemented")
    }

    override fun validateEmail(email: String): Validation {
        TODO("Not yet implemented")
    }

    override fun validateUserName(userName: String): Validation {
        TODO("Not yet implemented")
    }

    override suspend fun getAllFavorites(customerId: Long): Flow<List<FavoriteEntity>> {
        return flow { FakeData.fakeFavoriteEntity }
    }

    override fun getSingleFavorite(productId: Long): Flow<FavoriteEntity> {
        return flow { FakeData.fakeFavoriteEntity }
    }

    override suspend fun deleteFavorite(productId: Long) {

    }

    override suspend fun saveFavorite(favoriteEntity: FavoriteEntity): Long {
        return 1L
    }

    override suspend fun checkProductInFavorite(productId: Long, customerId: Long): Int {
        return 1
    }

    override fun saveFavoriteDraftId(draftId: Long) {

    }

    override fun saveCustomerId(customerId: Long) {

    }

    override fun getFavoriteDraftId(): Long {
        return 123456
    }

    override fun getCustomerId(): Long {
        return 678910
    }

    override fun saveCartDraftId(draftId: Long) {

    }

    override fun getCartDraftId(): Long {
        return 123456
    }

    override fun getCurrencyUnit(): String {
        return "EGP"
    }

    override fun setCurrencyUnit(unit: String) {

    }

    override fun changeLanguage(language: String) {

    }

    override fun getCurrentLanguage(): String {
        return "en"
    }

    override fun saveCustomerEmail(customerEmail: String) {
    }

    override fun getCustomerEmail(): String {
        return "example@gemail"
    }

    override fun saveCustomerUserName(customerUserName: String) {

    }

    override fun getCustomerUserName(): String {
        return "example"
    }

    override fun clearSharedPreferences() {

    }
}