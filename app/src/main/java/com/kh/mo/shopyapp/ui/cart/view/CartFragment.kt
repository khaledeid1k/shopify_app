package com.kh.mo.shopyapp.ui.cart.view

import android.os.Bundle
import android.util.Log
import android.view.View
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
            }

            Constants.ACTION_ADD -> {
                add(item)
            }

            Constants.ACTION_SUB -> {
                sub(item)
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
                    binding.apply {
                        lottiNoProduct.visibility = View.VISIBLE
                        lottiNoProduct.playAnimation()
                        cartRecyclerV.visibility = View.GONE
                        cartTotalTxtV.visibility = View.GONE
                        cartShippingTitleTxtV.visibility = View.GONE
                        binding.cartTotalPriceTxtV.visibility = View.GONE
                        cartCheckoutBtn.visibility = View.GONE
                    }

                }

                ApiState.Loading -> {
                    Log.i(TAG, "observeProductListState: loading...")
                    binding.apply {
                        cartRecyclerV.visibility = View.GONE
                        cartTotalTxtV.visibility = View.GONE
                        cartShippingTitleTxtV.visibility = View.GONE
                        cartTotalPriceTxtV.visibility = View.GONE
                        cartCheckoutBtn.visibility = View.GONE
                        loading.visibility = View.VISIBLE
                        loading.playAnimation()
                    }
                }

                is ApiState.Success -> {
                    Log.i(TAG, "observeProductListState: success ${state.data}")
                    if (state.data.isEmpty()) {
                        binding.apply {
                            lottiNoProduct.visibility = View.VISIBLE
                            lottiNoProduct.playAnimation()
                            cartRecyclerV.visibility = View.GONE
                            cartTotalTxtV.visibility = View.GONE
                            cartShippingTitleTxtV.visibility = View.GONE
                            cartTotalPriceTxtV.visibility = View.GONE
                            cartCheckoutBtn.visibility = View.GONE
                        }
                    } else {
                        cartAdapter.submitList(state.data)
                        calculateTotal(state.data)
                        binding.apply {
                            lottiNoProduct.visibility = View.GONE
                            lottiNoProduct.pauseAnimation()
                            loading.visibility = View.GONE
                            loading.pauseAnimation()
                            cartRecyclerV.visibility = View.VISIBLE
                            cartTotalTxtV.visibility = View.VISIBLE
                            cartShippingTitleTxtV.visibility = View.VISIBLE
                            cartTotalPriceTxtV.visibility = View.VISIBLE
                            cartCheckoutBtn.visibility = View.VISIBLE
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
        if (item.quantity?.minus(1)!! <= 0) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.minimum_one_item))
                .setMessage(getString(R.string.minimum_one_item_message))
                .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton(resources.getString(R.string.delete)) { dialog, _ ->
                    delete(item)
                    dialog.dismiss()
                }
                .show()
        } else {
            viewModel.subOneFromItem(item)
        }
    }
}