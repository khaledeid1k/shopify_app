package com.kh.mo.shopyapp.ui.home.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentHomeBinding
import com.kh.mo.shopyapp.home.view.BrandsAdapter
import com.kh.mo.shopyapp.model.ui.AdModel
import com.kh.mo.shopyapp.remote.RemoteSourceImp
import com.kh.mo.shopyapp.repo.RepoImp
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.base.BaseViewModelFactory
import com.kh.mo.shopyapp.ui.home.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {

    private val TAG="HomeFragment"
    private lateinit var homeViewModel:HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var brandsAdapter: BrandsAdapter
    private lateinit var mainCategoriesAdapter: MainCategoryAdapter
    private lateinit var adsAdapter: AdsAdapter
    private val listener: (AdModel) -> Unit = {
        homeViewModel.getCoupon(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intiViewModel()
        getBrands()
        getMainCategories()


        val adsList = listOf(
            AdModel("https://drive.google.com/uc?export=download&id=1nmCztDLfwa7Kkw7-yz8tJEvqUi6oKA4M", "1402684080412", "17996919505180"),
            AdModel("https://drive.google.com/uc?export=download&id=1kJqcbsprXzbROM3zepzryPi7i0dQ5fah", "1402721403164", "18006657990940"),
            AdModel("https://drive.google.com/uc?export=download&id=1CUw0NsjOuTcMfktIzhpSMlhhApf9-QQ3", "1402721534236", "18006657466652")
        )
        adsAdapter = AdsAdapter(requireContext(), adsList, listener)
        binding.recyclerCoupon.adapter = adsAdapter

        val totalPages = adsList.size
        lifecycleScope.launch(Dispatchers.Main) {
            while (isActive) {
                delay(3000)
                binding.recyclerCoupon.apply {
                    if (currentItem + 1 > totalPages - 1) {
                        currentItem = 0
                    } else {
                        currentItem++
                    }
                }
            }
        }
    }

    private fun getMainCategories() {
        lifecycleScope.launch {
            homeViewModel.mainCategories.collect {category->
                mainCategoriesAdapter= MainCategoryAdapter(requireContext()){
                  //  Log.i("sssss",category.toData()!!.get(category.toData()!!.lastIndexOf(it)).title)
                    val title=category.toData()?.get(category.toData()!!.lastIndexOf(it))?.title!!
                    val collectionId= category.toData()!!.get(category.toData()!!.lastIndexOf(it)).id!!
                    val action=HomeFragmentDirections.actionHomeFragmentToCategoryFragment(title,collectionId)
                        Navigation.findNavController(requireView()).navigate(action)


                }
                //to drop the first item from response
                mainCategoriesAdapter.submitList(category.toData()?.drop(1))

                Log.i("HomeFragment",category.toData()?.get(0)?.image?.src.toString())
                binding.recyclerCategory.adapter = mainCategoriesAdapter
            }

        }

    }

    private fun getBrands() {
        lifecycleScope.launch {
            homeViewModel.brands.collect {
                brandsAdapter=BrandsAdapter(requireContext()){

                    val action=HomeFragmentDirections.actionHomeFragmentToBrandProductsFragment(it.title!!,it.image?.src!!)
                    Navigation.findNavController(requireView()).navigate(action)
                }
                brandsAdapter.submitList(it.toData())

                Log.i("HomeFragment",it.toData()?.get(0)?.image?.src.toString())
                binding.recyclerBrands.adapter = brandsAdapter
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
        homeViewModel = ViewModelProvider(
            this,
            showProductsViewModelFactory
        )[HomeViewModel::class.java]
    }



}