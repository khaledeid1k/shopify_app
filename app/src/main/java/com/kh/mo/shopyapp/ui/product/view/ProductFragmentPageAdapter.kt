package com.kh.mo.shopyapp.ui.product.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kh.mo.shopyapp.ui.product.product_Info.view.ProductInfoFragment
import com.kh.mo.shopyapp.ui.product.product_details.view.DetailsFragment
import com.kh.mo.shopyapp.ui.product.product_reviews.view.ReviewsFragment

class ProductFragmentPageAdapter(
    fragment: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragment, lifecycle) {
    override fun getItemCount()=3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProductInfoFragment()
            1 -> ReviewsFragment()
            else -> DetailsFragment()
        }
    }
}