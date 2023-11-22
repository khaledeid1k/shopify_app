package com.kh.mo.shopyapp.model.response.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val admin_graphql_api_id: String?,
    val created_at: String?,
    val height: Int?,
    val id: Long?,
    val position: Int?,
    val product_id: Long?,
    val src: String?,
    val updated_at: String?,
    val variant_ids: List<Long?>?,
    val width: Int?
) : Parcelable