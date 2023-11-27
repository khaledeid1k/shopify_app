package com.kh.mo.shopyapp.model.ui

data class Cart(
    val quantity: Int?,
    val title: String?,
    val price: String?,
    val productId: Long?,
    val variantId: Long?,
    val variantTitle: String?,
    var imageSrc: String?
)
