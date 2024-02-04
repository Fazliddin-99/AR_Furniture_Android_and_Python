package com.fazliddin.armebel.presentation.ui.startscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fazliddin.armebel.databinding.CategoriesListItemBinding
import com.fazliddin.armebel.data.remote.dto.Category

class CategoriesListAdapter(private val onClickListener: OnClickListener) : ListAdapter<Category, CategoriesListAdapter.CategoryVH>(
    object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Category, newItem: Category) = oldItem == newItem
    }
) {

    class CategoryVH(private val binding: CategoriesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category, onClickListener: OnClickListener) {
            binding.category = item
            binding.root.setOnClickListener {
                onClickListener.onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoriesListItemBinding.inflate(inflater, parent, false)

        return CategoryVH(binding)
    }

    override fun onBindViewHolder(holder: CategoryVH, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }

    class OnClickListener(private var onClickListener: (Category) -> Unit) {
        fun onClick(category: Category) = onClickListener(category)
    }
}