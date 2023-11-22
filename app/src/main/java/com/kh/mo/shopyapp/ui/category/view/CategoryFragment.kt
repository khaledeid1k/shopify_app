package com.kh.mo.shopyapp.ui.category.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentCategoryBinding
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.category.viewmodel.CategoryViewModel
import kotlinx.coroutines.launch


class CategoryFragment : BaseFragment<FragmentCategoryBinding, CategoryViewModel>() {
    private val TAG = "TAG CategoryFragment"
    override val layoutIdFragment = R.layout.fragment_category
    override fun getViewModelClass() = CategoryViewModel::class.java
    private lateinit var subCategoriesAdapter: SubCategoriesAdapter
    private var categoryName = ""
    private var productType = ""
    private var collectionId: Long = 0L


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryName = CategoryFragmentArgs.fromBundle(requireArguments()).nameOfMainCategory
        collectionId = CategoryFragmentArgs.fromBundle(requireArguments()).collectionId


        Log.i(TAG, collectionId.toString())
        Log.i(TAG, categoryName)

        viewModel.getCollectionProducts(collectionId)
        viewModel.filterProductsBySubCollection(collectionId, productType)
        binding.tvCategoryName.text = categoryName
        getSubCategories()
        getCollectionProducts()
        filterProductsBySubCollection()
        onClick()
    }

    private fun onClick() {
        binding.firstSubcategory.setOnClickListener {
            productType = binding.firstSubcategory.text.toString()
            viewModel.filterProductsBySubCollection(collectionId, productType)
        }
        binding.secondSubcategory.setOnClickListener {
            productType = binding.secondSubcategory.text.toString()
            viewModel.filterProductsBySubCollection(collectionId, productType)
        }
        binding.thirdSubcategory.setOnClickListener {
            productType = binding.thirdSubcategory.text.toString()
            viewModel.filterProductsBySubCollection(collectionId, productType)
        }
    }

    private fun getSubCategories() {
        lifecycleScope.launch {
            viewModel.products.collect {
                when (it) {
                    is ApiState.Failure -> {}
                    is ApiState.Loading -> {}
                    is ApiState.Success -> {
                        val data = it.data.distinctBy { it.productType }
                        binding.firstSubcategory.setText(data.get(0).productType)
                        binding.secondSubcategory.setText(data.get(1).productType)
                        binding.thirdSubcategory.setText(data.get(2).productType)
                    }

                }


            }

        }
    }

    private fun getCollectionProducts() {
        lifecycleScope.launch {
            viewModel.productsCollection.collect {
                when (it) {
                    is ApiState.Failure -> {}
                    is ApiState.Loading -> {}
                    is ApiState.Success -> {
                        subCategoriesAdapter = SubCategoriesAdapter(requireContext())

                        subCategoriesAdapter.submitList(it.data)
                        binding.recyclerProductsCategory.adapter = subCategoriesAdapter
                    }


                }

            }

        }
    }

    private fun filterProductsBySubCollection() {
        lifecycleScope.launch {
            viewModel.filterProductsCollection.collect {
                when (it) {
                    is ApiState.Failure -> {}
                    is ApiState.Loading -> {}
                    is ApiState.Success -> {
                        subCategoriesAdapter = SubCategoriesAdapter(requireContext())
                        subCategoriesAdapter.submitList(it.data)
                        binding.recyclerProductsCategory.adapter = subCategoriesAdapter
                    }
                }

            }

        }
    }

}