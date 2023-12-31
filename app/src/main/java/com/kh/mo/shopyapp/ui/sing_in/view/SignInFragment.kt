package com.kh.mo.shopyapp.ui.sing_in.view

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.kh.mo.shopyapp.MainActivity
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentSinginBinding
import com.kh.mo.shopyapp.model.entity.Validation
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.sing_in.viewmodel.SignInViewModel
import com.kh.mo.shopyapp.utils.getText
import com.kh.mo.shopyapp.utils.printError
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class SignInFragment : BaseFragment<FragmentSinginBinding, SignInViewModel>() {

    private val progressDialog: ProgressDialog by lazy {
        ProgressDialog(requireActivity())
    }
    override val layoutIdFragment = R.layout.fragment_singin

    override fun getViewModelClass() = SignInViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLoginWithApi()
        observeLoginWithFireBase()
        observeGetDraftFavoriteId()
        observeCheckEmailVerification()
        checkEmailValueValidation()
        checkPasswordValidation()
        singInCustomer()
        navigateToSingUp()
        checkIsUserLogin()
        loginAsGust()
    }

   private fun checkIsUserLogin(){
        if(viewModel.checkIsUserLogin()){
            viewModel.checkEmailVerification()

        }
    }


    private fun observeLoginWithApi() {
        lifecycleScope.launch {
            viewModel.singIn.collect {
                when (it) {
                    is ApiState.Failure -> {
                        printError(requireContext(),progressDialog,it.msg)
                    }
                    ApiState.Loading -> {}
                    is ApiState.Success -> {
                        viewModel.singInWithFireBase(
                            UserData(
                                id=it.data.id,
                                email = it.data.email,
                                password = binding.passwordValue.text.toString()
                            )
                        )
                    }
                }
            }
        }
    }
    private fun observeLoginWithFireBase() {
        lifecycleScope.launch {
            viewModel.checkCustomerExists.collect {
                when (it) {
                    is ApiState.Failure -> { printError(requireContext(),progressDialog,it.msg)}
                    ApiState.Loading -> {}
                    is ApiState.Success -> {
                        viewModel.getDraftFavoriteId(it.data)
                    }
                }
            }
        }
    }
    private fun observeGetDraftFavoriteId(){
        lifecycleScope.launch {
            viewModel.draftIds.collect {
                when (it) {
                    is ApiState.Failure -> {  printError(requireContext(),progressDialog,it.msg) }
                    is ApiState.Loading -> { }
                    is ApiState.Success -> {
                        it.data.let { draftIds ->
                            Log.d("TAG", "observeGetDraftFavoriteId: ${it.data}")
                            viewModel.saveFavoriteDraftId(draftIds[0].toLong())
                            viewModel.saveCartDraftId(draftIds[1].toLong())
                            viewModel.checkEmailVerification()

                    }
                }
            }
        }
        }
    }

    private fun observeCheckEmailVerification() {
        lifecycleScope.launch {
            viewModel.checkEmailVerification.collectLatest {
                when(it){
                    is ApiState.Failure -> {
                       printError(requireContext(),progressDialog,it.msg)
                           }
                    is ApiState.Loading -> {}
                    is ApiState.Success -> {
                        progressDialog.dismiss()
                        navigateToHomeScreen()
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
                    email = binding.emailValue.text?.trim().toString(),
                    password = binding.passwordValue.text?.trim().toString(),
                )
            )
        }
    }

    private fun singInCustomer() {
        checkDataValidation {
            progressDialog.setMessage(getString(R.string.loading_login))
            progressDialog.show()
            viewModel.singInCustomer(it.email)

        }
    }

    private fun navigateToSingUp() {
        binding.goToRegister.setOnClickListener {
            findNavController().navigate(
                SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            )
        }
    }
    private fun navigateToHomeScreen(){
        checkIsCustomerLogin()
        findNavController().navigate(
            SignInFragmentDirections.actionSignInFragmentToHomeFragment()
        )

    }

    private fun loginAsGust(){
        binding.loginAsGust.setOnClickListener {
            navigateToHomeScreen()
        }
    }

  private  fun checkIsCustomerLogin(){
        (requireContext() as MainActivity).checkIsLogin(checkCustomerId())
    }
    private fun checkCustomerId() = viewModel.getCustomerId() != 0L

}