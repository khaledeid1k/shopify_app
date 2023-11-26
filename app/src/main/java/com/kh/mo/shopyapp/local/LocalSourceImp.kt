package com.kh.mo.shopyapp.local

import android.content.Context
import com.kh.mo.shopyapp.local.dp.ShopyDataBase
import com.kh.mo.shopyapp.local.dp.sharedPref.SharedPreferencesShopy
import com.kh.mo.shopyapp.local.dp.sharedPref.currencyUnit
import com.kh.mo.shopyapp.local.dp.sharedPref.customerId
import com.kh.mo.shopyapp.local.dp.sharedPref.favoriteDraftId
import com.kh.mo.shopyapp.local.validation.AuthInputValidatorImpl
import com.kh.mo.shopyapp.local.validation.ValidationSateImpl
import com.kh.mo.shopyapp.model.entity.FavoriteEntity
import com.kh.mo.shopyapp.model.entity.LineItemEntity
import com.kh.mo.shopyapp.model.entity.Validation
import kotlinx.coroutines.flow.Flow

class LocalSourceImp private constructor(context: Context):LocalSource {
    private val validationSateImpl= ValidationSateImpl(AuthInputValidatorImpl())
   private val shopyDao=ShopyDataBase.getShopyDataBaseDataBaseInstance(context).shopyDao()
    private val sharedPreferencesShopy =    SharedPreferencesShopy(context).customPreference()

    override fun validatePassword(password: String): Validation =
        validationSateImpl.validatePassword(password)


    override fun validateConfirmPassword(password: String, rePassword: String): Validation=
        validationSateImpl.validateConfirmPassword(password, rePassword)


    override fun validateEmail(email: String): Validation =
       validationSateImpl.validateEmail(email)



    override fun validateUserName(userName: String): Validation =
        validationSateImpl.validateUserName(userName)


    override suspend fun getAllFavorites(customerId: Long):Flow<List<FavoriteEntity>>{
       return shopyDao.getAllFavorites(customerId)
    }

    override suspend fun deleteFavorite(productId: Long) {
        shopyDao.deleteFavorite(productId)
    }

    override suspend fun saveFavorite(favoriteEntity: FavoriteEntity):Long {
      return shopyDao.saveFavorite(favoriteEntity)
    }

    override suspend fun checkProductInFavorite(productId: Long): Int {
       return shopyDao.checkProductInFavorite(productId)
    }

    override fun saveCustomerId(customerId:Long ) {
        sharedPreferencesShopy.customerId = customerId

    }
    override fun getCustomerId():Long {
       return sharedPreferencesShopy.customerId

    }

    override fun saveFavoriteDraftId(draftId:Long ) {
        sharedPreferencesShopy.favoriteDraftId = draftId
    }

    override fun  getFavoriteDraftId() :Long{
       return sharedPreferencesShopy.favoriteDraftId

    }

    override fun getCurrencyUnit(): String {
        return sharedPreferencesShopy.currencyUnit
    }

    override fun setCurrencyUnit(unit: String) {
        sharedPreferencesShopy.currencyUnit = unit
    }

    companion object {
        @Volatile
        private var instance: LocalSourceImp? = null
        fun getLocalSourceImpInstance(context: Context): LocalSourceImp {
            return instance ?: synchronized(this) {
                val instanceHolder = LocalSourceImp(context)
                instance = instanceHolder
                instanceHolder

            }
        }
    }

}