package com.kh.mo.shopyapp.ui.category.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentCategoryBinding
import com.kh.mo.shopyapp.home.view.BrandsAdapter
import com.kh.mo.shopyapp.remote.RemoteSourceImp
import com.kh.mo.shopyapp.repo.RepoImp
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.base.BaseViewModelFactory
import com.kh.mo.shopyapp.ui.category.viewmodel.CategoryViewModel
import com.kh.mo.shopyapp.ui.home.view.HomeFragmentDirections
import com.kh.mo.shopyapp.ui.home.view.brand.BrandProductsFragmentArgs
import com.kh.mo.shopyapp.ui.home.viewmodel.HomeViewModel
import kotlinx.coroutines.launch


class CategoryFragment : BaseFragment() {
    private val TAG = "TAG CategoryFragment"
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var subCategoriesAdapter:SubCategoriesAdapter
    private var categoryName=""
    private lateinit var binding:FragmentCategoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCategoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryName = CategoryFragmentArgs.fromBundle(requireArguments()).nameOfMainCategory

        intiViewModel()
        binding.tvCategoryName.text=categoryName
        getSubCategories()

    }
    private fun getSubCategories(){
        lifecycleScope.launch {
            categoryViewModel.products.collect {
                val data=it.toData()?.distinctBy { it.productType }
                binding.firstSubcategory.setText(data?.get(0)?.productType.toString())
                binding.secondSubcategory.setText(data?.get(1)?.productType.toString())
                binding.thirdSubcategory.setText(data?.get(2)?.productType.toString())

//                subCategoriesAdapter= SubCategoriesAdapter(requireContext())
//
//                subCategoriesAdapter.submitList(it.toData()?.distinctBy { it.productType })
//                binding.recyclerSubCategory.adapter = subCategoriesAdapter
            }

        }
    }

    private fun intiViewModel() {
        val showProductsViewModelFactory =
            BaseViewModelFactory(
                RepoImp.getRepoImpInstance
                    (
                    RemoteSourceImp.getRemoteSourceImpInstance()
                )
            )
        categoryViewModel = ViewModelProvider(
            this,
            showProductsViewModelFactory
        )[CategoryViewModel::class.java]
    }



}