package com.kh.mo.shopyapp.local

import com.kh.mo.shopyapp.local.validation.AuthInputValidatorImpl
import com.kh.mo.shopyapp.local.validation.ValidationSateImpl
import com.kh.mo.shopyapp.model.entity.Validation
import com.kh.mo.shopyapp.model.request.UserData

class LocalSourceImp private constructor():LocalSource {
    private val validationSateImpl= ValidationSateImpl(AuthInputValidatorImpl())
    override fun validatePassword(password: String): Validation =
        validationSateImpl.validatePassword(password)


    override fun validateConfirmPassword(password: String, rePassword: String): Validation=
        validationSateImpl.validateConfirmPassword(password, rePassword)


    override fun validateEmail(email: String): Validation =
       validationSateImpl.validateEmail(email)



    override fun validateUserName(userName: String): Validation =
        validationSateImpl.validateUserName(userName)


    companion object {
        @Volatile
        private var instance: LocalSourceImp? = null
        fun getLocalSourceImpInstance(): LocalSourceImp {
            return instance ?: synchronized(this) {
                val instanceHolder = LocalSourceImp()
                instance = instanceHolder
                instanceHolder

            }
        }
    }

}