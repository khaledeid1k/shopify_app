package com.kh.mo.shopyapp.model.request

data class CustomerDataRequest(
    val first_name: String,
    val email: String,
    val verified_email: Boolean,
    val password: String,
    val password_confirmation: String
    )
