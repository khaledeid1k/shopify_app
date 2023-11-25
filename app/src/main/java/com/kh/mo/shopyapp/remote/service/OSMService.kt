package com.kh.mo.shopyapp.remote.service

import com.kh.mo.shopyapp.model.response.osm.NominatimResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OSMService {
    @GET("reverse")
    suspend fun getAddressDetails(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("zoom") zoom: Int = 10,
        @Query("namedetails") nameDetails: Int = 0,
        @Query("ddressdetails") addressDetails: Int = 1,
        @Query("format") format: String = "json",
        @Query("accept-language") language: String = "en"
    ): Response<NominatimResponse>
}