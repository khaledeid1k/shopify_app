package com.kh.mo.shopyapp.remote

import com.kh.mo.shopyapp.model.response.allproducts.AllProductsResponse
import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.maincategory.MainCategoryResponse
import com.kh.mo.shopyapp.remote.service.Network
import retrofit2.Response

class RemoteSourceImp private constructor() : RemoteSource {
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

    override suspend fun getDiscountCode(priceRuleId: String, discountCodeId: String) =
        Network.retrofitService.getDiscountCode(priceRuleId, discountCodeId)
}

