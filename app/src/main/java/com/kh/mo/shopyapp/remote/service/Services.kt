package com.kh.mo.shopyapp.remote.service

import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.maincategory.MainCategoryResponse
import com.kh.mo.shopyapp.model.response.productsofbrand.ProductsOfSpecificBrandResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Services {

    @GET("smart_collections.json")
    suspend fun getAllBrands(): Response<BrandsResponse>

    @GET("custom_collections.json")
    suspend fun getMainCategories(): Response<MainCategoryResponse>

    @GET("products.json")
    suspend fun getProductsOfSpecificBrand(@Query("vendor") brandName:String): Response<ProductsOfSpecificBrandResponse>


}