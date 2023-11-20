package com.kh.mo.shopyapp.repo

import com.kh.mo.shopyapp.local.LocalSource
import com.kh.mo.shopyapp.local.LocalSourceImp
import com.kh.mo.shopyapp.model.entity.Validation
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.remote.RemoteSource
import com.kh.mo.shopyapp.remote.RemoteSourceImp
import kotlinx.coroutines.flow.Flow

class RepoImp private constructor(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource
) : Repo {

    override suspend fun storeData(userId: String, userData: UserData) = remoteSource.storeData(userId, userData)





    override fun validateUserName(userName: String)= localSource.validateUserName(userName)
    override fun validateEmail(email: String) = localSource.validateEmail(email)
    override fun validatePassword(password: String) =localSource.validatePassword(password)
    override fun validateConfirmPassword(password: String, rePassword: String) = localSource.validateConfirmPassword(password, rePassword)

    companion object {
        @Volatile
        private var instance: RepoImp? = null
        fun getRepoImpInstance(remoteSource: RemoteSource, localSource: LocalSource): RepoImp {
            return instance ?: synchronized(this) {
                val instanceHolder = RepoImp(remoteSource, localSource)
                instance = instanceHolder
                instanceHolder

            }
        }
    }
}