package com.kh.mo.shopyapp.repo

import com.kh.mo.shopyapp.model.entity.Validation
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.remote.ApiState
import kotlinx.coroutines.flow.Flow

interface Repo {
     suspend fun storeData(userId: String, userData: UserData): Flow<ApiState<String>>

     fun validatePassword(password: String): Validation
     fun validateConfirmPassword(password: String, rePassword: String): Validation
     fun validateEmail(email: String): Validation
     fun validateUserName(userName: String): Validation
}