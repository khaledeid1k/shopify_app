package com.kh.mo.shopyapp.ui.home.view.brand

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentBrandProductsBinding
import com.kh.mo.shopyapp.local.LocalSourceImp
import com.kh.mo.shopyapp.remote.RemoteSourceImp
import com.kh.mo.shopyapp.repo.RepoImp
import com.kh.mo.shopyapp.ui.base.BaseViewModelFactory
import com.kh.mo.shopyapp.ui.home.viewmodel.HomeViewModel
import kotlinx.coroutines.launch


class BrandProductsFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var brandName: String = ""
    private var brandImage: String = ""
    private lateinit var binding: FragmentBrandProductsBinding
    private lateinit var productsBrandsAdapter: ProductsOfBrandsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBrandProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intiViewModel()
        brandName = BrandProductsFragmentArgs.fromBundle(requireArguments()).brandName
        brandImage = BrandProductsFragmentArgs.fromBundle(requireArguments()).brandImage
        Glide.with(requireContext())
            .load(brandImage)
            .placeholder(R.drawable.placeholder_products)
            .into(binding.imgBrandItem)
        homeViewModel.getProductsOfSpecificBrand(brandName)
        getProductsOfBrand()
    }

    private fun getProductsOfBrand() {
        lifecycleScope.launch {
            homeViewModel.productsBrand.collect {
                productsBrandsAdapter = ProductsOfBrandsAdapter(requireContext())
                productsBrandsAdapter.submitList(it.toData())

                Log.i("HomeFragment", it.toData()?.get(0)?.images?.get(0)?.src.toString())
                binding.recycleProductsSpecificBrand.adapter = productsBrandsAdapter
            }

        }
    }

    private fun intiViewModel() {
        val showProductsViewModelFactory =
            BaseViewModelFactory(
                RepoImp.getRepoImpInstance
                    (
                    RemoteSourceImp.getRemoteSourceImpInstance(),
                    LocalSourceImp.getLocalSourceImpInstance()
                )
            )
        homeViewModel = ViewModelProvider(
            this,
            showProductsViewModelFactory
        )[HomeViewModel::class.java]
    }

}