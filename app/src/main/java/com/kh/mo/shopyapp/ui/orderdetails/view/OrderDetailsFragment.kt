package com.kh.mo.shopyapp.ui.orderdetails.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentOrderBinding
import com.kh.mo.shopyapp.databinding.FragmentOrderDetailsBinding
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.home.view.brand.BrandProductsFragmentArgs
import com.kh.mo.shopyapp.ui.order.view.OrderAdapter
import com.kh.mo.shopyapp.ui.order.view.OrderFragmentDirections
import com.kh.mo.shopyapp.ui.order.viewmodel.OrderViewModel
import com.kh.mo.shopyapp.ui.orderdetails.viewmodel.OrderDetailsViewModel
import kotlinx.coroutines.launch


class OrderDetailsFragment : BaseFragment<FragmentOrderDetailsBinding, OrderDetailsViewModel>() {
    private val TAG = "TAG OrderDetailsFragment"
    override val layoutIdFragment = R.layout.fragment_order_details
    override fun getViewModelClass() = OrderDetailsViewModel::class.java
    private lateinit var orderDetailsAdapter: OrderDetailsAdapter

    private var orderId:Long=0L
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderId = OrderDetailsFragmentArgs.fromBundle(requireArguments()).orderId
        Log.i(TAG,orderId.toString())
        viewModel.getOrdersById(orderId)
        getOrderDetails()
    }

    private fun getOrderDetails() {
        lifecycleScope.launch {
            viewModel.orderList.collect {
                when (it) {
                    is ApiState.Failure -> {
                        Log.i(TAG,"Fail")
                    }
                    is ApiState.Loading -> {
                        Log.i(TAG,"Loading")
                    }
                    is ApiState.Success -> {
                        val data = it.data


                        Log.i(TAG,data.toString())
                        orderDetailsAdapter = OrderDetailsAdapter(requireContext())
                        orderDetailsAdapter.submitList(data)
                        binding.recycleOrderDetails.adapter = orderDetailsAdapter

                    }
                }
            }

        }
    }
}