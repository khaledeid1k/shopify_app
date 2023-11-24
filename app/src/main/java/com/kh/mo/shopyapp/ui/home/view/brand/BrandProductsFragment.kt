package com.kh.mo.shopyapp.ui.home.view.brand

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentBrandProductsBinding
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.home.viewmodel.HomeViewModel
import kotlinx.coroutines.launch


class BrandProductsFragment : BaseFragment<FragmentBrandProductsBinding,HomeViewModel> (){


    private var brandName: String = ""
    private var brandImage: String = ""
    override val layoutIdFragment = R.layout.fragment_brand_products
    override fun getViewModelClass() = HomeViewModel::class.java
    private lateinit var productsBrandsAdapter: ProductsOfBrandsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        brandName = BrandProductsFragmentArgs.fromBundle(requireArguments()).brandName
        brandImage = BrandProductsFragmentArgs.fromBundle(requireArguments()).brandImage
        Glide.with(requireContext())
            .load(brandImage)
            .placeholder(R.drawable.placeholder_products)
            .into(binding.imgBrandItem)
        viewModel.getProductsOfSpecificBrand(brandName)
        getProductsOfBrand()
    }

    private fun getProductsOfBrand() {
        lifecycleScope.launch {
            viewModel.productBrand.collect {
                when(it){
                    is ApiState.Failure ->{}
                    ApiState.Loading -> {}
                    is ApiState.Success -> {
                        val products = it.data
                        productsBrandsAdapter = ProductsOfBrandsAdapter(requireContext()){
                            position->
                            findNavController().navigate(
                                BrandProductsFragmentDirections.actionBrandProductsFragmentToProductFragment(
                                    products[position]
                                )
                            )
                        }
                        productsBrandsAdapter.submitList(products)

                        binding.recycleProductsSpecificBrand.adapter = productsBrandsAdapter
                    }
                }
            }
        }
    }



}