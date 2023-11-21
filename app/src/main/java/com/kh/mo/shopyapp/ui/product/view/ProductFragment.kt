package com.kh.mo.shopyapp.ui.product.view

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentProductBinding
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.product.viewmodel.ProductViewModel


class ProductFragment : BaseFragment<FragmentProductBinding, ProductViewModel>() {
    override val layoutIdFragment = R.layout.fragment_product
    override fun getViewModelClass() = ProductViewModel::class.java


    private fun receiveProduct() = ProductFragmentArgs.fromBundle(requireArguments()).productDetails


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val receiveProduct = receiveProduct()
        initProductPageAdapter()
        tightIndicatorToViewPager()
        binding.apply {
            lifecycleOwner = this@ProductFragment
            product=receiveProduct
        }

    }

   private fun tightIndicatorToViewPager(){
       binding.apply {
           productImagesViewPager.adapter = ProductImagesAdapter(receiveProduct().images)
           dotsIndicator.setViewPager2(productImagesViewPager)
       }

    }


    private fun initProductPageAdapter(){
        val productFragmentPageAdapter = ProductFragmentPageAdapter(
            requireActivity().supportFragmentManager, lifecycle
        )

        binding.homeFragmentViewPager.adapter = productFragmentPageAdapter
        TabLayoutMediator(
            binding.homeFragmentTabs,
            binding.homeFragmentViewPager
        ) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.product)
                1 -> tab.text = getString(R.string.details)
                2 -> tab.text = getString(R.string.reviews)
            }

        }.attach()

    }



}



