package com.kh.mo.shopyapp.ui.checkout.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
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

        binding.addressEditTxt.setOnClickListener {
            showAddressesDialog()
        }
    }

    private fun observeProductListState() {
        collectLatestFlowOnLifecycle(viewModel.productList) { state ->
            when (state) {
                is ApiState.Failure -> {
                    Log.i(TAG, "observeProductListState: failure ${state.msg}")
                }

                ApiState.Loading -> {
                    Log.i(TAG, "observeProductListState: loading...")
                }

                is ApiState.Success -> {
                    Log.i(TAG, "observeProductListState: success ${state.data}")
                    if (state.data.isEmpty()) {
                        Toast.makeText(requireContext(), "no data in cart", Toast.LENGTH_SHORT)
                            .show()
                        binding.productsRecyclerV.visibility = View.GONE
                    } else {
                        adapter.submitList(state.data)
                        calculateTotal(state.data)
                    }
                }
            }
        }
    }

    private suspend fun calculateTotal(cartList: List<Cart>) {
        val currency: String = cartList[0].price?.split(" ")?.get(1) ?: ""
        var totalPrice = 0.0
        cartList.asFlow().collect {
            val price = it.price?.split(" ")?.get(0)?.toDouble() ?: 0.0
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
                }
                is ApiState.Loading -> {}
                is ApiState.Success -> {
                    Log.i(TAG, "observeAddressState: success,${it.data}")
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