package com.kh.mo.shopyapp.ui.sing_in.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentSignUpBinding
import com.kh.mo.shopyapp.databinding.FragmentSinginBinding
import com.kh.mo.shopyapp.model.entity.Validation
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.sing_in.viewmodel.SignInViewModel
import com.kh.mo.shopyapp.ui.sing_up.view.SignUpFragmentDirections
import com.kh.mo.shopyapp.utils.getText
import kotlinx.coroutines.launch


class SignInFragment : BaseFragment<FragmentSinginBinding, SignInViewModel>() {
    override val layoutIdFragment=R.layout.fragment_singin

    override fun getViewModelClass()=SignInViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLoginResult()
        observeLoginInFireBase()
        checkEmailValueValidation()
        checkPasswordValidation()
        singIn()
        navigateToSingUp()
    }

   private fun observeLoginResult(){
        lifecycleScope.launch {
            viewModel.singIn.collect{
                when(it){
                    is ApiState.Failure -> {}
                    ApiState.Loading -> {}
                    is ApiState.Success -> {viewModel.checkCustomerExists(it.data.id.toString())}
                }
            }
        }
    }
   private fun observeLoginInFireBase(){
        lifecycleScope.launch {
            viewModel.checkCustomerExists .collect{
                when(it){
                    is ApiState.Failure -> {}
                    ApiState.Loading -> {}
                    is ApiState.Success -> {
                   if(it.data.password==binding.passwordValue.text?.trim().toString()){
                       Toast.makeText(requireContext(), "Sing in Successfully", Toast.LENGTH_SHORT).show()
                       findNavController().navigate(
                               SignInFragmentDirections.actionSignInFragmentToHomeFragment()
                       )
                   }
                    }
                }
            }
        }
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

    private fun checkDataValidation(getUserData: (userData: UserData) -> Unit) {
        binding.singInB.setOnClickListener {
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
            getUserData(
                UserData(
                    email=  binding.emailValue.text?.trim().toString(),
                    password=   binding.passwordValue.text?.trim().toString(),
                )
            )
        }
    }
    private fun singIn() {
        checkDataValidation {
            viewModel.singIn(it.email)
        }
    }

    private fun navigateToSingUp(){
        binding.goToRegister.setOnClickListener {
            findNavController().navigate(
                SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            )
        }
    }

}