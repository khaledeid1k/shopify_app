package com.kh.mo.shopyapp.model.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    var customerId: Long = 0L,
    val id: Long = 0L,
    var address: String?,
    var markLocation: String? = "",
    var city: String?,
    var country: String?,
    var default: Boolean?,
    var name: String?,
    var phone: String?,
    var state: String?,
    var zip: Long?,
    var stateCode: String = ""
) : Parcelable
