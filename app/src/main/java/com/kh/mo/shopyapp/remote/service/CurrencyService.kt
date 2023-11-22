package com.kh.mo.shopyapp.remote.service

import com.kh.mo.shopyapp.BuildConfig
import com.kh.mo.shopyapp.model.response.currency.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {
    @GET("rates/latest")
    suspend fun getLatestCurrencyRate(
        @Query("apikey") apiKey: String = BuildConfig.currencyApiKey,
        @Query("symbols") symbols: String = "usd,egp,eur,GBP"
    ): Response<CurrencyResponse>
}