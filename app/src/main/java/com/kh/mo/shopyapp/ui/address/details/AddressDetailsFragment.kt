package com.kh.mo.shopyapp.ui.address.details

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.Navigation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
            defaultAddressSwitch.isChecked = addressArgs.default == true
        }
        setupBtns()
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

    private fun setupBtns() {
        binding.apply {
            if (sourceArgs == "settings") {
                confirmBtn.text = resources.getString(R.string.update_address)
                negativeBtn.text = resources.getString(R.string.delete_address)
                disableConfirmBtn()
            } else {
                confirmBtn.text = resources.getString(R.string.add_address)
                negativeBtn.text = resources.getString(R.string.cancel)
                enableConfirmBtn()
            }
        }
        addClickListener()
    }

    private fun enableConfirmBtn() {
        binding.confirmBtn.apply {
            isEnabled = true
            backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.orange)
        }
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

            phoneNumberET.apply {
                if (this.text.isNullOrEmpty()) {
                    phoneNumberTxtF.error = "required"
                    disableConfirmBtn()
                }
                addTextChangedListener {
                    if (it.isNullOrEmpty()) {
                        phoneNumberTxtF.error = "required"
                        disableConfirmBtn()
                    } else {
                        phoneNumberTxtF.isErrorEnabled = false
                        updatedAddress.phone = it.toString()
                        addressStateFlow.value = updatedAddress
                    }
                }
            }

            streetNameET.addTextChangedListener {
                updatedAddress.address = it.toString()
                addressStateFlow.value = updatedAddress
            }

            markLocationET.addTextChangedListener {
                updatedAddress.markLocation = it.toString()
                addressStateFlow.value = updatedAddress
            }

            defaultAddressSwitch.setOnCheckedChangeListener { _, isChecked ->
                updatedAddress.default = isChecked
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
        if (sourceArgs == "settings") {
            binding.confirmBtn.setOnClickListener {
                addressStateFlow.value.let { address ->
                    Log.i(TAG, "addClickListener: updating address $address")
                    viewModel.updateAddress(
                        customerId = address.customerId,
                        addressId = address.id,
                        updatedAddress = address
                    )
                    addUpdateAddressStateListener()
                }
            }
            binding.negativeBtn.setOnClickListener {
                showDeleteAddressDialog()
            }

        } else {
            binding.apply {
                confirmBtn.setOnClickListener {
                    addressStateFlow.value.let {address ->
                        viewModel.addAddressToCustomer(address.customerId, address)
                        observeAddAddressState()
                    }
                }

                negativeBtn.setOnClickListener {
                    Navigation.findNavController(_view).navigateUp()
                }
            }
        }
    }

    private fun addUpdateAddressStateListener() {
        collectLatestFlowOnLifecycle(viewModel.updateAddressState) { apiState ->
            when (apiState) {
                is ApiState.Failure -> {
                    Log.i(TAG, "addUpdateAddressStateListener: failure: ${apiState.msg}")
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.something_went_wrong_please_try_again_later),
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
                    Log.i(TAG, "addUpdateAddressStateListener: success ${apiState.data}")
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

    private fun addDeleteAddressStateListener() {
        collectLatestFlowOnLifecycle(viewModel.deleteAddressState) { apiState ->
            when (apiState) {
                is ApiState.Failure -> {
                    Log.i(TAG, "addDeleteAddressStateListener: failure: ${apiState.msg}")
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
                        "address deleted successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    Navigation.findNavController(_view).navigateUp()
                }
            }
        }
    }

    private fun showDeleteAddressDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.delete_address))
            .setMessage(getString(R.string.delete_address_confirm))
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.confirm)) { dialog, _ ->
                handleDeleteAction()
                dialog.dismiss()
            }
            .show()
    }

    private fun handleDeleteAction() {
        addressStateFlow.value.let {address ->
            viewModel.deleteAddress(
                customerId = address.customerId,
                addressId = address.id
            )
            addDeleteAddressStateListener()
        }
    }

    private fun observeAddAddressState() {
        collectLatestFlowOnLifecycle(viewModel.addAddressState) {state ->
            when(state) {
                is ApiState.Failure -> {
                    Log.i(TAG, "observeAddAddressState: failure: ${state.msg}")
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
                        "address added successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    Navigation.findNavController(_view).navigateUp()
                }
            }
        }
    }
}