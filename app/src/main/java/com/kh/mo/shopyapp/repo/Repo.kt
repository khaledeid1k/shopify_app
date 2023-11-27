package com.kh.mo.shopyapp.repo

import com.kh.mo.shopyapp.model.entity.CustomerEntity
import com.kh.mo.shopyapp.model.entity.FavoriteEntity
import com.kh.mo.shopyapp.model.entity.Validation
import com.kh.mo.shopyapp.model.request.DraftOrderRequest
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.model.response.ads.DiscountCodeResponse
import com.kh.mo.shopyapp.model.response.allproducts.AllProductsResponse
import com.kh.mo.shopyapp.model.response.allproducts.ProductResponse
import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.draft_order.DraftOrderResponse
import com.kh.mo.shopyapp.model.response.maincategory.MainCategoryResponse
import com.kh.mo.shopyapp.model.response.order.OrdersResponse
import com.kh.mo.shopyapp.model.response.orderdetails.OrderDetailsResponse
import com.kh.mo.shopyapp.model.ui.Address
import com.kh.mo.shopyapp.model.ui.Cart
import com.kh.mo.shopyapp.model.ui.DraftOrder
import com.kh.mo.shopyapp.model.ui.Review
import com.kh.mo.shopyapp.model.ui.allproducts.Product
import com.kh.mo.shopyapp.remote.ApiState
import kotlinx.coroutines.flow.Flow

interface Repo {
    suspend fun getListOfSpecificProductsIds( productsIds: String):Flow<ApiState<List<FavoriteEntity>>>
    suspend fun getProductsIdForDraftFavorite(draftFavoriteId: Long): Flow<ApiState<List<Long>>>
    suspend fun singUpWithFireBase(userData: UserData): Flow<ApiState<String>>
    suspend fun singInWithFireBase(userData: UserData): Flow<ApiState<String>>
    suspend fun logout()
    fun checkIsUserLogin():Boolean
     suspend fun createFavoriteDraft(draftOrderRequest: DraftOrderRequest):Flow<ApiState<DraftOrder>>
    suspend fun saveFavoriteDraftIdInFireBase(customerId:Long,favoriteDraft:Long): Flow<ApiState<String>>
    suspend fun createCustomer(userData: UserData): Flow<ApiState<CustomerEntity>>
    suspend fun singInCustomer(email: String): Flow<ApiState<UserData>>
    suspend fun getDraftFavoriteId(customerId: String): Flow<ApiState<String?>>
    fun validatePassword(password: String): Validation
    fun validateConfirmPassword(password: String, rePassword: String): Validation
    fun validateEmail(email: String): Validation
    fun validateUserName(userName: String): Validation
    fun reviews():List<Review>
    suspend fun getAllBrands(): Flow<ApiState<BrandsResponse>>
    suspend fun getMainCategories(): Flow<ApiState<MainCategoryResponse>>
    suspend fun getDiscountCode(
        priceRuleId: String,
        discountCodeId: String
    ): Flow<ApiState<DiscountCodeResponse>>

    suspend fun getProductsOfSpecificBrand(brandName:String): Flow<ApiState<AllProductsResponse>>
    suspend fun getAllProducts(): Flow<ApiState<List<Product>>>
    suspend fun getProductsByCollection(collectionId:Long): Flow<ApiState<List<Product>>>
    suspend fun filterProductsBySubCollection(collectionId:Long,productType:String): Flow<ApiState<List<Product>>>
    suspend fun getAddressesOfCustomer(customerId: Long): Flow<ApiState<List<Address>>>
    suspend fun updateAddressOfCustomer(
        customerId: Long,
        addressId: Long,
        updatedAddress: Address
    ): Flow<ApiState<Address>>

    suspend fun deleteAddressOfCustomer(
        customerId: Long,
        addressId: Long
    ): Flow<ApiState<Int>>

    suspend fun getAddressDetails(
        latitude: Double,
        longitude: Double
    ): Flow<ApiState<Address>>

    suspend fun addAddressToCustomer(
        customerId: Long,
        address: Address
    ): Flow<ApiState<Address>>
    suspend fun backUpDraftFavorite(draftOrderRequest: DraftOrderRequest,draftFavoriteId: Long): Flow<ApiState<String>>



    suspend fun getAllFavorites(): Flow<List<FavoriteEntity>>
    suspend fun getSingleFavorite(productId: Long): Flow<FavoriteEntity?>

    suspend fun deleteFavorite(productId:Long)
    suspend fun saveFavorite(favoriteEntity: FavoriteEntity):Long
    suspend fun checkProductInFavorite(productId: Long): Flow<ApiState<Int>>

    fun saveFavoriteDraftId(draftId: Long)
    fun saveCustomerId(customerId: Long)
    fun getFavoriteDraftId(): Long
    fun getCustomerId(): Long
    fun saveCartDraftId(draftId: Long)
    fun getLocalCartDraftId(): Long

    fun saveCustomerEmail(customerEmail:String)
    fun getCustomerEmail():String

    fun saveCustomerUserName(customerUserName:String)
    fun getCustomerUserName():String

    fun clearSharedPreferences()
    suspend fun getCurrencyUnit(): String
    suspend fun setCurrencyUnit(unit: String): String
    suspend fun getOrdersByCustomerID(customerId: Long): Flow<ApiState<OrdersResponse>>
    suspend fun getOrderById(id: Long): Flow<ApiState<OrderDetailsResponse>>
    suspend fun getImageOrders(productId: Long): Flow<ApiState<ProductResponse>>
    suspend fun setLanguage(language: String)
    suspend fun getCurrentLanguage(): String
    suspend fun getDraftCartId(customerId: String): Flow<ApiState<String?>>
    suspend fun saveCartDraftIdInFireBase(customerId:Long, cartDraftId:Long): Flow<ApiState<String>>
    suspend fun getAllProductsInCart(cartId: String): Flow<ApiState<List<Cart>>>
    fun addProductToCart(product: Product): Flow<ApiState<Boolean>>
}