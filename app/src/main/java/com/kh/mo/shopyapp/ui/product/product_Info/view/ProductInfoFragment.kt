package com.kh.mo.shopyapp.ui.product.product_Info.view

import android.os.Bundle
import android.util.Log
import android.view.View
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentProductInfoBinding
import com.kh.mo.shopyapp.model.ui.productsofbrand.Product
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.product.product_Info.viewmodel.ProductInfoViewModel
import com.kh.mo.shopyapp.utils.Constants


class ProductInfoFragment :BaseFragment<FragmentProductInfoBinding,ProductInfoViewModel>(){
    override val layoutIdFragment=R.layout.fragment_product_info
    override fun getViewModelClass()=ProductInfoViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sada()
    }

    fun sada(){

        val product = arguments?.getParcelable<Product>(Constants.ProductInfoFragment)
        Log.d("TAG", "sada: $product")
        binding.listSize.adapter=ProductSizeAdapter(product?.options?.get(0)?.values)

    }

    companion object {
        @JvmStatic
        fun newInstance(product: Product) = ProductInfoFragment().apply {
            arguments = Bundle().apply {
                putParcelable(Constants.ProductInfoFragment, product)
            }
        }
    }


}