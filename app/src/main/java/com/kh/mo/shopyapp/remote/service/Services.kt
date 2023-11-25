package com.kh.mo.shopyapp.remote.service

import com.kh.mo.shopyapp.model.request.AddressRequest
import com.kh.mo.shopyapp.model.request.CustomerRequest
import com.kh.mo.shopyapp.model.response.address.AddressResponse
import com.kh.mo.shopyapp.model.request.DraftOrderRequest
import com.kh.mo.shopyapp.model.response.orderdetails.OrderDetailsResponse
import com.kh.mo.shopyapp.model.response.address.AddressesResponse
import com.kh.mo.shopyapp.model.response.ads.DiscountCodeResponse
import com.kh.mo.shopyapp.model.response.allproducts.AllProductsResponse
import com.kh.mo.shopyapp.model.response.allproducts.ProductResponse
import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.create_customer.CustomerResponse
import com.kh.mo.shopyapp.model.response.draft_order.DraftOrderResponse
import com.kh.mo.shopyapp.model.response.login.Login
import com.kh.mo.shopyapp.model.response.maincategory.MainCategoryResponse
import com.kh.mo.shopyapp.model.response.order.OrdersResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface Services {

    @GET("smart_collections.json")
    suspend fun getAllBrands(): Response<BrandsResponse>

    @GET("custom_collections.json")
    suspend fun getMainCategories(): Response<MainCategoryResponse>

    @GET("products.json")
    suspend fun getProductsOfSpecificBrand(@Query("vendor") brandName: String): Response<AllProductsResponse>

    @GET("price_rules/{priceRuleId}/discount_codes/{discountCodeId}.json")
    suspend fun getDiscountCode(
        @Path("priceRuleId") priceRuleId: String,
        @Path("discountCodeId") discountCodeId: String
    ): Response<DiscountCodeResponse>

    @GET("products.json")
    suspend fun getAllProducts(): Response<AllProductsResponse>

    @GET("products.json")
    suspend fun getProductsByCollection(@Query("collection_id") collectionId: Long): Response<AllProductsResponse>

    @POST("customers.json")
    suspend fun createCustomer(@Body customer: CustomerRequest): Response<CustomerResponse>

    @GET("customers.json")
    suspend fun singIn(@Query("email") email: String): Response<Login>


    @POST("draft_orders.json")
    suspend fun createFavoriteDraft(@Body draftOrderRequest: DraftOrderRequest): Response<DraftOrderResponse>


    @GET("products.json")
    suspend fun filterProductsBySubCollection(@Query("collection_id") collectionId: Long,@Query("product_type") productType: String): Response<AllProductsResponse>

    @GET("customers/{customerId}/addresses.json")
    suspend fun getAddressesOfCustomer(
        @Path("customerId") customerId: Long
    ): Response<AddressesResponse>

    @PUT("draft_orders/{draftFavoriteId}.json")
    suspend fun backUpDraftFavorite(@Body draftOrderRequest: DraftOrderRequest,
        @Path("draftFavoriteId") draftFavoriteId: Long
    ): Response<DraftOrderResponse>

    @GET("draft_orders/{draftFavoriteId}.json")
    suspend fun getProductsIdForDraftFavorite(
        @Path("draftFavoriteId") draftFavoriteId: Long
    ): Response<DraftOrderResponse>

    @GET("products.json")
    suspend fun getListOfSpecificProductsIds(@Query("ids") productsIds: List<Long>):Response<AllProductsResponse>


    @PUT("customers/{customerId}/addresses/{addressId}")
    suspend fun updateAddressOfCustomer(
        @Path("customerId") customerId: Long,
        @Path("addressId") addressId: Long,
        @Body updatedAddress: AddressRequest
    ): Response<AddressResponse>

    @DELETE("customers/{customerId}/addresses/{addressId}")
    suspend fun deleteAddressOfCustomer(
        @Path("customerId") customerId: Long,
        @Path("addressId") addressId: Long
    )

    @POST("customers/{customerId}/addresses.json")
    suspend fun addAddressToCustomer(
        @Path("customerId") customerId: Long,
        @Body address: AddressRequest
    ): Response<AddressResponse>

    @GET("orders.json")
    suspend fun getOrdersByCustomerID(@Query("customer_id") customerId: Long): Response<OrdersResponse>
    @GET("orders/{order_id}.json")
    suspend fun getOrderByID(@Path("order_id") orderId: Long): Response<OrderDetailsResponse>

    @GET("products/{product_id}/images.json")
    suspend fun getImageOrders(@Path("product_id") product_id: Long): Response<ProductResponse>
}