package com.kh.mo.shopyapp.ui.product.product_details.view

import android.os.Bundle
import android.view.View
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentDetailsBinding
import com.kh.mo.shopyapp.model.ui.allproducts.Product
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.product.product_details.viewmodel.ProductDetailsViewModel
import com.kh.mo.shopyapp.utils.Constants


class ProductDetailsFragment : BaseFragment<FragmentDetailsBinding, ProductDetailsViewModel>() {

    override val layoutIdFragment = R.layout.fragment_details

    override fun getViewModelClass() = ProductDetailsViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        receiveProduct()
    }

    fun receiveProduct() {
        val product = arguments?.getParcelable<Product>(Constants.ProductDetailsFragment)
        binding.apply {
            with(product) {
                brandValue.text = this?.vendor ?: ""
                weightUnitValue.text = this?.variants?.get(0)?.weightUnit ?: ""
                categoryValue.text = this?.productType ?: ""
                statusValue.text = this?.status
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(product: Product) = ProductDetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(Constants.ProductDetailsFragment, product)
            }
        }
    }
}