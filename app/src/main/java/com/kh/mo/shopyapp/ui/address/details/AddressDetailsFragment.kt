package com.kh.mo.shopyapp.ui.address.details

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.Navigation
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentAddressDetailsBinding
import com.kh.mo.shopyapp.model.ui.Address
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.ui.base.BaseFragment
import kotlinx.coroutines.flow.MutableStateFlow

class AddressDetailsFragment :
    BaseFragment<FragmentAddressDetailsBinding, AddressDetailsViewModel>() {
    private val TAG = "TAG AddressDetailsFragment"
    private lateinit var _view: View
    private lateinit var addressArgs: Address
    private lateinit var sourceArgs: String
    private lateinit var addressStateFlow: MutableStateFlow<Address>
    private lateinit var updatedAddress: Address
    override val layoutIdFragment = R.layout.fragment_address_details

    override fun getViewModelClass() = AddressDetailsViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _view = view
        getArgs()
        binding.apply {
            lifecycleOwner = this@AddressDetailsFragment
            address = addressArgs
        }
        setupConfirmBtn()
        addTextWatchers()
        addAddressStateListener()
    }

    private fun getArgs() {
        AddressDetailsFragmentArgs.fromBundle(requireArguments()).apply {
            addressArgs = address
            sourceArgs = source
        }
        addressStateFlow = MutableStateFlow(addressArgs)
        updatedAddress = addressArgs.copy()
    }

    private fun setupConfirmBtn() {
        binding.confirmBtn.apply {
            if (sourceArgs == "settings") {
                text = resources.getString(R.string.update_address)
                disableConfirmBtn()
            } else {
                text = resources.getString(R.string.add_address)
                enableConfirmBtn()
            }
        }
    }

    private fun enableConfirmBtn() {
        binding.confirmBtn.apply {
            isEnabled = true
            backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.orange)
        }
        addClickListener()
    }

    private fun disableConfirmBtn() {
        binding.confirmBtn.apply {
            isEnabled = false
            backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.dark_gray)
        }
    }
    private fun addTextWatchers() {
        binding.apply {
            countryET.addTextChangedListener {
                updatedAddress.country = it.toString()
                addressStateFlow.value = updatedAddress
            }

            stateET.addTextChangedListener {
                updatedAddress.state = it.toString()
                addressStateFlow.value = updatedAddress
            }

            cityET.addTextChangedListener {
                updatedAddress.city = it.toString()
                addressStateFlow.value = updatedAddress
            }

            addressTagET.addTextChangedListener {
                updatedAddress.name = it.toString()
                addressStateFlow.value = updatedAddress
            }

            phoneNumberET.addTextChangedListener {
                updatedAddress.phone = it.toString()
                addressStateFlow.value = updatedAddress
            }

            streetNameET.addTextChangedListener {
                updatedAddress.address = it.toString()
                addressStateFlow.value = updatedAddress
            }

            markLocationET.addTextChangedListener {
                updatedAddress.markLocation = it.toString()
                addressStateFlow.value = updatedAddress
            }
        }
    }

    private fun addAddressStateListener() {
        collectLatestFlowOnLifecycle(addressStateFlow) {
            if (it != addressArgs) {
                enableConfirmBtn()
            }
        }
    }

    private fun addClickListener() {
        binding.confirmBtn.setOnClickListener {
            if (sourceArgs == "settings") {
                addressStateFlow.value.let { address ->
                    Log.i(TAG, "addClickListener: updating address $address")
                    viewModel.updateAddress(
                        customerId = address.customerId,
                        addressId = address.id,
                        updatedAddress = address
                    )
                    addUpdateAddressStateListener()
                }

            } else {

            }
        }
    }

    private fun addUpdateAddressStateListener() {
        collectLatestFlowOnLifecycle(viewModel.updateAddressState) {apiState ->
            when(apiState) {
                is ApiState.Failure -> {
                    Log.i(TAG, "addUpdateAddressStateListener: failure: ${apiState.msg}")
                    Toast.makeText(
                        requireContext(),
                        "something went wrong please try again later",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBar.visibility = View.GONE
                    enableConfirmBtn()
                }
                is ApiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    disableConfirmBtn()
                }
                is ApiState.Success -> {
                    Toast.makeText(
                        requireContext(),
                        "address updated successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    Navigation.findNavController(_view).navigateUp()
                }
            }
        }
    }
}