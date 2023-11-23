package com.kh.mo.shopyapp.ui.address

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
    val userId: MutableStateFlow<Long?> = MutableStateFlow(null)
    var mView: View? = null
    private val listener: (Address) -> Unit = { address ->
        mView?.let{_view ->
            val action = SettingsFragmentDirections.actionSettingsFragmentToAddressDetailsFragment(address)
            Navigation.findNavController(_view).navigate(action)
            this.dismiss()
        }
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
        observerUserId()
        binding.openMapBtn.setOnClickListener {
            Toast.makeText(requireContext(), "openmap", Toast.LENGTH_SHORT).show()
        }

    }

    private fun init() {
        val viewModelFactory =
            BaseViewModelFactory(
                RepoImp.getRepoImpInstance(
                    RemoteSourceImp.getRemoteSourceImpInstance(),
                    LocalSourceImp.getLocalSourceImpInstance()

                )
            )
        addressViewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[AddressViewModel::class.java]
        addressAdapter = AddressAdapter(requireContext(), listener)
    }

    private fun observerUserId() {
        lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userId.collectLatest { id ->
                    id?.let {
                        addressViewModel.getAddresses(id)
                        observeAddressResponse()
                    }
                }
            }
        }
    }

    private suspend fun observeAddressResponse() {
        withContext(Dispatchers.Main) {
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
}