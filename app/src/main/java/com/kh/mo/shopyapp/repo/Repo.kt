package com.kh.mo.shopyapp.repo

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.kh.mo.shopyapp.model.entity.CustomerEntity
import com.kh.mo.shopyapp.model.entity.Validation
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.model.response.ads.DiscountCodeResponse
import com.kh.mo.shopyapp.model.response.allproducts.AllProductsResponse
import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.maincategory.MainCategoryResponse
import com.kh.mo.shopyapp.model.ui.Review
import com.kh.mo.shopyapp.remote.ApiState
import kotlinx.coroutines.flow.Flow

interface Repo {
    suspend fun storeCustomerInFireBase(userId: Long, userData: UserData): Flow<ApiState<String>>
    suspend fun singUpWithFireBase(userData: UserData): Flow<ApiState<String>>    
    suspend fun singInWithFireBase(userData: UserData): Flow<ApiState<String>>

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

}