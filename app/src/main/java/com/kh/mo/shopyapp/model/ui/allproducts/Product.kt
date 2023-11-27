package com.kh.mo.shopyapp.model.ui.allproducts


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id:Long=0L,
    val productImages: List<ProductImage> = emptyList(),
    val productType: String="",
    val productImage: ProductImage=ProductImage(),
    val title: String="",
    val productVariants: List<ProductVariant> = emptyList(),
    val productOptions: List<ProductOption> = emptyList(),
    val vendor: String="",
    val status: String="",
    var isFavorite: Boolean=false,
) : Parcelable