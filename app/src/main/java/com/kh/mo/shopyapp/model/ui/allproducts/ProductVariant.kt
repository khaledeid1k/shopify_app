package com.kh.mo.shopyapp.model.ui.allproducts

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductVariant(
    val id: Long=0L,
    var price: String="",
    val productId: Long=0,
    val title: String="",
    val weightUnit: String=""
) : Parcelable