package com.kh.mo.shopyapp.ui.profile.view

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentProfileBinding
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.profile.viewmodel.ProfileViewModel


class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {
    override val layoutIdFragment = R.layout.fragment_profile

    override fun getViewModelClass() = ProfileViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginBtn.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToSignInFragment("profile")
            Navigation.findNavController(view).navigate(action)
        }
        binding.settingsBtn.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_profileFragment_to_settingsFragment)
        }
        observeUserData()
    }

    private fun observeUserData() {
        collectLatestFlowOnLifecycle(viewModel.userData) { userData ->
            userData?.let {
                showLoggedInViews()
                setUserData(userData)
            } ?: showNotLoggedInViews()
        }
    }

    private fun showLoggedInViews() {
        binding.apply {
            notLoggedInCard.visibility = View.GONE
            settingsBtn.visibility = View.VISIBLE
            ordersCard.visibility = View.VISIBLE
            wishListCard.visibility = View.VISIBLE
        }
    }

    private fun setUserData(data: UserData) {
        binding.apply {
            userNameTV.text = data.userName
            emailTV.text = data.email
        }
    }

    private fun showNotLoggedInViews() {
        binding.apply {
            notLoggedInCard.visibility = View.VISIBLE
            settingsBtn.visibility = View.GONE
            ordersCard.visibility = View.GONE
            wishListCard.visibility = View.GONE
        }
    }
}