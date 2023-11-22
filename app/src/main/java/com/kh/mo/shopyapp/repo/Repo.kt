package com.kh.mo.shopyapp.repo

import com.kh.mo.shopyapp.model.response.ads.DiscountCodeResponse
import com.kh.mo.shopyapp.model.response.allproducts.AllProductsResponse
import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.maincategory.MainCategoryResponse
import com.kh.mo.shopyapp.remote.ApiSate
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Repo {

    suspend fun getAllBrands(): Flow<ApiSate<BrandsResponse>>
    suspend fun getMainCategories(): Flow<ApiSate<MainCategoryResponse>>
    suspend fun getDiscountCode(
        priceRuleId: String,
        discountCodeId: String
    ): Flow<ApiSate<DiscountCodeResponse>>
    suspend fun getProductsOfSpecificBrand(brandName:String): Flow<ApiSate<AllProductsResponse>>
    suspend fun getAllProducts(): Flow<ApiSate<AllProductsResponse>>
    suspend fun getProductsByCollection(collectionId:Long): Flow<ApiSate<AllProductsResponse>>



}