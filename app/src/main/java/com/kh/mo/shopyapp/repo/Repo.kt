package com.kh.mo.shopyapp.repo

import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.remote.ApiState
import kotlinx.coroutines.flow.Flow

interface Repo {
     suspend fun storeData(userId: String, userData: UserData): Flow<ApiState<String>>
}