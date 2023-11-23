package com.kh.mo.shopyapp.remote

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.kh.mo.shopyapp.model.request.CustomerDataRequest
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.model.response.ads.DiscountCodeResponse
import com.kh.mo.shopyapp.model.response.allproducts.AllProductsResponse
import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.create_customer.CustomerResponse
import com.kh.mo.shopyapp.model.response.currency.Rates
import com.kh.mo.shopyapp.model.response.login.Login
import com.kh.mo.shopyapp.model.response.maincategory.MainCategoryResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RemoteSource {

    suspend fun singUpWithFireBase(userData: UserData): Task<AuthResult>
    suspend fun getAllBrands(): Response<BrandsResponse>
    suspend fun getMainCategories(): Response<MainCategoryResponse>
    suspend fun getDiscountCode(
        priceRuleId: String,
        discountCodeId: String
    ): Response<DiscountCodeResponse>

    suspend fun getProductsOfSpecificBrand(brandName: String): Response<AllProductsResponse>
    suspend fun storeCustomerInFireBase(userId: Long, userData: UserData): Flow<ApiState<String>>
    suspend fun checkCustomerExists(customerId: String): Flow<ApiState<UserData>>
    suspend fun createCustomer(customerDataRequest: CustomerDataRequest): Response<CustomerResponse>
    suspend fun singIn(email: String): Response<Login>
    suspend fun getCurrencyRate(): Rates
    suspend fun isCurrencyDbUpdated(): Boolean
    suspend fun getAllProducts(): Response<AllProductsResponse>
    suspend fun getProductsByCollection(collectionId: Long): Response<AllProductsResponse>
}