package com.kh.mo.shopyapp.ui.cart.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
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
                Toast.makeText(requireContext(), "delete", Toast.LENGTH_SHORT).show()
            }

            Constants.ACTION_ADD -> {
                Toast.makeText(requireContext(), "add", Toast.LENGTH_SHORT).show()
            }

            Constants.ACTION_SUB -> {
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
            viewModel.getDraftCartId()
            observeCartDraftIdState()
        }
    }

    private fun observeCartDraftIdState() {
        collectLatestFlowOnLifecycle(viewModel.draftCartId) { state ->
            when (state) {
                is ApiState.Failure -> {
                    Log.i(TAG, "observeCartDraftIdState: failure ${state.msg}")
                }

                ApiState.Loading -> {
                    Log.i(TAG, "observeCartDraftIdState: loading..")
                }

                is ApiState.Success -> {
                    Log.i(TAG, "observeCartDraftIdState: success ${state.data}")
                    viewModel.getAllProductsInCart(state.data)
                    observeProductListState()
                }
            }
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
                    cartAdapter.submitList(state.data)
                    calculateTotal(state.data)
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
}