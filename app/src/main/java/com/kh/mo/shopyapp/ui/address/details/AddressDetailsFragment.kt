package com.kh.mo.shopyapp.ui.address.details

import android.os.Bundle
import android.view.View
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentAddressDetailsBinding
import com.kh.mo.shopyapp.ui.base.BaseFragment

class AddressDetailsFragment : BaseFragment<FragmentAddressDetailsBinding, AddressDetailsViewModel>() {
    override val layoutIdFragment = R.layout.fragment_address_details

    override fun getViewModelClass() = AddressDetailsViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addressArgs = AddressDetailsFragmentArgs.fromBundle(requireArguments()).address
        binding.apply {
            lifecycleOwner = this@AddressDetailsFragment
            address = addressArgs
        }
    }
}