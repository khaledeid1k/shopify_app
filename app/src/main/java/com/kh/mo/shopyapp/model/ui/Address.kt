package com.kh.mo.shopyapp.model.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val address: String,
    val markLocation: String="",
    val city: String,
    val country: String,
    val default: Boolean,
    val name: String,
    val phone: String,
    val state: String,
) : Parcelable
