package com.kh.mo.shopyapp.ui.orderdetails.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentOrderDetailsBinding
import com.kh.mo.shopyapp.model.ui.order.LineItem
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.orderdetails.viewmodel.OrderDetailsViewModel
import kotlinx.coroutines.launch


class OrderDetailsFragment : BaseFragment<FragmentOrderDetailsBinding, OrderDetailsViewModel>() {
    private val TAG = "TAG OrderDetailsFragment"
    override val layoutIdFragment = R.layout.fragment_order_details
    override fun getViewModelClass() = OrderDetailsViewModel::class.java
    private lateinit var orderDetailsAdapter: OrderDetailsAdapter
    private lateinit var  data: List<LineItem>

    private var orderId: Long = 0L
    private var uri: Uri? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderId = OrderDetailsFragmentArgs.fromBundle(requireArguments()).orderId
        Log.i(TAG, orderId.toString())
        viewModel.getOrdersById(orderId)
        getOrderDetails()
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
                        data = it.data
                        Log.i(TAG, "product id" + it.data.get(0).productId!!)
                        viewModel.getImageOrder(it.data.get(0).productId!!)
                        getImage()
                        Log.i(TAG, data.toString())
                        Log.i(TAG, uri.toString())



                    }
                }
            }

        }
    }

    fun getImage() {
        lifecycleScope.launch {
            viewModel.imageOrders.collect {
                when (it) {
                    is ApiState.Failure -> {
                        Log.i(TAG, "Image Fail")
                    }
                    is ApiState.Loading -> {
                        Log.i(TAG, "Image Loading")
                    }
                    is ApiState.Success -> {
                        val data1 = it.data
                        uri = data1.get(0).src.toUri()
                        Log.i(TAG, "uri $uri")
                        orderDetailsAdapter = OrderDetailsAdapter(requireContext(), uri!!)
                        orderDetailsAdapter.submitList(data)
                        binding.recycleOrderDetails.adapter = orderDetailsAdapter
                    }
                }
            }

        }
    }
}