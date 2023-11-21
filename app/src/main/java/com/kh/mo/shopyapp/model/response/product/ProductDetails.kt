package com.kh.mo.shopyapp.model.response.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ProductDetails(
    val admin_graphql_api_id: String?,
    val body_html: String?,
    val created_at: String?,
    val handle: String?,
    val id: Long?,
    val image: Image?,
    val images: List<Image?>?,
    val options: List<Option?>?,
    val product_type: String?,
    val published_at: String?,
    val published_scope: String?,
    val status: String?,
    val tags: String?,
    val template_suffix: String?,
    val title: String?,
    val updated_at: String?,
    val variants: List<Variant?>?,
    val vendor: String?
) : Parcelable