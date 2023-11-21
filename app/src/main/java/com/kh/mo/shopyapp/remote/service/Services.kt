package com.kh.mo.shopyapp.remote.service

import com.kh.mo.shopyapp.model.request.CustomerRequest
import com.kh.mo.shopyapp.model.response.ads.DiscountCodeResponse
import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.create_customer.CustomerResponse
import com.kh.mo.shopyapp.model.response.login.Login
import com.kh.mo.shopyapp.model.response.maincategory.MainCategoryResponse
import com.kh.mo.shopyapp.model.response.productsofbrand.ProductsOfSpecificBrandResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Services {

    @GET("smart_collections.json")
    suspend fun getAllBrands(): Response<BrandsResponse>

    @GET("custom_collections.json")
    suspend fun getMainCategories(): Response<MainCategoryResponse>

    @GET("products.json")
    suspend fun getProductsOfSpecificBrand(@Query("vendor") brandName: String): Response<ProductsOfSpecificBrandResponse>

    @GET("price_rules/{priceRuleId}/discount_codes/{discountCodeId}.json")
    suspend fun getDiscountCode(
        @Path("priceRuleId") priceRuleId: String,
        @Path("discountCodeId") discountCodeId: String
    ): Response<DiscountCodeResponse>

    @POST("customers.json")
    suspend fun createCustomer(@Body customer: CustomerRequest): Response<CustomerResponse>

    @GET("customers.json")
    suspend fun singIn(@Query("email") email: String): Response<Login>


}