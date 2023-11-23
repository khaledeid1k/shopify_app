package com.kh.mo.shopyapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kh.mo.shopyapp.repo.Repo
import com.kh.mo.shopyapp.ui.address.AddressViewModel
import com.kh.mo.shopyapp.ui.category.viewmodel.CategoryViewModel
import com.kh.mo.shopyapp.ui.home.viewmodel.HomeViewModel
import com.kh.mo.shopyapp.ui.product.product_Info.viewmodel.ProductInfoViewModel
import com.kh.mo.shopyapp.ui.product.product_details.viewmodel.ProductDetailsViewModel
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
            else -> throw Throwable("Unsupported view model")
        }
    }
}
