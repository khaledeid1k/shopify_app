package com.kh.mo.shopyapp.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentSettingsBinding
import com.kh.mo.shopyapp.model.ui.CurrencySettingModel
import com.kh.mo.shopyapp.model.ui.SettingsModel
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.model.ui.SupportedCurrencies
import com.kh.mo.shopyapp.ui.address.list.AddressFragment
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.profile.viewmodel.ProfileViewModel
import com.kh.mo.shopyapp.ui.sing_in.view.SignInFragmentDirections
import com.kh.mo.shopyapp.utils.createDialog
import com.kh.mo.shopyapp.utils.makeGone
import com.kh.mo.shopyapp.utils.makeVisible
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest
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
            4->showCurrencies()
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
                              binding.loading.makeGone()
                        binding.loadingOverlay.makeGone()
                    }
                    is ApiState.Loading -> {
                        binding.loading.makeVisible()
                        binding.loadingOverlay.makeVisible()
                    }
                    is ApiState.Success -> {
                        binding.loading.makeGone()
                        binding.loadingOverlay.makeGone()
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

    private fun observerSyncData() {
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
            SettingsModel(resources.getString(R.string.currency), R.drawable.ic_currency),
            SettingsModel(resources.getString(R.string.language), R.drawable.ic_language)
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

    private fun showCurrencies() {
        viewModel.getCurrencyPreference()
        val dialog = SettingsBottomSheet(getSupportedCurrencyList()) {
            Toast.makeText(
                requireContext(),
                getString(R.string.change_currency_to) + it.title, Toast.LENGTH_SHORT
            ).show()
            viewModel.updateCurrencyPreference(it.key.value)
        }
        dialog.show(requireActivity().supportFragmentManager, "74")
        observerCurrencyUnitOnDialog(dialog)
    }

    private fun getSupportedCurrencyList(): List<CurrencySettingModel> {
        return listOf(
            CurrencySettingModel(resources.getString(R.string.egp_currency), SupportedCurrencies.EGP),
            CurrencySettingModel(resources.getString(R.string.usd_currency), SupportedCurrencies.USD),
            CurrencySettingModel(resources.getString(R.string.eur_currency), SupportedCurrencies.EUR),
            CurrencySettingModel(resources.getString(R.string.gbp_currency), SupportedCurrencies.GBP)
        )
    }

    private fun observerCurrencyUnitOnDialog(dialog: SettingsBottomSheet) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currencyPreference.collectLatest { currencyUnit ->
                    Log.i(TAG, "currency unit set to: $currencyUnit")
                    val list = getSupportedCurrencyList().map {
                        if (it.key.value == currencyUnit)
                            it.copy(isThePreference = true)
                        else
                            it.copy(isThePreference = false)
                    }
                    Log.i(TAG, "observerCurrencyUnitOnDialog: $list")
                    dialog.currencyUnitList = list
                    dialog.isListUpdated.value = true
                }
            }
        }
    }
}
