package com.kh.mo.shopyapp.datasource

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentSnapshot
import com.kh.mo.shopyapp.model.request.AddressRequest
import com.kh.mo.shopyapp.model.request.CustomerDataRequest
import com.kh.mo.shopyapp.model.request.DraftOrderRequest
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.model.request.order.CreateOrderRequest
import com.kh.mo.shopyapp.model.response.address.AddressResponse
import com.kh.mo.shopyapp.model.response.address.AddressesResponse
import com.kh.mo.shopyapp.model.response.ads.DiscountCodeResponse
import com.kh.mo.shopyapp.model.response.ads.PriceRuleResponse
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
import com.kh.mo.shopyapp.model.response.singleproduct.SingleProductResponse
import com.kh.mo.shopyapp.remote.RemoteSource
import retrofit2.Response

class FakeRemoteSourceImp: RemoteSource {

    override suspend fun getListOfSpecificProductsIds(productsIds: String): Response<AllProductsResponse> {
        return Response.success(FakeData.fakeAllProductsResponse)
    }

    override suspend fun getProductsIdForDraftFavorite(draftFavoriteId: Long): Response<DraftOrderResponse> {
        return Response.success(FakeData.fakeDraftOrderResponse)
    }

    override suspend fun singUpWithFireBase(userData: UserData): Task<AuthResult> {
        TODO("Not yet implemented")
    }

    override suspend fun singInWithFireBase(userData: UserData): Task<AuthResult> {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }

    override fun checkIsUserLogin(): Boolean {
        return true
    }

    override suspend fun createFavoriteDraft(draftOrderRequest: DraftOrderRequest): Response<DraftOrderResponse> {
        return Response.success(FakeData.fakeDraftOrderResponse)
    }

    override suspend fun backUpDraftFavorite(
        draftOrderRequest: DraftOrderRequest,
        draftFavoriteId: Long
    ): Response<DraftOrderResponse> {
        return Response.success(FakeData.fakeDraftOrderResponse)
    }

    override suspend fun getAllBrands(): Response<BrandsResponse> {
        return Response.success(FakeData.fakeBrandsResponse)
    }

    override suspend fun getMainCategories(): Response<MainCategoryResponse> {
        return Response.success(FakeData.fakeMainCategoryResponse)
    }

    override suspend fun getDiscountCode(
        priceRuleId: String,
        discountCodeId: String
    ): Response<DiscountCodeResponse> {
        return Response.success(FakeData.fakeDiscountCodeResponse)
    }

    override suspend fun sendEmailVerification(): Task<Void>? {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsOfSpecificBrand(brandName: String): Response<AllProductsResponse> {
        return Response.success(FakeData.fakeAllProductsResponse)
    }

    override suspend fun getDraftIds(customerId: String): Task<DocumentSnapshot> {
        TODO("Not yet implemented")
    }

    override suspend fun checkEmailVerification(): Boolean {
        return true
    }

    override suspend fun createCustomer(customerDataRequest: CustomerDataRequest): Response<CustomerResponse> {
        return Response.success(FakeData.fakeCustomerResponse)
    }

    override suspend fun singInCustomer(email: String): Response<Login> {
        return Response.success(FakeData.fakeLogin)
    }

    override suspend fun getCurrencyRate(): Rates {
        return FakeData.fakeRates
    }

    override suspend fun isCurrencyDbUpdated(): Boolean {
        return true
    }

    override suspend fun getAllProducts(): Response<AllProductsResponse> {
        return Response.success(FakeData.fakeAllProductsResponse)
    }

    override suspend fun getSingleProduct(productId: Long): Response<SingleProductResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsByCollection(collectionId: Long): Response<AllProductsResponse> {
        return Response.success(FakeData.fakeAllProductsResponse)
    }

    override suspend fun filterProductsBySubCollection(
        collectionId: Long,
        productType: String
    ): Response<AllProductsResponse> {
        return Response.success(FakeData.fakeAllProductsResponse)
    }

    override suspend fun getAddressesOfCustomer(customerId: Long): Response<AddressesResponse> {
        return Response.success(FakeData.fakeAddressesResponse)
    }

    override suspend fun updateAddressOfCustomer(
        customerId: Long,
        addressId: Long,
        updatedAddress: AddressRequest
    ): Response<AddressResponse> {
        return Response.success(FakeData.fakeAddressResponse)
    }

    override suspend fun deleteAddressOfCustomer(customerId: Long, addressId: Long) {

    }

    override suspend fun getAddressDetails(
        latitude: Double,
        longitude: Double,
        language: String
    ): Response<NominatimResponse> {
        return Response.success(FakeData.fakeNominatimResponse)
    }

    override suspend fun addAddressToCustomer(
        customerId: Long,
        address: AddressRequest
    ): Response<AddressResponse> {
        return Response.success(FakeData.fakeAddressResponse)
    }

    override suspend fun getOrdersByCustomerID(customerId: Long): Response<OrdersResponse> {
        return Response.success(FakeData.fakeOrdersResponse)
    }

    override suspend fun getOrderByID(orderId: Long): Response<OrderDetailsResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getImageOrders(productId: Long): Response<ProductResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun createCartDraft(draftOrderRequest: DraftOrderRequest): Response<DraftOrderResponse> {
        return Response.success(FakeData.fakeDraftOrderResponse)
    }

    override suspend fun saveCartDraftIdInFireBase(
        customerId: Long,
        cartDraftId: Long,
        favoriteDraft: Long
    ): Task<Void> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllProductIdsInCart(cartId: String): Response<DraftOrderResponse> {
        return Response.success(FakeData.fakeDraftOrderResponse)
    }

    override suspend fun getPriceRule(priceRuleId: String): Response<PriceRuleResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun createOrder(orderRequest: CreateOrderRequest): Response<OrdersResponse> {
        return Response.success(FakeData.fakeOrdersResponse)
    }
}