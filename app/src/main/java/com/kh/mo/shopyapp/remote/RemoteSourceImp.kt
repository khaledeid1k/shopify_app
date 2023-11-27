package com.kh.mo.shopyapp.remote

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.kh.mo.shopyapp.model.request.AddressRequest
import com.kh.mo.shopyapp.model.request.CustomerDataRequest
import com.kh.mo.shopyapp.model.request.CustomerRequest
import com.kh.mo.shopyapp.model.request.DraftOrderRequest
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.model.response.orderdetails.OrderDetailsResponse
import com.kh.mo.shopyapp.model.response.allproducts.AllProductsResponse
import com.kh.mo.shopyapp.model.response.allproducts.ProductResponse
import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.create_customer.CustomerResponse
import com.kh.mo.shopyapp.model.response.currency.Rates
import com.kh.mo.shopyapp.model.response.draft_order.DraftOrderResponse
import com.kh.mo.shopyapp.model.response.login.Login
import com.kh.mo.shopyapp.model.response.maincategory.MainCategoryResponse
import com.kh.mo.shopyapp.model.response.osm.NominatimResponse
import com.kh.mo.shopyapp.model.response.order.OrdersResponse
import com.kh.mo.shopyapp.remote.service.Network
import com.kh.mo.shopyapp.utils.Constants
import com.kh.mo.shopyapp.utils.formatCurrentDate
import com.kh.mo.shopyapp.utils.isHoursPassed
import com.kh.mo.shopyapp.utils.parseDate
import kotlinx.coroutines.tasks.await
import retrofit2.Response

class RemoteSourceImp private constructor() : RemoteSource {
    private val TAG = "TAG RemoteSourceImp"
    private val network = Network.retrofitService
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFireStore = FirebaseFirestore.getInstance()

    override suspend fun saveFavoriteDraftIdInFireBase(
        customerId: Long,
        favoriteDraft: Long
    ): Task<Void> {
        val documentReference: DocumentReference = firebaseFireStore.collection(
            Constants.collectionPath
        ).document(customerId.toString())
        val user: HashMap<String, String> = HashMap()
        user[Constants.DRAFT_FAVORITE_ID] = favoriteDraft.toString()
        return documentReference.set(user)

    }

    override suspend fun getDraftFavoriteId(customerId: String): Task<DocumentSnapshot> {

         return   firebaseFireStore.collection(Constants.collectionPath)
                .document(customerId).get()
    }

    override suspend fun getListOfSpecificProductsIds( productsIds: String):Response<AllProductsResponse> {
        return  network.getListOfSpecificProductsIds(productsIds)
    }

    override suspend fun getProductsIdForDraftFavorite(draftFavoriteId: Long): Response<DraftOrderResponse> {
        return network.getProductsIdForDraftFavorite(draftFavoriteId)
    }


