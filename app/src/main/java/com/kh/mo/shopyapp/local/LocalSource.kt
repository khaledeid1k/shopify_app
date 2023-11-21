package com.kh.mo.shopyapp.local

import com.kh.mo.shopyapp.model.entity.Validation

interface LocalSource {

    fun validatePassword(password: String): Validation
    fun validateConfirmPassword(password: String, rePassword: String): Validation
    fun validateEmail(email: String): Validation
    fun validateUserName(userName: String): Validation

}