package com.kh.mo.shopyapp.model.ui.allproducts


import com.google.gson.annotations.SerializedName

data class Products(

    @SerializedName("product_type")
    val productType: String?

)