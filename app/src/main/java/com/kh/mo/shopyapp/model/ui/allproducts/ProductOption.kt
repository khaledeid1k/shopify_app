package com.kh.mo.shopyapp.model.ui.allproducts

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class  ProductOption(
    val values: List<String> = emptyList()
) : Parcelable