package com.kh.mo.shopyapp.model.response.osm


import com.google.gson.annotations.SerializedName

data class AddressDetailsResponse(
    var city: String? = "",
    @SerializedName("house_number")
    var houseNumber: String? = null,
    @SerializedName("road")
    var road: String? = null,
    @SerializedName("village")
    var village: String? = null,
    @SerializedName("region")
    var region: String? = null,
    @SerializedName("ISO3166-2-lvl4")
    var ISO: String? = null,
    @SerializedName("postcode")
    var postcode: String? = null,
    @SerializedName("country")
    var country: String? = null,
    @SerializedName("country_code")
    var countryCode: String? = null
)