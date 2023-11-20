package com.kh.mo.shopyapp.ui.sing_up.view

import android.os.Bundle
import android.view.View
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentSignUpBinding
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.sing_up.viewmodel.SignUpViewModel


class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpViewModel>() {

    override val layoutIdFragment: Int=R.layout.fragment_sign_up
    override fun getViewModelClass() = SignUpViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.singUpB.setOnClickListener {
            viewModel.storeData("1569897106932", UserData("khaledEid@gmail.com","1234567"))

        }
    }


}