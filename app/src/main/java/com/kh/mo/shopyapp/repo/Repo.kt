package com.kh.mo.shopyapp.repo

import com.kh.mo.shopyapp.model.ui.Address
import com.kh.mo.shopyapp.model.entity.CustomerEntity
import com.kh.mo.shopyapp.model.entity.LineItemEntity
import com.kh.mo.shopyapp.model.entity.Validation
import com.kh.mo.shopyapp.model.request.DraftOrderRequest
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.model.response.ads.DiscountCodeResponse
import com.kh.mo.shopyapp.model.response.allproducts.AllProductsResponse
import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.maincategory.MainCategoryResponse
import com.kh.mo.shopyapp.model.ui.DraftOrder
import com.kh.mo.shopyapp.model.ui.Review
import com.kh.mo.shopyapp.remote.ApiState
import kotlinx.coroutines.flow.Flow

interface Repo {
    suspend fun singUpWithFireBase(userData: UserData): Flow<ApiState<String>>
    suspend fun singInWithFireBase(userData: UserData): Flow<ApiState<String>>
    suspend fun logout()
    fun checkIsUserLogin():Boolean
     suspend fun createFavoriteDraft(draftOrderRequest: DraftOrderRequest):Flow<ApiState<DraftOrder>>
    suspend fun saveFavoriteDraftIdInFireBase(customerId:Long,favoriteDraft:Long): Flow<ApiState<String>>
    suspend fun createCustomer(userData: UserData): Flow<ApiState<CustomerEntity>>
    suspend fun singInCustomer(email: String): Flow<ApiState<UserData>>
    suspend fun checkCustomerExists(customerId: String): Flow<ApiState<UserData>>
    fun validatePassword(password: String): Validation
    fun validateConfirmPassword(password: String, rePassword: String): Validation
    fun validateEmail(email: String): Validation
    fun validateUserName(userName: String): Validation
    fun reviews():List<Review>
    suspend fun getAllBrands(): Flow<ApiState<BrandsResponse>>
    suspend fun getMainCategories(): Flow<ApiState<MainCategoryResponse>>
    suspend fun getDiscountCode(
        priceRuleId: String,
        discountCodeId: String
    ): Flow<ApiState<DiscountCodeResponse>>
    suspend fun getProductsOfSpecificBrand(brandName:String): Flow<ApiState<AllProductsResponse>>
    suspend fun getAllProducts(): Flow<ApiState<AllProductsResponse>>
    suspend fun getProductsByCollection(collectionId:Long): Flow<ApiState<AllProductsResponse>>
    suspend fun filterProductsBySubCollection(collectionId:Long,productType:String): Flow<ApiState<AllProductsResponse>>

    suspend fun getAddressesOfCustomer(customerId: Long): Flow<ApiState<List<Address>>>

    suspend fun getAllLinetItems(): List<LineItemEntity>
    suspend fun deleteLinetItems(productId:Long)
    suspend fun saveLinetItems(lineItemEntity: LineItemEntity)
    fun saveFavoriteDraftId(draftId:Long )
    fun saveCustomerId(customerId:Long )
    fun  getFavoriteDraftId( ):Long
    fun getCustomerId( ):Long

}