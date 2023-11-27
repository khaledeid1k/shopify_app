package com.kh.mo.shopyapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kh.mo.shopyapp.repo.Repo
import com.kh.mo.shopyapp.ui.address.details.AddressDetailsViewModel
import com.kh.mo.shopyapp.ui.address.list.AddressViewModel
import com.kh.mo.shopyapp.ui.address.map.MapViewModel
import com.kh.mo.shopyapp.ui.cart.viewModel.CartViewModel
import com.kh.mo.shopyapp.ui.category.viewmodel.CategoryViewModel
import com.kh.mo.shopyapp.ui.favorite.viewmodel.FavoritesViewModel
import com.kh.mo.shopyapp.ui.home.viewmodel.HomeViewModel
import com.kh.mo.shopyapp.ui.order.viewmodel.OrderViewModel
import com.kh.mo.shopyapp.ui.orderdetails.viewmodel.OrderDetailsViewModel
import com.kh.mo.shopyapp.ui.product.product_Info.viewmodel.ProductInfoViewModel
import com.kh.mo.shopyapp.ui.product.product_details.viewmodel.ProductDetailsViewModel
import com.kh.mo.shopyapp.ui.product.product_reviews.viewmodel.ProductReviewsViewModel
import com.kh.mo.shopyapp.ui.product.viewmodel.ProductViewModel
import com.kh.mo.shopyapp.ui.profile.viewmodel.ProfileViewModel
import com.kh.mo.shopyapp.ui.sing_in.viewmodel.SignInViewModel
import com.kh.mo.shopyapp.ui.sing_up.viewmodel.SignUpViewModel

class BaseViewModelFactory(private val repo: Repo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            HomeViewModel::class.java -> HomeViewModel(repo) as T
            SignUpViewModel::class.java -> SignUpViewModel(repo) as T
            SignInViewModel::class.java -> SignInViewModel(repo) as T
            ProductViewModel::class.java -> ProductViewModel(repo) as T
            ProductInfoViewModel::class.java -> ProductInfoViewModel(repo) as T
            ProductDetailsViewModel::class.java -> ProductDetailsViewModel(repo) as T
            CategoryViewModel::class.java -> CategoryViewModel(repo) as T
            ProfileViewModel::class.java -> ProfileViewModel(repo) as T
            AddressViewModel::class.java -> AddressViewModel(repo) as T
            AddressDetailsViewModel::class.java -> AddressDetailsViewModel(repo) as T
            ProductReviewsViewModel::class.java -> ProductReviewsViewModel(repo) as T
            MapViewModel::class.java -> MapViewModel(repo) as T
            FavoritesViewModel::class.java -> FavoritesViewModel(repo) as T
            OrderViewModel::class.java -> OrderViewModel(repo) as T
            OrderDetailsViewModel::class.java -> OrderDetailsViewModel(repo) as T
            CartViewModel::class.java -> CartViewModel(repo) as T
            else -> throw Throwable("Unsupported view model")
        }
    }
}
