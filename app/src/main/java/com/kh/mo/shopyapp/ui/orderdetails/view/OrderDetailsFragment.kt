package com.kh.mo.shopyapp.ui.orderdetails.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentOrderDetailsBinding
import com.kh.mo.shopyapp.model.ui.allproducts.Product
import com.kh.mo.shopyapp.model.ui.orderdetails.LineItem
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.category.view.CategoryFragmentDirections
import com.kh.mo.shopyapp.ui.orderdetails.viewmodel.OrderDetailsViewModel
import kotlinx.coroutines.launch


class OrderDetailsFragment : BaseFragment<FragmentOrderDetailsBinding, OrderDetailsViewModel>() {
    private val TAG = "TAG OrderDetailsFragment"
    override val layoutIdFragment = R.layout.fragment_order_details
    override fun getViewModelClass() = OrderDetailsViewModel::class.java
    private lateinit var orderDetailsAdapter: OrderDetailsAdapter
    private var orderId: Long = 0L
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderId = OrderDetailsFragmentArgs.fromBundle(requireArguments()).orderId
        viewModel.getOrdersById(orderId)
        getOrderDetails()
        getInfoOfClient()
    }


    private fun getOrderDetails() {
        lifecycleScope.launch {
            viewModel.orderList.collect {
                when (it) {
                    is ApiState.Failure -> {
                        Log.i(TAG, "Fail")
                    }
                    is ApiState.Loading -> {
                        Log.i(TAG, "Loading")
                    }
                    is ApiState.Success -> {
                        val data = it.data
                        orderDetailsAdapter = OrderDetailsAdapter(requireContext())
                        orderDetailsAdapter.submitList(data)
                        binding.recycleOrderDetails.adapter = orderDetailsAdapter

                    }
                }
            }

        }
    }
    private fun getInfoOfClient() {
        lifecycleScope.launch {
            viewModel.orders.collect {
                when (it) {
                    is ApiState.Failure -> {
                        Log.i(TAG, "Fail")
                    }
                    is ApiState.Loading -> {
                        Log.i(TAG, "Loading")
                    }
                    is ApiState.Success -> {
                        val data = it.data
                        // Log.i(TAG,data.toString())
                        binding.apply {
                            tvNameClient.text = data.get(0).customer?.firstName
                            tvAddress.text = data.get(0).customer?.defaultAddress?.address1
                            Log.i(TAG, "address" + data.get(0).customer?.defaultAddress)
                            tvPhoneNumber.text = data.get(0).customer?.defaultAddress?.phone
                            //tvPaymentMethodValue.text=data.get(0).paymentGatewayNames?.get(0).toString()
                            tvSubTotalValue.text = data.get(0).subtotalPrice + data.get(0).currency
                            tvTotalValue.text = data.get(0).totalPrice + data.get(0).currency
                            tvServiceFeeValue.text = data.get(0).totalDiscounts
                        }
                    }


                }
            }
        }

    }


}
