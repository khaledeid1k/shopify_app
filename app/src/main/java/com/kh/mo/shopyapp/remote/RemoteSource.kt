package com.kh.mo.shopyapp.remote

import com.kh.mo.shopyapp.model.request.UserData
import kotlinx.coroutines.flow.Flow

interface RemoteSource {
      suspend fun storeData(userId: String, userData: UserData): Flow<ApiState<String>>
}