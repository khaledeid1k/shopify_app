package com.kh.mo.shopyapp.local.validation

import com.kh.mo.shopyapp.model.entity.Validation
import com.kh.mo.shopyapp.utils.Constants

class AuthInputValidatorImpl : AuthInputValidator {
    override fun passwordValidator(password: String): Validation {
        val digitPattern = Regex(".*[0-9].*")
        val lowercasePattern = Regex(".*[a-z].*")
        val uppercasePattern = Regex(".*[A-Z].*")
        val specialCharacterPattern = Regex(".*[@#$%^&+=].*")
        val lengthPattern = Regex(".{8,20}")

        if (!password.matches(digitPattern)) {
            return Validation(
                false,
                "Password must contain at least one digit",
                Constants.ErrorPassword
            )
        }
        if (!password.matches(lowercasePattern)) {
            return Validation(
                false,
                "Password must contain at least one lowercase letter.",
                Constants.ErrorPassword
            )
        }
        if (!password.matches(uppercasePattern)) {
            return Validation(
                false,
                "Password must contain at least one uppercase letter.",
                Constants.ErrorPassword
            )
        }
        if (!password.matches(specialCharacterPattern)) {
            return Validation(
                false,
                "Password must contain at least one special character (@#$%^&+=).",
                Constants.ErrorPassword
            )
        }
        if (!password.matches(lengthPattern)) {
            return Validation(
                false,
                "Password length must be between 8 and 20 characters.",
                Constants.ErrorPassword
            )
        }

        return Validation(true, "", "")


    }

    override fun confirmPasswordValidator(password: String, rePassword: String): Validation {
        return if (password != rePassword) {
            Validation(false, "Password not Equal", Constants.ErrorRePassword)
        } else {
            Validation(true, "", "")

        }

    }

    override fun emailValidator(email: String): Validation {
        val lengthPattern: Boolean = email.matches(Regex(".{8,40}"))


        if (!email.matches(Regex(".*@\\w+\\..*"))) {
            return Validation(false, "Invalid placement of '@' symbol.", Constants.ErrorEmail)
        }
        if (!email.contains("@")) {
            return Validation(false, "Missing '@' symbol.", Constants.ErrorEmail)
        }
        if (!email.endsWith(".com") && !email.endsWith(".org") && !email.endsWith(".net")) {
            return Validation(
                false,
                "Invalid domain. Supported domains are .com, .org, and .net.",
                Constants.ErrorEmail
            )
        }

        return if (!lengthPattern) {
            Validation(
                false,
                "Email length must be between 15 and 30 characters.",
                Constants.ErrorEmail
            )
        } else Validation(true, "", "")


    }

    override fun userNameValidator(userName: String): Validation {
        val digitPattern = Regex(".*[0-9].*")
        val lowercasePattern = Regex(".*[a-z].*")
        val uppercasePattern = Regex(".*[A-Z].*")
        val lengthPattern: Boolean = userName.matches(Regex(".{4,8}"))

        if (!userName.matches(digitPattern)) {
            return Validation(
                false,
                "User name must contain at least one digit",
                Constants.ErrorEmail
            )
        }
        if (!userName.matches(lowercasePattern)) {
            return Validation(
                false,
                "User name must contain at least one lowercase letter.",
                Constants.ErrorEmail
            )
        }
        if (!userName.matches(uppercasePattern)) {
            return Validation(
                false,
                "User name must contain at least one uppercase letter.",
                Constants.ErrorEmail
            )
        }
        return if (!lengthPattern) {
            Validation(
                false,
                "User name length must be between 4 and 8 characters.",
                Constants.ErrorEmail
            )
        } else Validation(true, "", "")

    }


}