package com.kh.mo.shopyapp.ui.cart.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentCartBinding
import com.kh.mo.shopyapp.model.ui.Cart
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.cart.viewModel.CartViewModel
import com.kh.mo.shopyapp.utils.Constants
import com.kh.mo.shopyapp.utils.roundTwoDecimals
import kotlinx.coroutines.flow.asFlow

class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel>() {
    private val TAG = "TAG CartFragment"
    override val layoutIdFragment = R.layout.fragment_cart
    private lateinit var cartAdapter: CartAdapter
    private val listener: (Cart, String) -> Unit = { item, action ->
        when (action) {
            Constants.ACTION_DELETE -> {
                delete(item)
                //Toast.makeText(requireContext(), "delete", Toast.LENGTH_SHORT).show()
            }

            Constants.ACTION_ADD -> {
                add(item)
                Toast.makeText(requireContext(), "add", Toast.LENGTH_SHORT).show()
            }

            Constants.ACTION_SUB -> {
                sub(item)
                Toast.makeText(requireContext(), "sub", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getViewModelClass() = CartViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartAdapter = CartAdapter(requireContext(), listener)
        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.divider_drawable, null)
            ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }
        binding.cartRecyclerV.addItemDecoration(dividerItemDecoration)
        binding.cartRecyclerV.adapter = cartAdapter

        if (viewModel.checkIsUserLogin()) {
            viewModel.getAllProductsInCart()
            observeProductListState()
        }
        binding.cartCheckoutBtn.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_cartFragment_to_checkoutFragment)
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
                        binding.cartRecyclerV.visibility = View.GONE
                    } else {
                        cartAdapter.submitList(state.data)
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
            cartTotalTxtV.visibility = View.VISIBLE
            cartShippingTitleTxtV.visibility = View.VISIBLE
            cartTotalPriceTxtV.text = totalPrice.roundTwoDecimals().toString() + currency
        }
    }

    private fun delete(item: Cart) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.delete_address))
            .setMessage(getString(R.string.delete_item_confirm))
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.confirm)) { dialog, _ ->
                viewModel.deleteItem(item)
                dialog.dismiss()
            }
            .show()
    }

    private fun add(item: Cart) {
        viewModel.addOneToItem(item)
    }

    private fun sub(item: Cart) {
        viewModel.subOneFromItem(item)
    }
}