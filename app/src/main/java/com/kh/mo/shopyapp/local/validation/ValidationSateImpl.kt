package com.kh.mo.shopyapp.local.validation

import com.kh.mo.shopyapp.model.entity.Validation
import com.kh.mo.shopyapp.model.request.UserData

class ValidationSateImpl(private val authInputValidator: AuthInputValidator) : ValidationSate {

    override fun validatePassword(password: String): Validation =
        authInputValidator.passwordValidator(password)


    override fun validateConfirmPassword(password: String, rePassword: String): Validation =
   authInputValidator.confirmPasswordValidator(password, rePassword)


    override fun validateEmail(email: String): Validation =
     authInputValidator.emailValidator(email)


    override fun validateUserName(userName: String): Validation =
        authInputValidator.userNameValidator(userName)



}