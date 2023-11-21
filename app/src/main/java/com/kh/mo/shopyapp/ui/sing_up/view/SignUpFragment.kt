package com.kh.mo.shopyapp.ui.sing_up.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentSignUpBinding
import com.kh.mo.shopyapp.model.entity.CustomerEntity
import com.kh.mo.shopyapp.model.entity.Validation
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.sing_in.view.SignInFragmentDirections
import com.kh.mo.shopyapp.ui.sing_up.viewmodel.SignUpViewModel
import com.kh.mo.shopyapp.utils.getText
import kotlinx.coroutines.launch


class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpViewModel>() {

    override val layoutIdFragment: Int = R.layout.fragment_sign_up
    override fun getViewModelClass() = SignUpViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkUserNameValidation()
        checkEmailValueValidation()
        checkPasswordValidation()
        checkConfirmPasswordValidation()
        createUser()
        observeCreateCustomerResult()
        observeSaveCustomerInFireBaseResult ()
        navigateToSingIn()
    }


    private fun printValidationResult(validation: Validation, field: TextInputLayout) {
        binding.apply {
            if (validation.isValid) {
                field.isErrorEnabled = false
            } else {
                field.isErrorEnabled = true
                field.error = validation.message
            }
        }
    }

    private fun checkValueValidation(
        textInputEditText: TextInputEditText,
        validate: (text: String) -> Unit,
    ) {
        binding.apply {
            textInputEditText.getText {
                validate(it)
            }
        }
    }


    private fun checkUserNameValidation() {
        checkValueValidation(
            binding.userNameValue,
        ) {
            printValidationResult(viewModel.validateUserName(it), binding.userNameTextField)
        }

    }

    private fun checkEmailValueValidation() {

        checkValueValidation(
            binding.emailValue,
        ) {
            printValidationResult(viewModel.validateEmail(it), binding.emailTextField)
        }

    }

    private fun checkPasswordValidation() {
        checkValueValidation(
            binding.passwordValue,
        ) {
            printValidationResult(viewModel.validatePassword(it), binding.passwordTextField)
        }

    }

    private fun checkConfirmPasswordValidation() {
        checkValueValidation(
            binding.confirmPasswordValue,
        ) {
            val passwordValue = binding.passwordValue.text.toString()
            printValidationResult(
                viewModel.validateConfirmPassword(passwordValue, it),
                binding.confirmPasswordTextField
            )
        }
    }

    private fun checkDataValidation(createUser: (userData: UserData) -> Unit) {
        binding.singUpB.setOnClickListener {
            binding.userNameValue.text?.trim().toString().let { userNameValue ->
                val validateUserName = viewModel.validateUserName(userNameValue)
                if (!validateUserName.isValid) {
                    printValidationResult(validateUserName, binding.userNameTextField)
                    return@setOnClickListener
                }
            }
            binding.emailValue.text?.trim().toString().let { emailValue ->
                val validateEmail = viewModel.validateEmail(emailValue)
                if (!validateEmail.isValid) {
                    printValidationResult(validateEmail, binding.emailTextField)
                    return@setOnClickListener
                }
            }
            binding.passwordValue.text?.trim().toString().let { passwordValue ->
                val validatePassword = viewModel.validatePassword(passwordValue)
                if (!validatePassword.isValid) {
                    printValidationResult(validatePassword, binding.passwordTextField)
                    return@setOnClickListener
                }
            }
            binding.confirmPasswordValue.text?.trim().toString().let { confirmPasswordValue ->
                val passwordValue = binding.passwordValue.text?.trim().toString()
                val validateConfirmPassword =
                    viewModel.validateConfirmPassword(passwordValue, confirmPasswordValue)
                if (!validateConfirmPassword.isValid) {
                    printValidationResult(validateConfirmPassword, binding.confirmPasswordTextField)
                    return@setOnClickListener
                }
            }
            createUser(
                UserData(
                    userName=   binding.userNameValue.text?.trim().toString(),
                  email=  binding.emailValue.text?.trim().toString(),
                 password=   binding.passwordValue.text?.trim().toString(),
                )
            )

        }
    }


    private fun createUser() {
        checkDataValidation {
            viewModel.createUser(it)
        }
    }


    private fun observeCreateCustomerResult() {
        lifecycleScope.launch {
            viewModel.createCustomer.collect {
                when (it) {
                    is ApiState.Failure -> {}
                    ApiState.Loading -> {}
                    is ApiState.Success -> {
                        storeInFirebase(it.data)
                    }
                }
            }
        }
    }

    private fun storeInFirebase(data: CustomerEntity){
        viewModel.storeCustomerFireBase(
            data.id,
            UserData(
                userName=     data.first_name,
                email=   data.email,
                password=     binding.passwordValue.text?.trim().toString()
            )
        )
    }

    private fun observeSaveCustomerInFireBaseResult (){
        lifecycleScope.launch {
            viewModel.saveCustomerFireBase.collect {
                when(it){
                    is ApiState.Failure -> {}
                    ApiState.Loading -> {}
                    is ApiState.Success ->{
                        Toast.makeText(requireContext(), "Sing Up Successfully ", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(
                            SignUpFragmentDirections.actionSignUpFragmentToHomeFragment()
                        )

                    }

                }

            }
        }
    }


   private fun navigateToSingIn(){
       binding.goToLogin.setOnClickListener {
           findNavController().navigate(
               SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
           )
       }
    }
}