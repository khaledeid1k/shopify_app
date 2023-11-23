package com.kh.mo.shopyapp.ui.category.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.DialogFilterBinding
import com.kh.mo.shopyapp.databinding.FragmentCategoryBinding
import com.kh.mo.shopyapp.model.ui.allproducts.Products
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
    private var flag = true
    private  var productsList: List<Products> = emptyList()
    private lateinit var filterBinding: DialogFilterBinding
    private lateinit var filterDialog: Dialog


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
        initDialog()
        onClick()
    }

    private fun initDialog() {
        filterDialog = Dialog(requireContext())
        filterDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        filterBinding = DialogFilterBinding.inflate(layoutInflater)
        filterDialog.setContentView(filterBinding.root)
    }

    private fun onClick() {
        binding.chipGroup.setOnCheckedChangeListener { _, checkedId ->

            if (checkedId == R.id.first_subcategory && flag) {
                flag = false
                productType = binding.firstSubcategory.text.toString()
                viewModel.filterProductsBySubCollection(collectionId, productType)
            } else {
                flag = true
                getAllProducts(productsList)

            }

            if (checkedId == R.id.second_subcategory && flag) {
                flag = false
                productType = binding.secondSubcategory.text.toString()
                viewModel.filterProductsBySubCollection(collectionId, productType)
            } else {
                flag = true
                getAllProducts(productsList)

            }
            if (checkedId == R.id.third_subcategory && flag) {
                flag = false
                productType = binding.thirdSubcategory.text.toString()
                viewModel.filterProductsBySubCollection(collectionId, productType)
            } else {
                flag = true
                getAllProducts(productsList)

            }


        }
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val searchText = p0.toString().trim()
                val filteredList = productsList.filter { brand ->
                    brand.title.contains(searchText, ignoreCase = true)
                }
                subCategoriesAdapter = SubCategoriesAdapter(requireContext())
                subCategoriesAdapter.submitList(filteredList)
                binding.recyclerProductsCategory.adapter = subCategoriesAdapter
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        }
        )
        binding.filterByPrice.setOnClickListener {
            filterDialog.show()
        }
        filterBinding.btnApplyFilter.setOnClickListener {
            val filteredPrice = productsList.filter { products ->
                var minPrice = filterBinding.etFrom.text.toString()
                var maxPrice = filterBinding.etTo.text.toString()
                if (minPrice.isBlank() && maxPrice.isBlank()) {
                    minPrice = "0"
                    maxPrice = "10000"


                } else if (maxPrice.isBlank()) {
                    maxPrice = "10000"
                } else {
                    minPrice = "0"
                }
                products.variants[0].price!!.toDouble() in minPrice.toDouble()..maxPrice.toDouble()
            }
            if (filteredPrice.isEmpty()) {
                binding.lottiNoProduct.visibility = LottieAnimationView.VISIBLE
                getAllProducts(filteredPrice)
                filterDialog.dismiss()

            } else {
                binding.lottiNoProduct.visibility = LottieAnimationView.GONE
                getAllProducts(filteredPrice)
                filterDialog.dismiss()

            }


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
                        getAllProducts(it.data)
                        productsList = it.data

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
                        getAllProducts(it.data)


                    }
                }

            }

        }
    }

    private fun getAllProducts(list: List<Products>) {

        subCategoriesAdapter = SubCategoriesAdapter(requireContext())
        subCategoriesAdapter.submitList(list)
        binding.recyclerProductsCategory.adapter = subCategoriesAdapter
    }

}