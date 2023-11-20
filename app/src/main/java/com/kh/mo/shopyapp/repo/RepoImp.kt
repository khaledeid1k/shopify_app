package com.kh.mo.shopyapp.repo

import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.remote.RemoteSource
import com.kh.mo.shopyapp.remote.RemoteSourceImp
import kotlinx.coroutines.flow.Flow

class RepoImp private constructor(private val remoteSource: RemoteSource):Repo {

    override suspend fun storeData(userId: String, userData: UserData)
    = remoteSource.storeData(userId,userData)



    companion object {
        @Volatile
        private var instance: RepoImp? = null
        fun getRepoImpInstance(remoteSource: RemoteSource): RepoImp {
            return instance ?: synchronized(this) {
                val instanceHolder = RepoImp(remoteSource)
                instance = instanceHolder
                instanceHolder

            }
        }
    }
}