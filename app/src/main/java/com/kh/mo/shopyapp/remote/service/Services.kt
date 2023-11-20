package com.kh.mo.shopyapp.remote.service

import com.kh.mo.shopyapp.model.request.CustomerRequest
import com.kh.mo.shopyapp.model.response.create_customer.CustomerResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Services {

    @POST("customers.json")
    suspend fun createCustomer(@Body customer : CustomerRequest): Response<CustomerResponse>



}