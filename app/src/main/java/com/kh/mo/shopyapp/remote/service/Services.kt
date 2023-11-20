package com.kh.mo.shopyapp.remote.service

import com.kh.mo.shopyapp.model.response.ads.DiscountCodeResponse
import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.maincategory.MainCategoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Services {

    @GET("smart_collections.json")
    suspend fun getAllBrands(): Response<BrandsResponse>

    @GET("custom_collections.json")
    suspend fun getMainCategories(): Response<MainCategoryResponse>

    @GET("price_rules/{priceRuleId}/discount_codes/{discountCodeId}.json")
    suspend fun getDiscountCode(
        @Path("priceRuleId") priceRuleId: String,
        @Path("discountCodeId") discountCodeId: String
    ): DiscountCodeResponse

}