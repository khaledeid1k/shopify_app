package com.kh.mo.shopyapp.remote

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.kh.mo.shopyapp.model.request.CustomerDataRequest
import com.kh.mo.shopyapp.model.request.CustomerRequest
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.model.response.create_customer.CustomerResponse
import com.kh.mo.shopyapp.model.response.login.Login
import com.kh.mo.shopyapp.remote.service.Network
import com.kh.mo.shopyapp.utils.Constants
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import retrofit2.Response

class RemoteSourceImp private constructor() : RemoteSource {
    private val network = Network.retrofitService
    override suspend fun storeData(userId: Long, userData: UserData) = flow {
        emit(ApiState.Loading)
        val documentReference: DocumentReference = FirebaseFirestore.getInstance().collection(
            Constants.collectionPath
        ).document(userId.toString())
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

    override suspend fun createCustomer(customerDataRequest: CustomerDataRequest): Response<CustomerResponse> {
        return network.createCustomer(CustomerRequest(customerDataRequest))

    }

    override suspend fun singIn(email: String): Response<Login> {
        return network.singIn(email)
    }

    override suspend fun checkCustomerExists(customerId: String) = flow {
        var email = ""
        var password = ""
        emit(ApiState.Loading)
        val collection =
            FirebaseFirestore.getInstance().collection(Constants.collectionPath)
                .document(customerId)
        val documentSnapshot = collection.get().await()
        if (documentSnapshot.exists()) {
            email = documentSnapshot.getString(Constants.email).toString()
            password = documentSnapshot.getString(Constants.password).toString()

        }
        emit(ApiState.Success(UserData(email = email, password = password)))

    }.catch {
        emit(ApiState.Failure(it.message.toString()))
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