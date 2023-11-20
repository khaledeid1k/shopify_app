package com.kh.mo.shopyapp.remote

import com.kh.mo.shopyapp.model.request.CustomerDataRequest
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.model.response.create_customer.CustomerResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RemoteSource {
      suspend fun storeData(userId: Long, userData: UserData): Flow<ApiState<String>>
      suspend fun createCustomer(customerDataRequest:CustomerDataRequest): Response<CustomerResponse>
}