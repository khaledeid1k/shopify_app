package com.kh.mo.shopyapp.model.entity

import androidx.annotation.NonNull
import androidx.room.Entity


@Entity(tableName = "favorite_table", primaryKeys = ["productId","customerId"])
data class FavoriteEntity(
    @NonNull
    val productId: Long,
    @NonNull
    val customerId:Long,
    val images: List<ImageEntity>,
    val productType: String,
    val image: ImageEntity,
    val title: String,
    val variants: List<VariantEntity>,
    val options: List<OptionEntity>,
    val vendor: String?,
    val status: String?,
)

data class VariantEntity(
    val id: Long?,
    val price: String?,
    val productId: Long?,
    val title: String?,
    val weightUnit: String?
)

data class OptionEntity(
    val values: List<String>
)

data class ImageEntity(
    val src: String
)
