package com.kh.mo.shopyapp.ui.product.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentProductBinding
import com.kh.mo.shopyapp.model.ui.allproducts.Product
import com.kh.mo.shopyapp.remote.ApiState
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.category.view.CategoryFragmentDirections
import com.kh.mo.shopyapp.ui.product.viewmodel.ProductViewModel
import com.kh.mo.shopyapp.utils.createDialog
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ProductFragment : BaseFragment<FragmentProductBinding, ProductViewModel>() {
    override val layoutIdFragment = R.layout.fragment_product
    override fun getViewModelClass() = ProductViewModel::class.java
    private lateinit var receiveProduct: Product
    private var isFavorite = false
    var job: Job? = null
    private fun receiveProduct() = ProductFragmentArgs.fromBundle(requireArguments()).productDetails
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        receiveProduct = receiveProduct()
        binding.apply {
            lifecycleOwner = this@ProductFragment
            product = receiveProduct

            addToCart.setOnClickListener {
                addProductToCart()
            }
        }

    }


    override fun onResume() {
        super.onResume()
        checkProductInFavorite(receiveProduct.id)
        observeIsFavorite()
        checkProductInFavorite()
        Log.d("TAG", "onViewCreated:${receiveProduct.isFavorite} ")
        initProductPageAdapter(receiveProduct)
        tightIndicatorToViewPagerOfImages()

    }

    private fun tightIndicatorToViewPagerOfImages() {
        binding.apply {
            productImagesViewPager.adapter = ProductImagesAdapter(receiveProduct().productImages)
            dotsIndicator.setViewPager2(productImagesViewPager)
        }

    }

    private fun checkProductInFavorite(productId: Long) =
        viewModel.checkProductInFavorite(productId)

    private fun initProductPageAdapter(receiveProduct: Product) {
        val productFragmentPageAdapter = ProductFragmentPageAdapter(
            receiveProduct,
            requireActivity().supportFragmentManager, lifecycle
        )
        tightIndicatorToViewPagerOfFragments(productFragmentPageAdapter)

    }

    private fun tightIndicatorToViewPagerOfFragments(productFragmentPageAdapter: ProductFragmentPageAdapter) {
        binding.homeFragmentViewPager.adapter = productFragmentPageAdapter
        TabLayoutMediator(
            binding.homeFragmentTabs,
            binding.homeFragmentViewPager
        ) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.product)
                1 -> tab.text = getString(R.string.details)
                2 -> tab.text = getString(R.string.reviews)
            }

        }.attach()
    }

    private fun checkProductInFavorite() {
        binding.addToFavorite.setOnClickListener {
            if (!viewModel.checkCustomerId()) {
                createDialog(context = requireContext(),
                    title = getString(R.string.please_login),
                    message = getString(R.string.gust_message),
                    sure = { navigateToSignInFragment() }, cancel = {})
            }else{
                isFavorite = !isFavorite
                if (isFavorite) {
                    binding.addToFavorite.text = requireContext().getString(R.string.favorite)
                } else {
                    binding.addToFavorite.text = requireContext().getString(R.string.add_to_favorite)
                }
            }


        }

    }

    private fun navigateToSignInFragment() {
        findNavController().navigate(
            ProductFragmentDirections
                .actionProductFragmentToSignInFragment()
        )

    }

    private fun observeIsFavorite() {
        job = lifecycleScope.launch {
            viewModel.productState.collect {
                Log.d("TAG", "observeIsFavorite:${it} ")
                isFavorite = it
                binding.addToFavorite.text = if (it) {
                    requireContext().getString(R.string.favorite)
                } else {
                    requireContext().getString(R.string.add_to_favorite)
                }
            }

        }
    }

    private fun addProductToCart() {
        viewModel.addToCart(receiveProduct)
        observeAddToCartState()
    }

    private fun observeAddToCartState() {
        collectLatestFlowOnLifecycle(viewModel.addToCartState) {
            when(it) {
                is ApiState.Failure -> {

                }
                ApiState.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                    binding.loading.playAnimation()
                }
                is ApiState.Success -> {
                    if (it.data) {
                        binding.addToCart.apply {
                            binding.loading.visibility = View.GONE
                            binding.loading.pauseAnimation()
                            text = "cart"
                            isEnabled = false
                            backgroundTintList =
                                ContextCompat.getColorStateList(requireContext(), R.color.dark_gray)
                        }
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        job?.cancel()
        if (isFavorite) {
            viewModel.saveFavorite(receiveProduct)
        } else {
            viewModel.deleteFavorite(receiveProduct.id)
        }
    }


}



