package com.kh.mo.shopyapp.repo

import com.kh.mo.shopyapp.model.entity.CustomerEntity
import com.kh.mo.shopyapp.model.entity.Validation
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.model.response.login.Login
import com.kh.mo.shopyapp.remote.ApiState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Repo {
     suspend fun storeData(userId: Long, userData: UserData): Flow<ApiState<String>>
     suspend fun createCustomer(userData: UserData): Flow<ApiState<CustomerEntity>>
     suspend fun singIn(email: String):Flow<ApiState<UserData>>
     suspend fun checkCustomerExists(customerId: String) :Flow<ApiState<UserData>>


     fun validatePassword(password: String): Validation
     fun validateConfirmPassword(password: String, rePassword: String): Validation
     fun validateEmail(email: String): Validation
     fun validateUserName(userName: String): Validation


}