package com.kh.mo.shopyapp.remote

import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import retrofit2.Response

interface RemoteSource {

    suspend fun getAllBrands(): Response<BrandsResponse>

}