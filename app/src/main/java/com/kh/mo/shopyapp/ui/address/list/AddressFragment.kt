package com.kh.mo.shopyapp.ui.address.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentAddressBinding
import com.kh.mo.shopyapp.local.LocalSourceImp
import com.kh.mo.shopyapp.model.ui.Address
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.remote.RemoteSourceImp
import com.kh.mo.shopyapp.repo.RepoImp
import com.kh.mo.shopyapp.ui.base.BaseViewModelFactory
import com.kh.mo.shopyapp.ui.checkout.view.CheckoutFragmentDirections
import com.kh.mo.shopyapp.ui.settings.SettingsFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddressFragment : BottomSheetDialogFragment() {
    private val TAG = "TAG AddressFragment"
    private lateinit var addressViewModel: AddressViewModel
    private lateinit var binding: FragmentAddressBinding
    private lateinit var addressAdapter: AddressAdapter
    val  userAddress: MutableStateFlow<Address?> =
        MutableStateFlow(null)

    var mView: View? = null
    var source: String? = null
    private val listener: (Address) -> Unit = { address ->
        mView?.let { _view ->
            if (source == "settings") {
                val action =
                    SettingsFragmentDirections.actionSettingsFragmentToAddressDetailsFragment(
                        address,
                        "settings"
                    )
                Navigation.findNavController(_view).navigate(action)
                this.dismiss()
            } else {
                userAddress.value = address
            }
        } ?: Toast.makeText(
            requireContext(),
            getString(R.string.something_went_wrong_please_try_again_later),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(TAG, "onCreateView: ")
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_address,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        observeAddressResponse()
        binding.openMapBtn.setOnClickListener {
            openMap()
        }
        binding.loginBtn.setOnClickListener {
            openMap()
        }
    }

    private fun init() {
        val viewModelFactory =
            BaseViewModelFactory(
                RepoImp.getRepoImpInstance(
                    RemoteSourceImp.getRemoteSourceImpInstance(),
                    LocalSourceImp.getLocalSourceImpInstance(requireContext())

                )
            )
        addressViewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[AddressViewModel::class.java]
        addressAdapter = AddressAdapter(requireContext(), listener)
    }

    private fun observeAddressResponse() {
        lifecycleScope.launch(Dispatchers.Main) {
            addressViewModel.userAddresses.collectLatest { addressResponse ->
                when (addressResponse) {
                    is ApiState.Failure -> {
                        Log.i(
                            TAG,
                            "getAddresses: failures: ${addressResponse.msg}"
                        )
                        binding.apply {
                            progressBar.visibility = View.GONE
                            noAddressesCard.visibility = View.VISIBLE
                        }
                    }

                    is ApiState.Loading -> {
                        Log.i(TAG, "getAddresses: loading")
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is ApiState.Success -> {
                        Log.i(
                            TAG,
                            "getAddresses: success: ${addressResponse.data}"
                        )
                        showAddressList(addressResponse.data)
                    }
                }
            }
        }
    }

    private fun showAddressList(addressList: List<Address>) {
        binding.apply {
            progressBar.visibility = View.GONE
            noAddressesCard.visibility = View.GONE
            savedAddressRecyclerV.visibility = View.VISIBLE
            savedAddressRecyclerV.adapter = addressAdapter
            addressAdapter.submitList(addressList)
            openMapBtn.visibility = View.VISIBLE
        }
    }

    private fun openMap() {
        mView?.let { _view ->
            val action = if (source == "settings") {
                SettingsFragmentDirections.actionSettingsFragmentToMapFragment(addressViewModel.getCustomerID())
            } else {
                CheckoutFragmentDirections.actionCheckoutFragmentToMapFragment(addressViewModel.getCustomerID())
            }
            Navigation.findNavController(_view)
                .navigate(action)
            this.dismiss()
        } ?: Toast.makeText(
            requireContext(),
            getString(R.string.something_went_wrong_please_try_again_later),
            Toast.LENGTH_SHORT
        ).show()
    }
}