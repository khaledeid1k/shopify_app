package com.kh.mo.shopyapp.ui.cart.view

import android.os.Bundle
import android.util.Log
import android.view.View
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentCartBinding
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.cart.viewModel.CartViewModel

class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel>() {
    private val TAG = "TAG CartFragment"
    override val layoutIdFragment = R.layout.fragment_cart

    override fun getViewModelClass() = CartViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

                }
            }
        }
    }
}