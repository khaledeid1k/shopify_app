package com.kh.mo.shopyapp.model.response.osm


import com.google.gson.annotations.SerializedName

data class NominatimResponse(
    @SerializedName("place_id")
    var placeId: Int? = null,
    @SerializedName("licence")
    var licence: String? = null,
    @SerializedName("osm_type")
    var osmType: String? = null,
    @SerializedName("osm_id")
    var osmId: Long? = null,
    @SerializedName("lat")
    var lat: String? = null,
    @SerializedName("lon")
    var lon: String? = null,
    @SerializedName("class")
    var mClass: String? = null,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("place_rank")
    var placeRank: Int? = null,
    @SerializedName("importance")
    var importance: Double? = null,
    @SerializedName("addresstype")
    var addresstype: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("display_name")
    var displayName: String? = null,
    @SerializedName("address")
    var address: AddressDetailsResponse? = AddressDetailsResponse(),
    @SerializedName("boundingbox")
    var boundingbox: ArrayList<String> = arrayListOf()
)