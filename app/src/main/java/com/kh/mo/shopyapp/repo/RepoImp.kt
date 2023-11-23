package com.kh.mo.shopyapp.repo

import android.util.Log
import com.kh.mo.shopyapp.local.LocalSource
import com.kh.mo.shopyapp.model.ui.Address
import com.kh.mo.shopyapp.model.entity.CustomerEntity
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.model.response.ads.DiscountCodeResponse
import com.kh.mo.shopyapp.model.response.allproducts.AllProductsResponse
import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.maincategory.MainCategoryResponse
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.remote.RemoteSource
import com.kh.mo.shopyapp.repo.mapper.convertCustomerResponseToCustomerEntity
import com.kh.mo.shopyapp.repo.mapper.convertLoginToUserData
import com.kh.mo.shopyapp.repo.mapper.convertToAddressEntity
import com.kh.mo.shopyapp.repo.mapper.convertToUpdateRequestAddress
import com.kh.mo.shopyapp.repo.mapper.convertUserDataToCustomerData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

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

    override suspend fun storeData(userId: Long, userData: UserData) =
        remoteSource.storeData(userId, userData)


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


    override suspend fun singIn(email: String) = flow {
        emit(ApiState.Loading)
        val customer = remoteSource.singIn(email)
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

    override suspend fun getAllProducts(): Flow<ApiState<AllProductsResponse>> {
        return flow {

            emit(ApiState.Loading)
            val allProducts =
                remoteSource.getAllProducts()
            if (allProducts.isSuccessful) {
                remoteSource.getAllProducts().body()
                    ?.let { emit(ApiState.Success(it)) }
            } else {
                emit(ApiState.Failure(allProducts.message()))
            }

        }.catch {
            emit(ApiState.Failure(it.message!!))
        }
    }

    override suspend fun getProductsByCollection(collectionId: Long): Flow<ApiState<AllProductsResponse>> {
        return flow {
            emit(ApiState.Loading)
            val productsCategory =
                remoteSource.getProductsByCollection(collectionId)
            if (productsCategory.isSuccessful) {
                remoteSource.getProductsByCollection(collectionId).body()
                    ?.let { emit(ApiState.Success(it)) }
            } else {
                emit(ApiState.Failure(productsCategory.message()))
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

    override suspend fun updateAddressOfCustomer(
        customerId: Long,
        addressId: Long,
        updatedAddress: Address
    ): Flow<ApiState<Address>> {
        return flow {
            emit(ApiState.Loading)
            val response =
                remoteSource.updateAddressOfCustomer(customerId, addressId, updatedAddress.convertToUpdateRequestAddress())
            if (response.isSuccessful) {
                response.body()?.let { address ->
                    emit(
                        ApiState.Success(
                            (address.convertToAddressEntity())
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


