package com.kh.mo.shopyapp.ui.category.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentCategoryBinding
import com.kh.mo.shopyapp.local.LocalSourceImp
import com.kh.mo.shopyapp.remote.RemoteSourceImp
import com.kh.mo.shopyapp.repo.RepoImp
import com.kh.mo.shopyapp.ui.base.BaseViewModelFactory
import com.kh.mo.shopyapp.ui.category.viewmodel.CategoryViewModel
import kotlinx.coroutines.launch


class CategoryFragment : Fragment() {
    private val TAG = "TAG CategoryFragment"
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var subCategoriesAdapter: SubCategoriesAdapter
    private var categoryName = ""
    private var collectionId: Long = 0L

    private lateinit var binding: FragmentCategoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_category,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryName = CategoryFragmentArgs.fromBundle(requireArguments()).nameOfMainCategory
        collectionId = CategoryFragmentArgs.fromBundle(requireArguments()).collectionId


        Log.i(TAG, collectionId.toString())
        Log.i(TAG, categoryName)

        intiViewModel()
        categoryViewModel.getCollectionProducts(collectionId)
        binding.tvCategoryName.text = categoryName
        getSubCategories()
        getCollectionProducts()

    }

    private fun getSubCategories() {
        lifecycleScope.launch {
            categoryViewModel.products.collect {
                val data = it.toData()?.distinctBy { it.productType }
                binding.firstSubcategory.setText(data?.get(0)?.productType.toString())
                binding.secondSubcategory.setText(data?.get(1)?.productType.toString())
                binding.thirdSubcategory.setText(data?.get(2)?.productType.toString())

            }

        }
    }

    private fun getCollectionProducts() {
        lifecycleScope.launch {
            categoryViewModel.productsCollection.collect {
                subCategoriesAdapter = SubCategoriesAdapter(requireContext())

                subCategoriesAdapter.submitList(it.toData())
                binding.recyclerProductsCategory.adapter = subCategoriesAdapter
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
        categoryViewModel = ViewModelProvider(
            this,
            showProductsViewModelFactory
        )[CategoryViewModel::class.java]
    }


}