package com.kh.mo.shopyapp.remote

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.utils.Constants
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class RemoteSourceImp private constructor() : RemoteSource {
    override suspend fun storeData(userId: String, userData: UserData) = flow {
        emit(ApiState.Loading)
        val documentReference: DocumentReference = FirebaseFirestore.getInstance().collection(
            Constants.collectionPath
        ).document(userId)
        val user: HashMap<String, String> = HashMap()
        user[Constants.email] = userData.email
        user[Constants.password] = userData.password
        try {
            documentReference.set(user).await()
            emit(ApiState.Success("Sign up Successfully "))
        } catch (exception: Exception) {
            emit(ApiState.Failure("An error occurred: ${exception.message}"))
        }
    }


    companion object {
        @Volatile
        private var instance: RemoteSourceImp? = null
        fun getRemoteSourceImpInstance(): RemoteSourceImp {
            return instance ?: synchronized(this) {
                val instanceHolder = RemoteSourceImp()
                instance = instanceHolder
                instanceHolder

            }
        }
    }
}