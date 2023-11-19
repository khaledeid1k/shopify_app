package com.kh.mo.shopyapp.remote

import android.content.Context
import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.remote.service.Network
import retrofit2.Response

class RemoteSourceImp private constructor():RemoteSource {
    override suspend fun getAllBrands(): Response<BrandsResponse> {
        return Network.retrofitService.getAllBrands()
    }

    companion object{
        @Volatile
        private  var instance: RemoteSourceImp?=null
        fun getRemoteDataImpInstance(): RemoteSourceImp {
            return instance ?: synchronized(this){
                val instanceHolder= RemoteSourceImp()
                instance =instanceHolder
                instanceHolder

            }
        }
    }

}
