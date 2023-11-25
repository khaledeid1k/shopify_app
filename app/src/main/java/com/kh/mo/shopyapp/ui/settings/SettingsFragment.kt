package com.kh.mo.shopyapp.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentSettingsBinding
import com.kh.mo.shopyapp.model.ui.CurrencySettingModel
import com.kh.mo.shopyapp.model.ui.SettingsModel
import com.kh.mo.shopyapp.ui.address.list.AddressFragment
import com.kh.mo.shopyapp.ui.profile.view.SettingsBottomSheet
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
            1 -> showCurrencies()
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
            SettingsModel(resources.getString(R.string.currency), R.drawable.ic_currency)
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

    private fun showCurrencies() {
        viewModel.getCurrencyPreference()
        val dialog = SettingsBottomSheet(getSupportedCurrencyList()) {
            Toast.makeText(requireContext(), "change to $it", Toast.LENGTH_SHORT).show()
        }
        dialog.show(requireActivity().supportFragmentManager, "74")
    }

    private fun getSupportedCurrencyList(): List<CurrencySettingModel> {
        val preferenceUnit = viewModel.currencyPreference.value
        val currencyList = listOf(
            CurrencySettingModel(resources.getString(R.string.egp_currency), "EGP"),
            CurrencySettingModel(resources.getString(R.string.usd_currency), "USD"),
            CurrencySettingModel(resources.getString(R.string.eur_currency), "EUR"),
            CurrencySettingModel(resources.getString(R.string.gbp_currency), "GBP")
        )
        currencyList.forEach {
            it.isThePreference = (it.key) == preferenceUnit
        }
        return currencyList
    }
}
