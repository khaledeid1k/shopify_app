package com.kh.mo.shopyapp.repo

import com.kh.mo.shopyapp.datasource.FakeLocalSourceImp
import com.kh.mo.shopyapp.datasource.FakeRemoteSourceImp
import com.kh.mo.shopyapp.model.entity.CustomerEntity
import com.kh.mo.shopyapp.model.entity.FavoriteEntity
import com.kh.mo.shopyapp.model.entity.Validation
import com.kh.mo.shopyapp.model.request.DraftOrderRequest
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.model.request.order.CreateOrderRequest
import com.kh.mo.shopyapp.model.response.ads.DiscountCodeResponse
import com.kh.mo.shopyapp.model.response.ads.PriceRuleResponse
import com.kh.mo.shopyapp.model.response.allproducts.ProductResponse
import com.kh.mo.shopyapp.model.response.order.OrdersResponse
import com.kh.mo.shopyapp.model.ui.Address
import com.kh.mo.shopyapp.model.ui.Cart
import com.kh.mo.shopyapp.model.ui.DraftOrder
import com.kh.mo.shopyapp.model.ui.Review
import com.kh.mo.shopyapp.model.ui.allproducts.Product
import com.kh.mo.shopyapp.model.ui.maincategory.CustomCollection
import com.kh.mo.shopyapp.model.ui.orderdetails.LineItem
import com.kh.mo.shopyapp.remote.ApiState
import kotlinx.coroutines.flow.Flow

class FakeRepoImp(
    private val fakeRemoteSourceImp: FakeRemoteSourceImp,
    private val fakeLocalSourceImp: FakeLocalSourceImp
): Repo {
    override suspend fun getListOfSpecificProductsIds(productsIds: String): Flow<ApiState<List<FavoriteEntity>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsIdForDraftFavorite(draftFavoriteId: Long): Flow<ApiState<List<Long>>> {
        TODO("Not yet implemented")
    }

    override suspend fun singUpWithFireBase(userData: UserData): Flow<ApiState<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun singInWithFireBase(userData: UserData): Flow<ApiState<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }

    override suspend fun sendEmailVerification(): Flow<ApiState<String>> {
        TODO("Not yet implemented")
    }

    override fun checkIsUserLogin(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun checkEmailVerification(): Flow<ApiState<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun createFavoriteDraft(draftOrderRequest: DraftOrderRequest): Flow<ApiState<DraftOrder>> {
        TODO("Not yet implemented")
    }

    override suspend fun createCustomer(userData: UserData): Flow<ApiState<CustomerEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun singInCustomer(email: String): Flow<ApiState<UserData>> {
        TODO("Not yet implemented")
    }

    override suspend fun getDraftIds(customerId: String): Flow<ApiState<List<String>>> {
        TODO("Not yet implemented")
    }

    override fun validatePassword(password: String): Validation {
        TODO("Not yet implemented")
    }

    override fun validateConfirmPassword(password: String, rePassword: String): Validation {
        TODO("Not yet implemented")
    }

    override fun validateEmail(email: String): Validation {
        TODO("Not yet implemented")
    }

    override fun validateUserName(userName: String): Validation {
        TODO("Not yet implemented")
    }

    override fun reviews(): List<Review> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllBrands(): Flow<ApiState<List<Product>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMainCategories(): Flow<ApiState<List<CustomCollection>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getDiscountCode(
        priceRuleId: String,
        discountCodeId: String
    ): Flow<ApiState<DiscountCodeResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsOfSpecificBrand(brandName: String): Flow<ApiState<List<Product>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllProducts(): Flow<ApiState<List<Product>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSingleProduct(productId: Long): Flow<ApiState<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsByCollection(collectionId: Long): Flow<ApiState<List<Product>>> {
        TODO("Not yet implemented")
    }

    override suspend fun filterProductsBySubCollection(
        collectionId: Long,
        productType: String
    ): Flow<ApiState<List<Product>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAddressesOfCustomer(customerId: Long): Flow<ApiState<List<Address>>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateAddressOfCustomer(
        customerId: Long,
        addressId: Long,
        updatedAddress: Address
    ): Flow<ApiState<Address>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAddressOfCustomer(
        customerId: Long,
        addressId: Long
    ): Flow<ApiState<Int>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAddressDetails(
        latitude: Double,
        longitude: Double
    ): Flow<ApiState<Address>> {
        TODO("Not yet implemented")
    }

    override suspend fun addAddressToCustomer(
        customerId: Long,
        address: Address
    ): Flow<ApiState<Address>> {
        TODO("Not yet implemented")
    }

    override suspend fun backUpDraftFavorite(
        draftOrderRequest: DraftOrderRequest,
        draftFavoriteId: Long
    ): Flow<ApiState<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllFavorites(): Flow<List<FavoriteEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSingleFavorite(productId: Long): Flow<FavoriteEntity?> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFavorite(productId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun saveFavorite(favoriteEntity: FavoriteEntity): Long {
        TODO("Not yet implemented")
    }

    override suspend fun checkProductInFavorite(productId: Long): Flow<ApiState<Int>> {
        TODO("Not yet implemented")
    }

    override fun saveFavoriteDraftId(draftId: Long) {
        TODO("Not yet implemented")
    }

    override fun saveCustomerId(customerId: Long) {
        TODO("Not yet implemented")
    }

    override fun getFavoriteDraftId(): Long {
        TODO("Not yet implemented")
    }

    override fun getCustomerId(): Long {
        TODO("Not yet implemented")
    }

    override fun saveCartDraftId(draftId: Long) {
        TODO("Not yet implemented")
    }

    override fun getLocalCartDraftId(): Long {
        TODO("Not yet implemented")
    }

    override fun saveCustomerEmail(customerEmail: String) {
        TODO("Not yet implemented")
    }

    override fun getCustomerEmail(): String {
        TODO("Not yet implemented")
    }

    override fun saveCustomerUserName(customerUserName: String) {
        TODO("Not yet implemented")
    }

    override fun getCustomerUserName(): String {
        TODO("Not yet implemented")
    }

    override fun clearSharedPreferences() {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrencyUnit(): String {
        TODO("Not yet implemented")
    }

    override suspend fun setCurrencyUnit(unit: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun getOrdersByCustomerID(customerId: Long): Flow<ApiState<OrdersResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun getOrderById(id: Long): Flow<ApiState<List<LineItem>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getImageOrders(productId: Long): Flow<ApiState<ProductResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun setLanguage(language: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentLanguage(): String {
        TODO("Not yet implemented")
    }

    override suspend fun saveCartDraftIdAndFavoriteIdInFireBase(): Flow<ApiState<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllProductsInCart(): Flow<ApiState<List<Cart>>> {
        TODO("Not yet implemented")
    }

    override fun addProductToCart(product: Product): Flow<ApiState<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun updateCartItems(cartList: List<Cart>): Flow<ApiState<List<Cart>>> {
        TODO("Not yet implemented")
    }

    override fun getPriceRule(priceRuleId: String): Flow<ApiState<PriceRuleResponse>> {
        TODO("Not yet implemented")
    }

    override fun createOrder(orderRequest: CreateOrderRequest): Flow<ApiState<OrdersResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun clearDraftCart(draftOrderRequest: DraftOrderRequest): Flow<ApiState<String>> {
        TODO("Not yet implemented")
    }
}