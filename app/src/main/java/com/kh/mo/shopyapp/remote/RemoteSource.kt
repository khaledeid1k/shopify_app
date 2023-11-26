package com.kh.mo.shopyapp.remote

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentSnapshot
import com.kh.mo.shopyapp.model.request.AddressRequest
import com.kh.mo.shopyapp.model.request.CustomerDataRequest
import com.kh.mo.shopyapp.model.request.DraftOrderRequest
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.model.response.address.AddressResponse
import com.kh.mo.shopyapp.model.response.address.AddressesResponse
import com.kh.mo.shopyapp.model.response.ads.DiscountCodeResponse
import com.kh.mo.shopyapp.model.response.allproducts.AllProductsResponse
import com.kh.mo.shopyapp.model.response.allproducts.ProductResponse
import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.create_customer.CustomerResponse
import com.kh.mo.shopyapp.model.response.currency.Rates
import com.kh.mo.shopyapp.model.response.draft_order.DraftOrderResponse
import com.kh.mo.shopyapp.model.response.login.Login
import com.kh.mo.shopyapp.model.response.maincategory.MainCategoryResponse
import com.kh.mo.shopyapp.model.response.order.OrdersResponse
import com.kh.mo.shopyapp.model.response.orderdetails.OrderDetailsResponse
import com.kh.mo.shopyapp.model.response.osm.NominatimResponse
import retrofit2.Response

interface RemoteSource {
    suspend fun getListOfSpecificProductsIds( productsIds: String):Response<AllProductsResponse>
    suspend fun getProductsIdForDraftFavorite(draftFavoriteId: Long): Response<DraftOrderResponse>
    suspend fun singUpWithFireBase(userData: UserData): Task<AuthResult>
    suspend fun singInWithFireBase(userData: UserData): Task<AuthResult>
    suspend fun logout()
    fun checkIsUserLogin():Boolean
    suspend fun createFavoriteDraft(draftOrderRequest: DraftOrderRequest): Response<DraftOrderResponse>
    suspend fun backUpDraftFavorite(draftOrderRequest: DraftOrderRequest,draftFavoriteId: Long): Response<DraftOrderResponse>
    suspend fun getAllBrands(): Response<BrandsResponse>
    suspend fun getMainCategories(): Response<MainCategoryResponse>
    suspend fun getDiscountCode(
        priceRuleId: String,
        discountCodeId: String
    ): Response<DiscountCodeResponse>

    suspend fun getProductsOfSpecificBrand(brandName: String): Response<AllProductsResponse>
    suspend fun saveFavoriteDraftIdInFireBase(customerId:Long,favoriteDraft:Long): Task<Void>
    suspend fun getDraftFavoriteId(customerId: String): Task<DocumentSnapshot>
    suspend fun createCustomer(customerDataRequest: CustomerDataRequest): Response<CustomerResponse>
    suspend fun singInCustomer(email: String): Response<Login>
    suspend fun getCurrencyRate(): Rates
    suspend fun isCurrencyDbUpdated(): Boolean
    suspend fun getAllProducts(): Response<AllProductsResponse>
    suspend fun getProductsByCollection(collectionId: Long): Response<AllProductsResponse>
    suspend fun filterProductsBySubCollection(collectionId: Long,productType:String): Response<AllProductsResponse>
    suspend fun getAddressesOfCustomer(customerId: Long): Response<AddressesResponse>
    suspend fun updateAddressOfCustomer(
        customerId: Long,
        addressId: Long,
        updatedAddress: AddressRequest
    ): Response<AddressResponse>
    suspend fun deleteAddressOfCustomer(
        customerId: Long,
        addressId: Long
    )
    suspend fun getAddressDetails(
        latitude: Double,
        longitude: Double,
        language: String = "en"
    ): Response<NominatimResponse>

    suspend fun addAddressToCustomer(
        customerId: Long,
        address: AddressRequest,

        ): Response<AddressResponse>

    suspend fun getOrdersByCustomerID(customerId: Long): Response<OrdersResponse>
    suspend fun getOrderByID(orderId: Long): Response<OrderDetailsResponse>
    suspend fun getImageOrders(productId: Long): Response<ProductResponse>
}