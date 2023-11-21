package com.kh.mo.shopyapp.model.request

data class UserData(
    val id:Long=0L,
    val userName:String="",
    val email: String="",
    val password: String="",
)