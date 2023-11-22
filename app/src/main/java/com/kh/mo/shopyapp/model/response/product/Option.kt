package com.kh.mo.shopyapp.model.response.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Option(
    val id: Long?,
    val name: String?,
    val position: Int?,
    val product_id: Long?,
    val values: List<String?>?
) : Parcelable