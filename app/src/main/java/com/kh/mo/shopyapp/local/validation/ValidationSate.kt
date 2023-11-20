package com.kh.mo.shopyapp.local.validation

import com.kh.mo.shopyapp.model.entity.Validation
import com.kh.mo.shopyapp.model.request.UserData

interface ValidationSate {

    fun validatePassword(password: String): Validation
    fun validateConfirmPassword(password: String, rePassword: String): Validation
    fun validateEmail(email: String): Validation
    fun validateUserName(userName: String): Validation
}