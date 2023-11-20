package com.kh.mo.shopyapp.ui.home.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.kh.mo.shopyapp.databinding.FragmentHomeBinding
import com.kh.mo.shopyapp.home.view.BrandsAdapter
import com.kh.mo.shopyapp.remote.RemoteSourceImp
import com.kh.mo.shopyapp.repo.RepoImp
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.base.BaseViewModelFactory
import com.kh.mo.shopyapp.ui.home.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {

    private lateinit var homeViewModel:HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var brandsAdapter: BrandsAdapter
    private lateinit var mainCategoriesAdapter: MainCategoryAdapter


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
        homeViewModel.getAllBrands()
        homeViewModel.getMainCategories()

        getBrands()
        getMainCategories()




    }

    private fun getMainCategories() {
        lifecycleScope.launch {
            homeViewModel.mainCategories.collect {
                mainCategoriesAdapter= MainCategoryAdapter(requireContext())
                //to drop the first item from response
                mainCategoriesAdapter.submitList(it.toData()?.drop(1))

                Log.i("HomeFragment",it.toData()?.get(0)?.image?.src.toString())
                binding.recyclerCategory.adapter = mainCategoriesAdapter
            }

        }
    }

    private fun getBrands() {
        lifecycleScope.launch {
            homeViewModel.barnds.collect {
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