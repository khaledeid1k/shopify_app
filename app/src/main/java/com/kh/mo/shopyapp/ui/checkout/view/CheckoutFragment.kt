package com.kh.mo.shopyapp.ui.checkout.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.kh.mo.shopyapp.MainActivity
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentCheckoutBinding
import com.kh.mo.shopyapp.model.ui.Address
import com.kh.mo.shopyapp.model.ui.Cart
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.ui.address.list.AddressFragment
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.checkout.viewmodel.CheckoutViewModel
import com.kh.mo.shopyapp.utils.roundTwoDecimals
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectLatest

class CheckoutFragment : BaseFragment<FragmentCheckoutBinding, CheckoutViewModel>() {
    private val TAG = "TAG CheckoutFragment"
    private lateinit var adapter: CheckoutAdapter
    private lateinit var _view: View
    lateinit var userAddress: MutableStateFlow<ApiState<List<Address>>>
    override val layoutIdFragment = R.layout.fragment_checkout

    override fun getViewModelClass() = CheckoutViewModel::class.java


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _view = view
        init()
        addListeners()
    }

    private fun init() {
        adapter = CheckoutAdapter(requireContext())
        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.divider_drawable, null)
            ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }
        binding.productsRecyclerV.addItemDecoration(dividerItemDecoration)
        binding.productsRecyclerV.adapter = adapter
        viewModel.getAllProductsInCart()
        viewModel.getAddress()
        userAddress = viewModel.userAddress as MutableStateFlow<ApiState<List<Address>>>
        observeProductListState()
        observeAddressState()
    }

    private fun addListeners() {
        binding.addressEditTxt.setOnClickListener {
            showAddressesDialog()
        }
        binding.cashOnDeliverySwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.paymentCardV.isEnabled = false
                binding.paymentCardNumberTxt.visibility = View.GONE
            } else {
                binding.paymentCardV.isEnabled = true
                binding.paymentCardNumberTxt.visibility = View.VISIBLE
            }
        }
        binding.paymentCardV.setOnClickListener {
            //(requireActivity() as MainActivity).startPaymentActivity()
        }
    }

    private fun observeProductListState() {
        collectLatestFlowOnLifecycle(viewModel.productList) { state ->
            when (state) {
                is ApiState.Failure -> {
                    binding.apply {
                        loading.visibility = View.GONE
                        loading.pauseAnimation()
                        lottiNoProduct.visibility = View.VISIBLE
                        lottiNoProduct.playAnimation()
                        constraintLayout.visibility = View.GONE
                        checkoutTotalTxtV.visibility = View.GONE
                        checkoutTotalPriceTxtV.visibility = View.GONE
                        confirmBtn.visibility = View.GONE
                    }
                }

                ApiState.Loading -> {
                    binding.apply {
                        loading.visibility = View.VISIBLE
                        loading.playAnimation()
                        constraintLayout.visibility = View.GONE
                        checkoutTotalTxtV.visibility = View.GONE
                        checkoutTotalPriceTxtV.visibility = View.GONE
                        confirmBtn.visibility = View.GONE
                    }
                }

                is ApiState.Success -> {
                    Log.i(TAG, "observeProductListState: success ${state.data}")
                    if (state.data.isEmpty()) {
                        binding.apply {
                            loading.visibility = View.GONE
                            loading.pauseAnimation()
                            lottiNoProduct.visibility = View.VISIBLE
                            lottiNoProduct.playAnimation()
                            constraintLayout.visibility = View.GONE
                            checkoutTotalTxtV.visibility = View.GONE
                            checkoutTotalPriceTxtV.visibility = View.GONE
                            confirmBtn.visibility = View.GONE
                        }
                    } else {
                        adapter.submitList(state.data)
                        calculateTotal(state.data)
                        binding.apply {
                            loading.visibility = View.GONE
                            loading.pauseAnimation()
                            lottiNoProduct.visibility = View.GONE
                            lottiNoProduct.pauseAnimation()
                            constraintLayout.visibility = View.VISIBLE
                            checkoutTotalTxtV.visibility = View.VISIBLE
                            checkoutTotalPriceTxtV.visibility = View.VISIBLE
                            confirmBtn.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private suspend fun calculateTotal(cartList: List<Cart>) {
        val currency: String = cartList[0].price?.split(" ")?.get(1) ?: ""
        var totalPrice = 0.0
        cartList.asFlow().collect {
            val price = (it.price?.split(" ")?.get(0)?.toDouble()?.times(it.quantity!!)) ?: 0.0
            totalPrice += price
        }
        binding.apply {
            checkoutTotalPriceTxtV.visibility = View.VISIBLE
            checkoutTotalPriceTxtV.text = totalPrice.roundTwoDecimals().toString() + currency
        }
    }

    private fun observeAddressState() {
        collectLatestFlowOnLifecycle(userAddress) {
            when(it) {
                is ApiState.Failure -> {
                    Log.i(TAG, "observeAddressState: failed,${it.msg}")
                    binding.mapAddressImgV.visibility = View.VISIBLE
                    showNoAddress()
                }
                is ApiState.Loading -> {
                    binding.mapAddressImgV.visibility = View.INVISIBLE
                }
                is ApiState.Success -> {
                    Log.i(TAG, "observeAddressState: success,${it.data}")
                    binding.mapAddressImgV.visibility = View.VISIBLE
                    if (it.data.isEmpty()) {
                        showNoAddress()
                    } else {
                        showAddress(address = it.data[0])
                    }
                }
            }
        }
    }

    private fun showAddress(address: Address) {
        binding.apply {
            addressNameValueTxtV.text = address.name
            addressValueTxtV.text =
                "${address.city} ${address.state} ${address.country}, ${address.phone}, ${address.address}"
        }
    }

    private fun showNoAddress() {
        binding.addressValueTxtV.text =
            getString(R.string.no_address_found_let_s_add_you_shipping_address)
    }

    private fun showAddressesDialog() {
        val dialog = AddressFragment()
        dialog.show(requireActivity().supportFragmentManager, dialog.tag)
        dialog.mView = _view
        dialog.source = "checkout"
        collectLatestFlowOnLifecycle(dialog.userAddress) {
            it?.let { clickedAddress ->
                userAddress.value = ApiState.Success(listOf(clickedAddress))
                dialog.dismiss()
            }
        }
    }
}