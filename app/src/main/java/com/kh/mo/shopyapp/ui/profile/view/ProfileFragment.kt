package com.kh.mo.shopyapp.ui.profile.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentProfileBinding
import com.kh.mo.shopyapp.model.request.UserData
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch


class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {
    private val TAG = "TAG ProfileFragment"

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
        binding.tvAllMyOrders.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(
                    R.id.action_profileFragment_to_orderFragment
                )
        }
        checkUserState()
        showSingleOrder()


    }

    private fun checkUserState() {
        if (viewModel.checkIsUserLogin()){
                showLoggedInViews()
                setUserData()
        } else {
            showNotLoggedInViews()
        }
    }

    private fun showLoggedInViews() {
        binding.apply {
            notLoggedInCard.visibility = View.GONE
            ordersCard.visibility = View.VISIBLE
            wishListCard.visibility = View.VISIBLE
            imageProfile.setImageResource(R.drawable.image_person)
        }
    }

    private fun setUserData() {
        binding.apply {
            userNameTV.text = viewModel.getCustomerUserName()
            emailTV.text = viewModel.getCustomerEmail()
        }
    }

    private fun showNotLoggedInViews() {
        binding.apply {
            notLoggedInCard.visibility = View.VISIBLE
            ordersCard.visibility = View.GONE
            wishListCard.visibility = View.GONE
            userNameTV.text = getString(R.string.guest)
            imageProfile.setImageResource(R.drawable.ic_profile)
        }
    }


    private fun showSingleOrder() {
        lifecycleScope.launch {
            viewModel.orders.collect {
                when (it) {
                    is ApiState.Failure -> {
                        Log.i(TAG, "Fail")
                    }
                    is ApiState.Loading -> {
                        Log.i(TAG, "Loading")
                    }
                    is ApiState.Success -> {
                        val data = it.data
                        if (!data.isEmpty()) {
                            binding.apply {
                                tvProductNameOrderProfile.text = data.get(0).id.toString()
                                tvProductSizeOrderProfile.text =
                                    data.get(0).lineItems?.get(0)?.quantity.toString() + "x"
                                tvProductPriceOrderProfile.text = data.get(0).subtotalPrice
                            }
                        }else{
                            binding.apply {
                                tvProductNameOrderProfile.text = "No orders yet"
                                tvProductSizeOrderProfile.visibility =View.GONE
                                tvProductPriceOrderProfile.visibility =View.GONE
                                tvAllMyOrders.isClickable=false
                            }
                        }


                    }


                }
            }
        }

    }

}
