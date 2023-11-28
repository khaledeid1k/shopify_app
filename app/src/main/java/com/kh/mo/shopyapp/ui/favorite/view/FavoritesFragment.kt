package com.kh.mo.shopyapp.ui.favorite.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentFavoritesBinding
import com.kh.mo.shopyapp.model.ui.Favorite
import com.kh.mo.shopyapp.model.ui.allproducts.Product
import com.kh.mo.shopyapp.ui.base.BaseFragment
import com.kh.mo.shopyapp.ui.favorite.viewmodel.FavoritesViewModel
import com.kh.mo.shopyapp.utils.createDialog
import com.kh.mo.shopyapp.utils.makeGone
import com.kh.mo.shopyapp.utils.makeVisible
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class FavoritesFragment : BaseFragment<FragmentFavoritesBinding, FavoritesViewModel>() {
    private lateinit var favoritesAdapter : FavoritesAdapter
     var  job: Job?=null
    override val layoutIdFragment=R.layout.fragment_favorites

    override fun getViewModelClass()=FavoritesViewModel::class.java


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllFavorites()
        observeDeleteFavorite()

    }



   private fun getAllFavorites(){
        lifecycleScope.launch {
            viewModel.favorites.collect {
                if (it.isNotEmpty()) {
             binding.noFavorites.makeGone()
                }
                else{
                    binding.noFavorites.makeVisible()
              }
                addAdapterToFavorites(it)
            }
        }
    }


    private fun observeSingleFavorites(){
        job=     lifecycleScope .launch {
           viewModel.singleFavorites.collect{
                 navigateToProductDetails(it)
            }
        }
    }

    private fun observeDeleteFavorite(){
        job=     lifecycleScope .launch {
            viewModel.deleteFavorites.collect{
                createDialog(context = requireContext(),
                title = getString(R.string.are_you_sure),
                    message = getString(R.string.will_delete_this_from_favorites),
                    sure = {viewModel.deleteFavoriteById(it)}, cancel = {})

            }
        }
    }
    private fun navigateToProductDetails(product: Product) {
        Log.d("TAG", "navigateToProductDetails: $product")
        findNavController().navigate(FavoritesFragmentDirections.actionFavoritesFragmentToProductFragment(product))
    }

    private fun addAdapterToFavorites(favorites: List<Favorite>) {
        favoritesAdapter=FavoritesAdapter(viewModel){
            Log.d("TAG", "addAdapterToFavorites: $it")
            viewModel.getSingleFavorite(favorites[it].productId)
            observeSingleFavorites()
        }
        favoritesAdapter.setItems(favorites)
        binding.favoritesList.adapter=favoritesAdapter

    }

    override fun onPause() {
        super.onPause()
            job?.cancel()

    }

    }