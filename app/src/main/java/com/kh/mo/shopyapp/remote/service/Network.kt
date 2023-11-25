package com.kh.mo.shopyapp.remote.service

import com.kh.mo.shopyapp.BuildConfig
import com.kh.mo.shopyapp.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Network {

    private val client = OkHttpClient.Builder().addInterceptor(ShopyInterceptor()).build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val retrofitService: Services by lazy {
        retrofit.create(Services::class.java)
    }

    private val currencyRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.CURRENCY_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val currencyService: CurrencyService by lazy {
        currencyRetrofit.create(CurrencyService::class.java)
    }

    private val nominatimRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.NOMINATIM_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val nominatimService: OSMService by lazy {
        nominatimRetrofit.create(OSMService::class.java)
    }
}