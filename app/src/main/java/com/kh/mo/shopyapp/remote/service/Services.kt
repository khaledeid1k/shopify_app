package com.kh.mo.shopyapp.remote.service

import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import retrofit2.Response
import retrofit2.http.GET

interface Services {

    @GET("smart_collections.json")
    suspend fun getAllBrands(): Response<BrandsResponse>


}