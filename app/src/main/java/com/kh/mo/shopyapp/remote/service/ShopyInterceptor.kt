package com.kh.mo.shopyapp.remote.service

import com.kh.mo.shopyapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ShopyInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()

        val modifiedRequest: Request = originalRequest.newBuilder()
            .header("Accept", "application/json")
            .header("X-Shopify-Access-Token", BuildConfig.accessToken)
            .build()
        return chain.proceed(modifiedRequest)
    }
}