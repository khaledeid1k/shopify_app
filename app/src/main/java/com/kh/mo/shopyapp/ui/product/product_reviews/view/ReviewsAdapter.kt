package com.kh.mo.shopyapp.ui.product.product_reviews.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.ItemReviewBinding
import com.kh.mo.shopyapp.model.ui.Review
import com.kh.mo.shopyapp.ui.base.BaseDataDiffUtil

class ReviewsAdapter
    : RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>() {

    private var reviews: List<Review> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {


        return ReviewsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_review,
                parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        val review = reviews[position]
        holder.bind(review)
    }

    override fun getItemCount()=reviews.size

    class ReviewsViewHolder (private val binding: ItemReviewBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(review: Review) {
            binding.review = review
        }
    }

    fun setItems(newItems: List<Review>) {
        val diffResult = DiffUtil.calculateDiff(
            BaseDataDiffUtil(reviews, newItems,
                checkItemsTheSame=    { oldItemPosition, newItemPosition -> reviews[oldItemPosition].name == newItems[newItemPosition].name },
                checkContentsTheSame=  { oldItemPosition, newItemPosition -> reviews[oldItemPosition] == newItems[newItemPosition] }
            )
        )
        reviews = newItems
        diffResult.dispatchUpdatesTo(this)
    }







}
