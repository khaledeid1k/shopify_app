package com.kh.mo.shopyapp.remote.service

import com.kh.mo.shopyapp.model.request.CustomerRequest
import com.kh.mo.shopyapp.model.response.create_customer.CustomerResponse
import com.kh.mo.shopyapp.model.response.login.Login
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Services {

    @POST("customers.json")
    suspend fun createCustomer(@Body customer : CustomerRequest): Response<CustomerResponse>

    @GET("customers.json")
    suspend fun singIn(@Query("email") email: String): Response<Login>


}