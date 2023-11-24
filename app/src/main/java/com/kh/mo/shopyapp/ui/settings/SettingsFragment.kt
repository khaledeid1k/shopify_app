package com.kh.mo.shopyapp.ui.settings

import android.os.Bundle
import android.view.View
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentSettingsBinding
import com.kh.mo.shopyapp.model.ui.SettingsModel
import com.kh.mo.shopyapp.ui.address.list.AddressFragment
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.profile.viewmodel.ProfileViewModel

class SettingsFragment : BaseFragment<FragmentSettingsBinding, ProfileViewModel>() {
    private val TAG = "TAG SettingsFragment"
    private lateinit var _view: View
    override val layoutIdFragment = R.layout.fragment_settings

    override fun getViewModelClass() = ProfileViewModel::class.java

    private val listener: (Int) -> Unit = { position ->
        when (position) {
            0 -> showAddresses()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _view = view
        setSettingsList()
    }

    private fun setSettingsList() {
        val settingList = listOf(
            SettingsModel(resources.getString(R.string.address), R.drawable.ic_location),
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

}
