package com.kh.mo.shopyapp.repo

import android.util.Log
import com.kh.mo.shopyapp.local.LocalSource
import com.kh.mo.shopyapp.model.entity.CustomerEntity
import com.kh.mo.shopyapp.model.entity.FavoriteEntity
import com.kh.mo.shopyapp.model.entity.LineItemEntity
import com.kh.mo.shopyapp.model.request.DraftOrderRequest
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.model.response.orderdetails.OrderDetailsResponse
import com.kh.mo.shopyapp.model.response.ads.DiscountCodeResponse
import com.kh.mo.shopyapp.model.response.allproducts.AllProductsResponse
import com.kh.mo.shopyapp.model.response.allproducts.ProductResponse
import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.maincategory.MainCategoryResponse
import com.kh.mo.shopyapp.model.ui.Address
import com.kh.mo.shopyapp.model.response.order.OrdersResponse
import com.kh.mo.shopyapp.model.ui.DraftOrder
import com.kh.mo.shopyapp.model.ui.Review
import com.kh.mo.shopyapp.model.ui.allproducts.Product
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.remote.RemoteSource
import com.kh.mo.shopyapp.repo.mapper.convertAllProductsResponseToProductsIds
import com.kh.mo.shopyapp.repo.mapper.convertCustomerResponseToCustomerEntity
import com.kh.mo.shopyapp.repo.mapper.convertDraftOrderResponseToDraftOrder
import com.kh.mo.shopyapp.repo.mapper.convertDraftOrderResponseToProductsIds
import com.kh.mo.shopyapp.repo.mapper.convertLoginToUserData
import com.kh.mo.shopyapp.repo.mapper.convertToAddress
import com.kh.mo.shopyapp.repo.mapper.convertToAddressRequest
import com.kh.mo.shopyapp.repo.mapper.convertToAllProducts
import com.kh.mo.shopyapp.repo.mapper.convertUserDataToCustomerData
import com.kh.mo.shopyapp.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class RepoImp private constructor(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource
) : Repo {
    private val TAG = "TAG RepoImp"

    override suspend fun updateCurrencyRates() {
        Log.i(TAG, "updateCurrencyRates: ${remoteSource.getCurrencyRate()}")
    }
    override suspend fun getListOfSpecificProductsIds( productsIds: List<Long>):Flow<ApiState<List<FavoriteEntity>>>
    {
        return flow {

            emit(ApiState.Loading)
            val draftFavorite =
                remoteSource.getListOfSpecificProductsIds(productsIds)
            Log.d(TAG, "asdasdasdasd: ${draftFavorite.body()}")

            if (draftFavorite.isSuccessful) {
                draftFavorite.body()
                    ?.let {
                        emit(ApiState.Success(it.convertAllProductsResponseToProductsIds())) }
            } else {
                emit(ApiState.Failure(draftFavorite.message()))
            }

        }.catch {
            emit(ApiState.Failure(it.message!!))
        }
    }

    override suspend fun getProductsIdForDraftFavorite(draftFavoriteId: Long): Flow<ApiState<List<Long>>> {

        return flow {

            emit(ApiState.Loading)
            val draftFavorite =
                remoteSource.getProductsIdForDraftFavorite(draftFavoriteId)
            if (draftFavorite.isSuccessful) {
                draftFavorite.body()
                    ?.let { emit(ApiState.Success(it.convertDraftOrderResponseToProductsIds())) }
            } else {
                emit(ApiState.Failure(draftFavorite.message()))
            }

        }.catch {
            emit(ApiState.Failure(it.message!!))
        }
    }



    override suspend fun singUpWithFireBase(userData: UserData) =
        flow {
            emit(ApiState.Loading)
            remoteSource.singUpWithFireBase(userData).await()
            emit(ApiState.Success("Sign up Successfully "))
        }.catch {
            emit(ApiState.Failure("An error occurred: ${it.message}"))
        }

    override suspend fun singInWithFireBase(userData: UserData)=
        flow {
            emit(ApiState.Loading)
            remoteSource.singInWithFireBase(userData).await()
            emit(ApiState.Success("Sign in Successfully "))
        }.catch {
            emit(ApiState.Failure("An error occurred: ${it.message}"))
        }

    override suspend fun logout() { remoteSource.logout() }

    override fun checkIsUserLogin()=remoteSource.checkIsUserLogin()
    override suspend fun createFavoriteDraft(draftOrderRequest: DraftOrderRequest): Flow<ApiState<DraftOrder>> {
        return flow {
            emit(ApiState.Loading)
            val favorite =   remoteSource.createFavoriteDraft(draftOrderRequest)
            if (!favorite.isSuccessful) {
                emit(ApiState.Failure(favorite.body().toString()))
            } else {
                favorite.body()?.let { responseBody ->
                    Log.d(TAG, "createFavoriteDraft: $responseBody")
                    emit(ApiState.Success(responseBody.convertDraftOrderResponseToDraftOrder()))
                } ?: run {
                    emit(ApiState.Failure("Api Error"))
                }
            }
        }.catch {
            emit(ApiState.Failure(it.message.toString()))
            Log.d(TAG, "createFavoriteDraft: ${it.message.toString()}")
        }

    }

    override suspend fun saveFavoriteDraftIdInFireBase(customerId:Long,favoriteDraft:Long)=
        flow {
            emit(ApiState.Loading)
            remoteSource.saveFavoriteDraftIdInFireBase(customerId, favoriteDraft).await()
            emit(ApiState.Success("Sign up Successfully "))
        }.catch {
            emit(ApiState.Failure("An error occurred: ${it.message}"))
        }




    override suspend fun createCustomer(userData: UserData): Flow<ApiState<CustomerEntity>> {

        return flow {
            emit(ApiState.Loading)
            val customer = remoteSource.createCustomer(userData.convertUserDataToCustomerData())
            if (!customer.isSuccessful) {
                emit(ApiState.Failure("NetWork Error"))
            } else {
                customer.body()?.let { responseBody ->
                    emit(ApiState.Success(responseBody.convertCustomerResponseToCustomerEntity()))
                } ?: run {
                    emit(ApiState.Failure("Email has already been taken"))
                }
            }
        }.catch {
            emit(ApiState.Failure(it.message!!))
        }
    }


    override suspend fun singInCustomer(email: String) = flow {
        emit(ApiState.Loading)
        val customer = remoteSource.singInCustomer(email)
        if (!customer.isSuccessful) {
            emit(ApiState.Failure("NetWork Error"))
        } else {
            customer.body()?.let { responseBody ->
                emit(ApiState.Success(responseBody.convertLoginToUserData()))
            } ?: run {
                emit(ApiState.Failure("Email not exist"))
            }
        }
    }.catch {
        emit(ApiState.Failure(it.message!!))
    }

    override suspend fun getDraftFavoriteId(customerId: String) =
        flow {
            var favoriteId:String?=""
            emit(ApiState.Loading)
            remoteSource.getDraftFavoriteId (customerId).addOnSuccessListener {
                if(it.exists()){
                    favoriteId=   it.data?.get(Constants.DRAFT_FAVORITE_ID) as String?
                }
            }.await()
            emit(ApiState.Success(favoriteId))
        }.catch {
            emit(ApiState.Failure("An error occurred: ${it.message}"))
        }



    override fun validateUserName(userName: String) = localSource.validateUserName(userName)
    override fun reviews(): List<Review> {
        val reviewsList = listOf(
            Review(
                name = "khaled eid",
                time = "2023-11-22 15:22",
                rating = 4,
                reviewText = "This product is amazing! I love how easy it is to use and how well it works."
            ),
            Review(
                name = "Omar ali",
                time = "2023-11-22 15:30 ",
                rating = 5,
                reviewText = "I am so happy with this purchase. It has exceeded all of my expectations."
            ),
            Review(
                name = "Mohand tarek",
                time = "2023-11-22 15:45 ",
                rating = 3,
                reviewText = "This product is okay. It does what it is supposed to do, but I am not blown away by it."
            ),
            Review(
                name = "Ehab ali",
                time = "2023-11-22 16:00 ",
                rating = 2,
                reviewText = "I am not impressed with this product. It is not worth the money."
            ),
            Review(
                name = "Moazz omar",
                time = "2023-11-22 16:15 ",
                rating = 1,
                reviewText = "This product is terrible. I would not recommend it to anyone."
            ),
            Review(
                name = "Hanna mohammed",
                time = "2023-11-22 16:30 ",
                rating = 4,
                reviewText = "Overall, I'm pretty satisfied with this product. It's not perfect, but it does the job well enough."
            ),
            Review(
                name = "Mahmoud tarek",
                time = "2023-11-22 16:45 ",
                rating = 5,
                reviewText = "I absolutely love this product! It's changed the way I do things for the better."
            ),
            Review(
                name = "Walla ramy",
                time = "2023-11-22 17:00 ",
                rating = 3,
                reviewText = "This product is just okay. It's not great, but it's not bad either."
            ),
            Review(
                name = "Hany rezk",
                time = "2023-11-22 17:15 ",
                rating = 2,
                reviewText = "I'm disappointed with this product. It didn't meet my expectations."
            ),
            Review(
                name = "Amar ali",
                time = "2023-11-22 17:30 ",
                rating = 1,
                reviewText = "This product is a waste of money. I would never buy it again."
            ),
            Review(
                name = "Tamer maher",
                time = "2023-11-22 17:45 ",
                rating = 4,
                reviewText = "I'm happy with this product. It's a good value for the price."
            ),
            Review(
                name = "abdall mohammed",
                time = "2023-11-22 18:00 ",
                rating = 5,
                reviewText = "I highly recommend this product to anyone looking for a high-quality solution."
            ),
            Review(
                name = "Hessen farok",
                time = "2023-11-22 18:15 ",
                rating = 3,
                reviewText = "This product is okay for basic needs, but it's not the best on the market."
            ),
            Review(
                name = "Ahmed kamel",
                time = "2023-11-22 18:30 ",
                rating = 2,
                reviewText = "I was hoping for more from this product. It didn't live up to my expectations."
            ),
            Review(
                name = "Mustafa ahmed",
                time = "2023-11-22 18:45 ",
                rating = 1,
                reviewText = "This product is a complete disaster. I would never buy it again."
            )
        )
        return reviewsList.shuffled().subList(1, 15)

    }

    override fun validateEmail(email: String) = localSource.validateEmail(email)
    override fun validatePassword(password: String) = localSource.validatePassword(password)
    override fun validateConfirmPassword(password: String, rePassword: String) =
        localSource.validateConfirmPassword(password, rePassword)

    override suspend fun getAllBrands(): Flow<ApiState<BrandsResponse>> {
        return flow {

            emit(ApiState.Loading)
            val allBrands =
                remoteSource.getAllBrands()
            if (allBrands.isSuccessful) {
                remoteSource.getAllBrands().body()
                    ?.let { emit(ApiState.Success(it)) }
            } else {
                emit(ApiState.Failure(allBrands.message()))
            }

        }.catch {
            emit(ApiState.Failure(it.message!!))
        }
    }

    override suspend fun getMainCategories(): Flow<ApiState<MainCategoryResponse>> {
        return flow {

            emit(ApiState.Loading)
            val mainCategories =
                remoteSource.getMainCategories()
            if (mainCategories.isSuccessful) {
                remoteSource.getMainCategories().body()
                    ?.let { emit(ApiState.Success(it)) }
            } else {
                emit(ApiState.Failure(mainCategories.message()))
            }

        }.catch {
            emit(ApiState.Failure(it.message!!))
        }

    }

    override suspend fun getProductsOfSpecificBrand(brandName: String): Flow<ApiState<AllProductsResponse>> {
        return flow {
            emit(ApiState.Loading)
            val brandItems =
                remoteSource.getProductsOfSpecificBrand(brandName)
            if (brandItems.isSuccessful) {
                remoteSource.getProductsOfSpecificBrand(brandName).body()
                    ?.let { emit(ApiState.Success(it)) }
            } else {
                emit(ApiState.Failure(brandItems.message()))
            }

        }.catch {
            emit(ApiState.Failure(it.message!!))
        }
    }

    override suspend fun getAllProducts(): Flow<ApiState<List<Product>>> {
        return flow {

            emit(ApiState.Loading)
            val allProducts =
                remoteSource.getAllProducts()
            if (allProducts.isSuccessful) {
                remoteSource.getAllProducts().body()
                    ?.let { emit(ApiState.Success(it.convertToAllProducts()
                        .map { product->
                            changeProductFavoriteValue(product)
                        })) }
            } else {
                emit(ApiState.Failure(allProducts.message()))
            }

        }.catch {
            emit(ApiState.Failure(it.message!!))
        }
    }

    override suspend fun getProductsByCollection(collectionId: Long): Flow<ApiState<List<Product>>> {
        return flow {
            emit(ApiState.Loading)
            val productsCategory =
                remoteSource.getProductsByCollection(collectionId)
            if (productsCategory.isSuccessful) {
                remoteSource.getProductsByCollection(collectionId).body()
                    ?.let { emit(ApiState.Success(it.convertToAllProducts()
                        .map { product->
                            changeProductFavoriteValue(product)
                        })) }
            } else {
                emit(ApiState.Failure(productsCategory.message()))
            }

        }.catch {
            emit(ApiState.Failure(it.message!!))
        }
    }

    override suspend fun filterProductsBySubCollection(
        collectionId: Long,
        productType: String
    ): Flow<ApiState<List<Product>>> {
        return flow {
            emit(ApiState.Loading)
            val productsSubCategory =
                remoteSource.filterProductsBySubCollection(collectionId,productType)
            if (productsSubCategory.isSuccessful) {
                remoteSource.filterProductsBySubCollection(collectionId,productType).body()
                    ?.let { emit(ApiState.Success(it.convertToAllProducts())) }
            } else {
                emit(ApiState.Failure(productsSubCategory.message()))
            }

        }.catch {
            emit(ApiState.Failure(it.message!!))
        }
    }

    override suspend fun getDiscountCode(
        priceRuleId: String,
        discountCodeId: String
    ): Flow<ApiState<DiscountCodeResponse>> {
        return flow {
            emit(ApiState.Loading)
            val response = remoteSource.getDiscountCode(priceRuleId, discountCodeId)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ApiState.Success(it))
                } ?: emit(ApiState.Failure("Null Response"))
            } else {
                emit(ApiState.Failure(response.message()))
            }
        }.catch {
            emit(ApiState.Failure(it.message.toString()))
        }
    }

    override suspend fun getAddressesOfCustomer(customerId: Long): Flow<ApiState<List<Address>>> {
        return flow {
            emit(ApiState.Loading)
            val response = remoteSource.getAddressesOfCustomer(customerId)
            if (response.isSuccessful) {
                response.body()?.let { addressRespone ->
                    emit(ApiState.Success(
                        addressRespone.addressResponses.map {
                            it.convertToAddress()
                        }
                    ))
                } ?: emit(ApiState.Failure("Null Response"))
            } else {
                emit(ApiState.Failure(response.message()))
            }
        }.catch {
            emit(ApiState.Failure(it.message.toString()))
        }
    }

    override suspend fun updateAddressOfCustomer(
        customerId: Long,
        addressId: Long,
        updatedAddress: Address
    ): Flow<ApiState<Address>> {
        return flow {
            emit(ApiState.Loading)
            val response =
                remoteSource.updateAddressOfCustomer(
                    customerId,
                    addressId,
                    updatedAddress.convertToAddressRequest()
                )
            if (response.isSuccessful) {
                response.body()?.let { address ->
                    emit(
                        ApiState.Success(
                            (address.convertToAddress())
                        )
                    )
                } ?: emit(ApiState.Failure("Null Response"))
            } else {
                Log.i(TAG, "updateAddressOfCustomer: faild ${response}")
                emit(ApiState.Failure(response.message()))
            }
        }.catch {
            Log.i(TAG, "updateAddressOfCustomer: excep ${it}")
            emit(ApiState.Failure(it.message.toString()))
        }
    }

    override suspend fun deleteAddressOfCustomer(
        customerId: Long,
        addressId: Long
    ): Flow<ApiState<Int>> {
        return flow {
            emit(ApiState.Loading)
            remoteSource.deleteAddressOfCustomer(
                customerId,
                addressId
            )
            emit(ApiState.Success(1))
        }.catch {
            Log.i(TAG, "updateAddressOfCustomer: excep ${it}")
            emit(ApiState.Failure(it.message.toString()))
        }
    }

    override suspend fun getAddressDetails(
        latitude: Double,
        longitude: Double
    ): Flow<ApiState<Address>> {
        return flow {
            emit(ApiState.Loading)
            val response = remoteSource.getAddressDetails(latitude, longitude)

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ApiState.Success(it.convertToAddress()))
                } ?: emit(ApiState.Failure("Null response"))
            } else {
                emit(ApiState.Failure(response.message()))
            }
        }.catch {
            emit(ApiState.Failure(it.message.toString()))
        }
    }

    override suspend fun addAddressToCustomer(
        customerId: Long,
        address: Address
    ): Flow<ApiState<Address>> {
        return flow {
            emit(ApiState.Loading)
            Log.i(TAG, "addAddressToCustomer: ${address.convertToAddressRequest()}")
            val response =
                remoteSource.addAddressToCustomer(
                    customerId,
                    address.convertToAddressRequest()
                )
            if (response.isSuccessful) {
                response.body()?.let { address ->
                    emit(
                        ApiState.Success(
                            (address.convertToAddress())
                        )
                    )
                } ?: emit(ApiState.Failure("Null Response"))
            } else {
                Log.i(TAG, "addAddressToCustomer: failed ${response}")
                emit(ApiState.Failure(response.message()))
            }
        }.catch {
            Log.i(TAG, "addAddressToCustomer: excep ${it}")
            emit(ApiState.Failure(it.message.toString()))
        }
    }

    override suspend fun backUpDraftFavorite(draftOrderRequest: DraftOrderRequest,draftFavoriteId: Long): Flow<ApiState<String>> {
        return flow {
            emit(ApiState.Loading)
            val backUpDraftFavorite =remoteSource.backUpDraftFavorite(draftOrderRequest,draftFavoriteId)
            if (!backUpDraftFavorite.isSuccessful) {
                emit(ApiState.Failure(backUpDraftFavorite.body().toString()))
            } else {
                backUpDraftFavorite.body()?.let {
                    emit(ApiState.Success("back Up Data successfully "))

                } ?: run {
                    emit(ApiState.Failure("Api Error"))
                }
            }
        }.catch {
            emit(ApiState.Failure(it.message.toString()))
            Log.d(TAG, "createFavoriteDraft: ${it.message.toString()}")
        }
    }

    override suspend fun getAllFavorites(): Flow<List<FavoriteEntity>>{
        return       localSource.getAllFavorites()

    }

    override suspend fun deleteFavorite(productId: Long) {
        localSource.deleteFavorite(productId)
    }

    override suspend fun saveFavorite(favoriteEntity: FavoriteEntity):Long {
        return localSource.saveFavorite(favoriteEntity)
    }

    override suspend fun checkProductInFavorite(productId: Long): Flow<ApiState<Int>> {

        return flow {
            emit(ApiState.Loading)
            emit(ApiState.Success(localSource.checkProductInFavorite(productId)))
        }.catch {
            emit(ApiState.Failure(it.message!!))
        }

    }

    override fun saveFavoriteDraftId(draftId: Long) {
        localSource.saveFavoriteDraftId(draftId)
    }

    override fun saveCustomerId(customerId: Long) {
        localSource.saveCustomerId(customerId)
    }

    override fun getFavoriteDraftId(): Long {
        return localSource.getFavoriteDraftId()
    }

    override fun getCustomerId(): Long {
      return  localSource.getCustomerId()
    }

    private suspend fun changeProductFavoriteValue(product: Product): Product {
        checkProductInFavorite(product.id).collect {
            if (it is ApiState.Success) {
                if (it.data != 0) {
                    product.isFavorite = true
                }
            }
        }
        return product
    }

    override suspend fun getCurrencyUnit(): String {
        return localSource.getCurrencyUnit()
    }

    override suspend fun setCurrencyUnit(unit: String) {
        localSource.setCurrencyUnit(unit)
    }

    override suspend fun getOrdersByCustomerID(customerId: Long): Flow<ApiState<OrdersResponse>> {
        return flow {
            emit(ApiState.Loading)
            val customerOrders =
                remoteSource.getOrdersByCustomerID(customerId)
            if (customerOrders.isSuccessful) {
                remoteSource.getOrdersByCustomerID(customerId).body()
                    ?.let { emit(ApiState.Success(it)) }
            } else {
                emit(ApiState.Failure(customerOrders.message()))
            }

        }.catch {
            emit(ApiState.Failure(it.message!!))
        }
    }

    override suspend fun getOrderById(id:Long): Flow<ApiState<OrderDetailsResponse>> {
        return flow {
            emit(ApiState.Loading)
            val order =
                remoteSource.getOrderByID(id)
            if (order.isSuccessful) {
                remoteSource.getOrderByID(id).body()
                    ?.let { emit(ApiState.Success(it)) }
            } else {
                emit(ApiState.Failure(order.message()))
            }

        }.catch {
            emit(ApiState.Failure(it.message!!))
        }
    }

    override suspend fun getImageOrders(productId: Long): Flow<ApiState<ProductResponse>> {
        return flow {
            emit(ApiState.Loading)
            val imageOrders =
                remoteSource.getImageOrders(productId)
            if (imageOrders.isSuccessful) {
                remoteSource.getImageOrders(productId).body()
                    ?.let { emit(ApiState.Success(it)) }
            } else {
                emit(ApiState.Failure(imageOrders.message()))
            }

        }.catch {
            emit(ApiState.Failure(it.message!!))
        }
    }

    companion object {
        @Volatile
        private var instance: RepoImp? = null
        fun getRepoImpInstance(remoteSource: RemoteSource, localSource: LocalSource): RepoImp {
            return instance ?: synchronized(this) {
                val instanceHolder = RepoImp(remoteSource, localSource)
                instance = instanceHolder
                instanceHolder

            }
        }
    }
}


