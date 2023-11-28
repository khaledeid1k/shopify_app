package com.kh.mo.shopyapp.ui.category.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.kh.mo.shopyapp.MainActivity
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.DialogFilterBinding
import com.kh.mo.shopyapp.databinding.FragmentCategoryBinding
import com.kh.mo.shopyapp.model.ui.allproducts.Product
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.category.viewmodel.CategoryViewModel
import com.kh.mo.shopyapp.utils.createDialog
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CategoryFragment : BaseFragment<FragmentCategoryBinding, CategoryViewModel>() {
    private val TAG = "TAG CategoryFragment"
    override val layoutIdFragment = R.layout.fragment_category
    override fun getViewModelClass() = CategoryViewModel::class.java
    private lateinit var productsCategoryAdapter: ProductsCategoryAdapter
    private var categoryName = ""
    private var productType = ""
    private var collectionId: Long = 0L
    private var flag = true
    private  var productList: List<Product> = emptyList()
    private lateinit var filterBinding: DialogFilterBinding
    private lateinit var filterDialog: Dialog
    var  job: Job?=null
    private var cartListener: (Product) -> Unit = {
        viewModel.addProductToCart(it)
        observeAddToCartState()

        if (viewModel.checkIsUserLogin()) {
            viewModel.addProductToCart(it)
            observeAddToCartState()
        } else {
            (requireActivity() as MainActivity).showRequestLoginDialog()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeCheckUserState()
        categoryName = CategoryFragmentArgs.fromBundle(requireArguments()).nameOfMainCategory
        collectionId = CategoryFragmentArgs.fromBundle(requireArguments()).collectionId




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
                addAdapterToCategories(productList)

            }

            if (checkedId == R.id.second_subcategory && flag) {
                flag = false
                productType = binding.secondSubcategory.text.toString()
                viewModel.filterProductsBySubCollection(collectionId, productType)
            } else {
                flag = true
                addAdapterToCategories(productList)

            }
            if (checkedId == R.id.third_subcategory && flag) {
                flag = false
                productType = binding.thirdSubcategory.text.toString()
                viewModel.filterProductsBySubCollection(collectionId, productType)
            } else {
                flag = true
                addAdapterToCategories(productList)

            }


        }
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val searchText = p0.toString().trim()
                val filteredList = productList.filter { brand ->
                    brand.title.contains(searchText, ignoreCase = true)
                }
                addAdapterToCategories(filteredList)
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        }
        )
        binding.filterByPrice.setOnClickListener {
            filterDialog.show()
        }
        filterBinding.btnApplyFilter.setOnClickListener {
            val filteredPrice = productList.filter { products ->
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
                products.productVariants[0].price!!.toDouble() in minPrice.toDouble()..maxPrice.toDouble()
            }
            if (filteredPrice.isEmpty()) {
                binding.lottiNoProduct.visibility = LottieAnimationView.VISIBLE
                addAdapterToCategories(filteredPrice)
                filterDialog.dismiss()

            } else {
                binding.lottiNoProduct.visibility = LottieAnimationView.GONE
                addAdapterToCategories(filteredPrice)
                filterDialog.dismiss()

            }


        }

    }


    private fun getSubCategories() {
        lifecycleScope.launch {
            viewModel.product.collect { apiState ->
                when (apiState) {
                    is ApiState.Failure -> {}
                    is ApiState.Loading -> {
                        binding.loading.visibility=View.VISIBLE
                    }
                    is ApiState.Success -> {
                        binding.loading.visibility=View.GONE
                        Log.d(TAG, "getSubCategories: ${apiState.data}")
                        val data = apiState.data.distinctBy { it.productType }
                        binding.firstSubcategory.text = data[0].productType
                        binding.secondSubcategory.text = data[1].productType
                        binding.thirdSubcategory.text = data[2].productType
                    }
                }
            }
        }
    }

    private fun getCollectionProducts() {
        lifecycleScope.launch {
            viewModel.productCollection.collect {
                when (it) {
                    is ApiState.Failure -> {}
                    is ApiState.Loading -> {}
                    is ApiState.Success -> {
                        addAdapterToCategories(it.data)
                        productList = it.data
                        Log.d(TAG, "getCollectionProducts: ${it.data}")

                    }
                }

            }

        }
    }

    private fun filterProductsBySubCollection() {
        lifecycleScope.launch {
            viewModel.filterProductCollection.collect {
                when (it) {
                    is ApiState.Failure -> {}
                    is ApiState.Loading -> {}
                    is ApiState.Success -> {
                        Log.d(TAG, "filterProductsBySubCollection: ${it.data}")
                        addAdapterToCategories(it.data)


                    }
                }

            }

        }
    }
    private fun observeCheckUserState() {
        Log.d(TAG, "3onClickFavouriteIcon: ")

        job=   lifecycleScope.launch {
       viewModel.userState.collectLatest{
            Log.d(TAG, "4onClickFavouriteIcon: ")
            createDialog(context = requireContext(),
                title=getString(R.string.please_login),
                message = getString(R.string.gust_message),
                sure = {navigateToSignInFragment()}, cancel = {})
        }

        }
    }
  private  fun navigateToSignInFragment(){
        findNavController().navigate(CategoryFragmentDirections
            .actionCategoryFragmentToSignInFragment())

    }

    private fun navigateToProductScreen(product: Product){
        findNavController().navigate(    CategoryFragmentDirections.actionCategoryFragmentToProductFragment(product))
    }

    fun addAdapterToCategories(products: List<Product>){
        productsCategoryAdapter = ProductsCategoryAdapter(viewModel.checkCustomerId(),viewModel, cartListener){
            navigateToProductScreen(products[it])
        }
        productsCategoryAdapter.setItems(products)
        binding.recyclerProductsCategory.adapter = productsCategoryAdapter
    }

    private fun observeAddToCartState() {
        collectLatestFlowOnLifecycle(viewModel.addToCartState) {
            when(it) {
                is ApiState.Failure -> {
                    Log.i(TAG, "observeAddToCartState: failed: ${it.msg}")
                    Toast.makeText(requireContext(), "failed", Toast.LENGTH_SHORT).show()
                }
                is ApiState.Loading -> {
                    Toast.makeText(requireContext(), "loading..", Toast.LENGTH_SHORT).show()}
                is ApiState.Success -> {
                    Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()}
            }
        }
    }

    override fun onPause() {
        super.onPause()
        job?.cancel()
    }
}