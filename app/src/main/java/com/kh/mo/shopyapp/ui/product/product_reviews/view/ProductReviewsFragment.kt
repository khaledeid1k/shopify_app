package com.kh.mo.shopyapp.ui.product.product_reviews.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentReviewsBinding
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.product.product_reviews.viewmodel.ProductReviewsViewModel


class ProductReviewsFragment : BaseFragment<FragmentReviewsBinding,ProductReviewsViewModel>() {

    lateinit var adapter:ReviewsAdapter
    override val layoutIdFragment=R.layout.fragment_reviews
    override fun getViewModelClass()=ProductReviewsViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intiReviewsAdapter()
        binding.apply {
            lifecycleOwner=this@ProductReviewsFragment
            reviewsAdapter=adapter
        }
        setReviewsList()

    }
    private fun setReviewsList(){
        adapter.setItems( viewModel.reviews())
    }
    private fun intiReviewsAdapter(){
        adapter=ReviewsAdapter()
    }


}