    override suspend fun singUpWithFireBase(userData: UserData): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(
            userData.email, userData.password
        )
    }

    override suspend fun singInWithFireBase(userData: UserData): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(
            userData.email, userData.password
        )
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }

    override fun checkIsUserLogin() = firebaseAuth.currentUser != null
    override suspend fun createFavoriteDraft(draftOrderRequest: DraftOrderRequest): Response<DraftOrderResponse> {
        return network.createFavoriteDraft(draftOrderRequest)
    }

    override suspend fun backUpDraftFavorite(draftOrderRequest: DraftOrderRequest,draftFavoriteId: Long): Response<DraftOrderResponse> {
        return network.backUpDraftFavorite(draftOrderRequest,draftFavoriteId)
    }


    override suspend fun createCustomer(customerDataRequest: CustomerDataRequest): Response<CustomerResponse> {
        return network.createCustomer(CustomerRequest(customerDataRequest))

    }

    override suspend fun singInCustomer(email: String): Response<Login> {
        return network.singIn(email)
    }

    override suspend fun getCurrencyRate(): Rates {
        return if (isCurrencyDbUpdated()) {
            getCurrencyRatesFromDB()
        } else {
            getLatestCurrencyRate() ?: getCurrencyRatesFromDB()
        }
    }

    override suspend fun isCurrencyDbUpdated(): Boolean {
        val documentSnapshot = getCurrencyDocument().get().await()
        return try {
            val storedDate = documentSnapshot.getString("date")?.let { parseDate(it) }
            val isOutOfDate = !isHoursPassed(storedDate)
            Log.i(TAG, "isCurrencyDbUpdated: $isOutOfDate")
            isOutOfDate
        } catch (e: Exception) {
            Log.i(TAG, "isCurrencyDbUpdated: exception ${e.message}")
            false
        }
    }

    private fun getCurrencyDocument(): DocumentReference {
        return FirebaseFirestore.getInstance().collection(Constants.CURRENCY_COLLECTION_ID)
            .document(Constants.CURRENCY_DOCUMENT_ID)
    }

    private suspend fun getLatestCurrencyRate(): Rates? {
        Log.i(TAG, "getLatestCurrencyRate: ")
        val response = Network.currencyService.getLatestCurrencyRate()
        if (response.isSuccessful) {
            response.body()?.let {
                updateCurrencyRatesDB(it.rates)
                return it.rates
            }
        }
        return null
    }

    private fun updateCurrencyRatesDB(rates: Rates) {
        val updatedRates = mapOf(
            "date" to formatCurrentDate(),
            "EGP" to rates.EGP,
            "GBP" to rates.GBP,
            "EUR" to rates.EUR,
            "USD" to rates.USD
        )
        getCurrencyDocument().update(updatedRates)
    }

    private suspend fun getCurrencyRatesFromDB(): Rates {
        val documentSnapshot = getCurrencyDocument().get().await()
        documentSnapshot.let {
            return Rates(
                EGP = it.getString("EGP").toString(),
                USD = it.getString("USD").toString(),
                GBP = it.getString("GBP").toString(),
                EUR = it.getString("EUR").toString()
            )
        }
    }


    override suspend fun getAllBrands(): Response<BrandsResponse> {
        return Network.retrofitService.getAllBrands()
    }

    override suspend fun getMainCategories(): Response<MainCategoryResponse> {
        return Network.retrofitService.getMainCategories()
    }

    override suspend fun getProductsOfSpecificBrand(brandName: String): Response<AllProductsResponse> {
        return Network.retrofitService.getProductsOfSpecificBrand(brandName)
    }

    override suspend fun getAllProducts(): Response<AllProductsResponse> {
        return Network.retrofitService.getAllProducts()
    }

    override suspend fun getProductsByCollection(collectionId: Long): Response<AllProductsResponse> {
        return Network.retrofitService.getProductsByCollection(collectionId)
    }

    override suspend fun filterProductsBySubCollection(
        collectionId: Long,
        productType: String
    ): Response<AllProductsResponse> {
        return Network.retrofitService.filterProductsBySubCollection(collectionId, productType)
    }

    override suspend fun getDiscountCode(priceRuleId: String, discountCodeId: String) =
        Network.retrofitService.getDiscountCode(priceRuleId, discountCodeId)

    override suspend fun getAddressesOfCustomer(customerId: Long) =
        Network.retrofitService.getAddressesOfCustomer(customerId)

    override suspend fun updateAddressOfCustomer(
        customerId: Long,
        addressId: Long,
        updatedAddress: AddressRequest
    ) = Network.retrofitService.updateAddressOfCustomer(customerId, addressId, updatedAddress)

    override suspend fun deleteAddressOfCustomer(customerId: Long, addressId: Long) =
        Network.retrofitService.deleteAddressOfCustomer(customerId, addressId)

    override suspend fun getAddressDetails(
        latitude: Double,
        longitude: Double,
        language: String
    ): Response<NominatimResponse> {
        val fullDetailsResponse =
            Network.nominatimService.getAddressDetails(latitude, longitude, zoom = 18)
        if (fullDetailsResponse.isSuccessful) {
            val cityResponse =
                Network.nominatimService.getAddressDetails(latitude, longitude, zoom = 10)
            if (cityResponse.isSuccessful) {
                fullDetailsResponse.body()?.address?.city = cityResponse.body()?.address?.city
            }
        }
        return fullDetailsResponse
    }

    override suspend fun addAddressToCustomer(customerId: Long, address: AddressRequest) =
        Network.retrofitService.addAddressToCustomer(customerId, address)


    override suspend fun getOrdersByCustomerID(customerId: Long): Response<OrdersResponse> {
        return Network.retrofitService.getOrdersByCustomerID(customerId)
    }

    override suspend fun getOrderByID(orderId: Long): Response<OrderDetailsResponse> {
        return Network.retrofitService.getOrderByID(orderId)
    }

    override suspend fun getImageOrders(productId: Long): Response<ProductResponse> {
        return Network.retrofitService.getImageOrders(productId)
    }

    override suspend fun createCartDraft(draftOrderRequest: DraftOrderRequest): Response<DraftOrderResponse> {
        return network.createFavoriteDraft(draftOrderRequest)
    }

    override suspend fun saveCartDraftIdInFireBase(
        customerId: Long,
        cartDraftId: Long,
        favoriteDraft: Long
    ): Task<Void> {
        val documentReference: DocumentReference = firebaseFireStore.collection(
            Constants.collectionPath
        ).document(customerId.toString())
        val user: HashMap<String, String> = HashMap()
        user[Constants.DRAFT_CART_ID] = cartDraftId.toString()
        user[Constants.DRAFT_FAVORITE_ID] = favoriteDraft.toString()
        return documentReference.set(user)
    }

    override suspend fun getAllProductIdsInCart(cartId: String): Response<DraftOrderResponse> {
        return network.getProductsIdForDraftFavorite(cartId.toLong())
    }

    companion object {
        @Volatile
        private var instance: RemoteSourceImp? = null
        fun getRemoteSourceImpInstance(): RemoteSourceImp {
            return instance ?: synchronized(this) {
                val instanceHolder = RemoteSourceImp()
                instance = instanceHolder
                instanceHolder

            }
        }
    }
}

