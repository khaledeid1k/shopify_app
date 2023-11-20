package com.kh.mo.shopyapp.local.validation

import com.kh.mo.shopyapp.model.entity.Validation

interface AuthInputValidator {

    fun passwordValidator(password: String): Validation
    fun confirmPasswordValidator(password: String, rePassword: String): Validation
    fun emailValidator(email: String): Validation
    fun userNameValidator(userName: String): Validation
}