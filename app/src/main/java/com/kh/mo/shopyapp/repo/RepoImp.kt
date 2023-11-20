package com.kh.mo.shopyapp.repo

import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.maincategory.MainCategoryResponse
import com.kh.mo.shopyapp.remote.ApiSate
import com.kh.mo.shopyapp.remote.RemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RepoImp private constructor(private var remoteSource: RemoteSource):Repo{


    override suspend fun getAllBrands(): Flow<ApiSate<BrandsResponse>> {
        return flow {

            emit(ApiSate.Loading)
            val allBrands =
                remoteSource.getAllBrands()
            if (allBrands.isSuccessful) {
                remoteSource.getAllBrands().body()
                    ?.let { emit(ApiSate.Success(it)) }
            } else {
                emit(ApiSate.Failure(allBrands.message()))
            }

        }.catch {
            emit(ApiSate.Failure(it.message!!))
        }
    }

    override suspend fun getMainCategories(): Flow<ApiSate<MainCategoryResponse>> {
        return flow {

            emit(ApiSate.Loading)
            val mainCategories =
                remoteSource.getMainCategories()
            if (mainCategories.isSuccessful) {
                remoteSource.getMainCategories().body()
                    ?.let { emit(ApiSate.Success(it)) }
            } else {
                emit(ApiSate.Failure(mainCategories.message()))
            }

        }.catch {
            emit(ApiSate.Failure(it.message!!))
        }

    }

    companion object {
        @Volatile
        private var instance: RepoImp? = null
        fun getRepoImpInstance( remoteSource: RemoteSource): RepoImp {
            return instance ?: synchronized(this) {
                val instanceHolder = RepoImp(remoteSource)
                instance = instanceHolder
                instanceHolder

            }
        }
    }


}


