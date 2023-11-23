package com.kh.mo.shopyapp.model.entity


data class AddressEntity(
    val address: String,
    val markLocation: String,
    val city: String,
    val country: String,
    val default: Boolean,
    val name: String,
    val phone: String,
    val state: String,
)
