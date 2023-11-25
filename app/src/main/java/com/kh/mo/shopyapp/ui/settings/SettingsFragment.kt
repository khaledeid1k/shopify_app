package com.kh.mo.shopyapp.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentSettingsBinding
import com.kh.mo.shopyapp.model.ui.SettingsModel
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.ui.address.list.AddressFragment
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.profile.viewmodel.ProfileViewModel
import com.kh.mo.shopyapp.ui.sing_in.view.SignInFragmentDirections
import com.kh.mo.shopyapp.utils.createDialog
import com.kh.mo.shopyapp.utils.makeGone
import com.kh.mo.shopyapp.utils.makeVisible
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SettingsFragment : BaseFragment<FragmentSettingsBinding, ProfileViewModel>() {
    private val TAG = "TAG SettingsFragment"
    private lateinit var _view: View
    override val layoutIdFragment = R.layout.fragment_settings
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _view = view
        setSettingsList()
    }

    override fun getViewModelClass() = ProfileViewModel::class.java

    private val listener: (Int) -> Unit = { position ->
        when (position) {
            0 -> showAddresses()
            1 -> sync()
            2 -> upload()
            3 -> logOut()
        }
    }

    private fun logOut() {
        createDialog(context = requireContext(),
            title = getString(R.string.warning),
            message = getString(R.string.warning_meesage),

            sure = {  viewModel.logOut()
                navigateToLoginScreen()}, cancel = {})

    }


    private fun upload() {
        viewModel.backUpDraftFavorite()
        observerUploadData()
    }

    private fun observerUploadData() {
        lifecycleScope.launch {
            viewModel.backUpDraftFavorite.collect {
                when (it) {
                    is ApiState.Failure -> {
                        //      binding.loading.makeGone()
                    }
                    is ApiState.Loading -> {
                        binding.loading.makeVisible()
                    }
                    is ApiState.Success -> {
                        binding.loading.makeGone()
                        Toast.makeText(requireContext(), "Upload Done", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    private fun sync() {
        viewModel.retrieveDraftFavorite()
        observerSyncData()
    }

    fun observerSyncData() {
        lifecycleScope.launch {
            viewModel.retrieveDraftFavorite.collect {
                when (it) {
                    is ApiState.Failure -> {}
                    ApiState.Loading -> {binding.loading.makeVisible()}
                    is ApiState.Success -> {
                        viewModel.saveProducts(it.data) { result ->
                            if (result > 0) {
                                binding.loading.makeGone()
                                Toast.makeText(requireContext(), "Sync Done", Toast.LENGTH_SHORT).show()

                            }
                        }
                    }
                }
            }

        }
    }


    private fun setSettingsList() {
        val settingList = listOf(
            SettingsModel(resources.getString(R.string.address), R.drawable.ic_location),
            SettingsModel(resources.getString(R.string.sync), R.drawable.sync),
            SettingsModel(resources.getString(R.string.upload), R.drawable.upload),
            SettingsModel(resources.getString(R.string.log_out), R.drawable.logout),
        )
        val settingAdapter = SettingAdapter(settingList, listener)
        binding.settingRecyclerV.adapter = settingAdapter
    }

    private fun showAddresses() {
        val dialog = AddressFragment()
        dialog.show(requireActivity().supportFragmentManager, dialog.tag)
        dialog.userId.value = viewModel.userData.value?.id
        dialog.mView = _view
    }


    private fun navigateToLoginScreen() {
        findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToSignInFragment())
    }

}
