package com.kh.mo.shopyapp.remote

import com.kh.mo.shopyapp.model.request.CustomerDataRequest
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.model.response.create_customer.CustomerResponse
import com.kh.mo.shopyapp.model.response.login.Login
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RemoteSource {
      suspend fun storeData(userId: Long, userData: UserData): Flow<ApiState<String>>
      suspend fun checkCustomerExists(customerId: String) :Flow<ApiState<UserData>>
      suspend fun createCustomer(customerDataRequest:CustomerDataRequest): Response<CustomerResponse>
      suspend fun singIn(email: String): Response<Login>

}