package com.kh.mo.shopyapp.remote.service

import com.kh.mo.shopyapp.model.response.ads.DiscountCodeResponse
import com.kh.mo.shopyapp.model.response.allproducts.AllProductsResponse
import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.maincategory.MainCategoryResponse
import com.kh.mo.shopyapp.model.response.productsofbrand.ProductsOfSpecificBrandResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface Services {

    @GET("smart_collections.json")
    suspend fun getAllBrands(): Response<BrandsResponse>

    @GET("custom_collections.json")
    suspend fun getMainCategories(): Response<MainCategoryResponse>

    @GET("products.json")
    suspend fun getProductsOfSpecificBrand(@Query("vendor") brandName:String): Response<ProductsOfSpecificBrandResponse>

    @GET("price_rules/{priceRuleId}/discount_codes/{discountCodeId}.json")
    suspend fun getDiscountCode(
        @Path("priceRuleId") priceRuleId: String,
        @Path("discountCodeId") discountCodeId: String
    ): Response<DiscountCodeResponse>

    @GET("products.json")
    suspend fun getAllProducts(): Response<AllProductsResponse>

}