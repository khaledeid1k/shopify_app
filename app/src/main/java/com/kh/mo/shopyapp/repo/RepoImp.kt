package com.kh.mo.shopyapp.repo

import android.util.Log
import com.kh.mo.shopyapp.local.LocalSource
import com.kh.mo.shopyapp.model.ui.Address
import com.kh.mo.shopyapp.model.entity.CustomerEntity
import com.kh.mo.shopyapp.model.entity.FavoriteEntity
import com.kh.mo.shopyapp.model.entity.LineItemEntity
import com.kh.mo.shopyapp.model.request.DraftOrderRequest
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.model.response.ads.DiscountCodeResponse
import com.kh.mo.shopyapp.model.response.allproducts.AllProductsResponse
import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.maincategory.MainCategoryResponse
import com.kh.mo.shopyapp.model.ui.DraftOrder
import com.kh.mo.shopyapp.model.ui.Review
import com.kh.mo.shopyapp.model.ui.allproducts.Product
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.remote.RemoteSource
import com.kh.mo.shopyapp.repo.mapper.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RepoImp private constructor(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource
) : Repo {
    private val TAG = "TAG RepoImp"

    init {
        GlobalScope.launch(Dispatchers.IO) {
            Log.i(TAG, "currencyRates: ${remoteSource.getCurrencyRate()}")
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

    override suspend fun checkCustomerExists(customerId: String) =
        remoteSource.checkCustomerExists(customerId)

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
                    ?.let { emit(ApiState.Success(it.convertToAllProducts()
                        .map { product->
                            changeProductFavoriteValue(product)
                        })) }
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
                            it.convertToAddressEntity()
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

    override suspend fun getAllLinetItems(): List<LineItemEntity>{
        return localSource.getAllLinetItems()
    }

    override suspend fun deleteLinetItems(productId: Long) {
        localSource.deleteLinetItems(productId)
    }

    override suspend fun saveLinetItems(lineItemEntity: LineItemEntity) {
        localSource.saveLinetItems(lineItemEntity)
    }

    override suspend fun getAllFavorites(): Flow<ApiState<List<FavoriteEntity>>> {
        return flow {
            emit(ApiState.Loading)
                localSource.getAllFavorites()?.let {
                    emit(ApiState.Success(it))
                }?:   emit(ApiState.Failure("No exit Data"))

        }.catch {
            emit(ApiState.Failure(it.message!!))
        }

    }

    override suspend fun deleteFavorite(productId: Long) {
        localSource.deleteFavorite(productId)
    }

    override suspend fun saveFavorite(product: Product) {
        localSource.saveFavorite(product.convertProductToFavoriteEntity())
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


