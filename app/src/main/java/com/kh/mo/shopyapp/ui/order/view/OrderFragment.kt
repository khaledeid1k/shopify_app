package com.kh.mo.shopyapp.ui.order.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentCategoryBinding
import com.kh.mo.shopyapp.databinding.FragmentOrderBinding
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.category.view.SubCategoriesAdapter
import com.kh.mo.shopyapp.ui.category.viewmodel.CategoryViewModel
import com.kh.mo.shopyapp.ui.order.viewmodel.OrderViewModel
import kotlinx.coroutines.launch


class OrderFragment : BaseFragment<FragmentOrderBinding, OrderViewModel>() {

    private val TAG = "TAG OrderFragment"
    override val layoutIdFragment = R.layout.fragment_order
    override fun getViewModelClass() = OrderViewModel::class.java
    private lateinit var orderAdapter: OrderAdapter
    private var customerID:Long=7590081495324L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG,"OnView")
        viewModel.getOrders(customerID)
        getAllOrders()



    }

    private fun getAllOrders() {
        lifecycleScope.launch {
            viewModel.orders.collect {
                when (it) {
                    is ApiState.Failure -> {
                        Log.i(TAG,"Fail")
                    }
                    is ApiState.Loading -> {
                        Log.i(TAG,"Loading")

                    }
                    is ApiState.Success -> {
                        val data = it.data
                        orderAdapter = OrderAdapter(requireContext())
                        orderAdapter.submitList(data)
                        binding.recyclerOrders.adapter = orderAdapter
                        Log.i(TAG,data.toString())

                    }
                }
            }

        }

    }
}