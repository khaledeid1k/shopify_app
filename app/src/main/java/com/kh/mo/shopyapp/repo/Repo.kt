package com.kh.mo.shopyapp.repo

import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.remote.ApiSate
import kotlinx.coroutines.flow.Flow

interface Repo {

    suspend fun getAllBrands(): Flow<ApiSate<BrandsResponse>>

}