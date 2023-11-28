package com.kh.mo.shopyapp.ui.favorite.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.ItemFavoriteBinding
import com.kh.mo.shopyapp.model.ui.Favorite
import com.kh.mo.shopyapp.ui.base.BaseDataDiffUtil

class FavoritesAdapter(private val favoritesAdapterListener:FavoritesAdapterListener,
                       private   val onClickFavorite:(Int)->Unit) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    private var favorites: List<Favorite> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {


        return FavoritesViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_favorite,
                parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val favorite = favorites[position]
        holder.bind(favorite,favoritesAdapterListener)
        holder.itemView.setOnClickListener {
            onClickFavorite(position)
        }
    }

    override fun getItemCount()=favorites.size

    class FavoritesViewHolder (private val binding: ItemFavoriteBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(favorite: Favorite,favoritesAdapterListener:FavoritesAdapterListener) {
            binding.apply {
                item = favorite
                listener=favoritesAdapterListener
            }

        }
    }

    fun setItems(newItems: List<Favorite>) {
        val diffResult = DiffUtil.calculateDiff(
            BaseDataDiffUtil(favorites, newItems,
                checkItemsTheSame=    { oldItemPosition, newItemPosition -> favorites[oldItemPosition].productId == newItems[newItemPosition].productId },
                checkContentsTheSame=  { oldItemPosition, newItemPosition -> favorites[oldItemPosition] == newItems[newItemPosition] }
            )
        )
        favorites = newItems
        diffResult.dispatchUpdatesTo(this)
    }





    interface FavoritesAdapterListener {

        fun deleteFavoriteListener(productId: Long)
    }

}
