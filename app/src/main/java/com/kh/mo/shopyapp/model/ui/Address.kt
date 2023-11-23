package com.kh.mo.shopyapp.model.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val customerId: Long,
    val id: Long,
    var address: String?,
    var markLocation: String?="",
    var city: String?,
    var country: String?,
    var default: Boolean?,
    var name: String?,
    var phone: String?,
    var state: String?,
) : Parcelable
