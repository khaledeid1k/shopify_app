package com.kh.mo.shopyapp.model.entity

import androidx.annotation.NonNull
import androidx.room.Entity

@Entity(tableName = "line_items_table", primaryKeys = ["product_id"])
data class LineItemEntity(
    @NonNull
    val product_id: Long ,
    val quantity: Int=1,
    val price: Double = 0.0,
    val title: String? = null,
    val variant_id: Long ,
)
