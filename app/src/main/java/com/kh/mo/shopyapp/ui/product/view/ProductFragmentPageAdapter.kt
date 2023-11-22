package com.kh.mo.shopyapp.ui.product.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kh.mo.shopyapp.model.ui.allproducts.Products
import com.kh.mo.shopyapp.ui.product.product_Info.view.ProductInfoFragment
import com.kh.mo.shopyapp.ui.product.product_details.view.ProductDetailsFragment
import com.kh.mo.shopyapp.ui.product.product_reviews.view.ProductReviewsFragment

class ProductFragmentPageAdapter(
    private val  receiveProduct: Products,
    fragment: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragment, lifecycle) {
    override fun getItemCount()=3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProductInfoFragment.newInstance(receiveProduct)
            1 -> ProductDetailsFragment.newInstance(receiveProduct)
            else -> ProductReviewsFragment()
        }
    }
}