package com.kh.mo.shopyapp.ui.product.product_reviews.viewmodel

import androidx.lifecycle.ViewModel
import com.kh.mo.shopyapp.model.ui.Review
import com.kh.mo.shopyapp.repo.Repo

class ProductReviewsViewModel(val repo: Repo):ViewModel() {
    fun reviews()=repo.reviews()
}