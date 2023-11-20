package com.kh.mo.shopyapp.remote

import com.kh.mo.shopyapp.model.response.ads.DiscountCodeResponse
import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.maincategory.MainCategoryResponse
import retrofit2.Response

interface RemoteSource {

    suspend fun getAllBrands(): Response<BrandsResponse>
    suspend fun getMainCategories(): Response<MainCategoryResponse>
    suspend fun getDiscountCode(priceRuleId: String, discountCodeId: String): DiscountCodeResponse
}