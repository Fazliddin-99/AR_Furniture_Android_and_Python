package com.fazliddin.armebel.presentation.ui.types

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fazliddin.armebel.databinding.TypesListItemBinding
import com.fazliddin.armebel.data.remote.dto.TypeFurniture

class TypesListAdapter(private val onClickListener: OnClickListener) : ListAdapter<TypeFurniture, TypesListAdapter.CategoryVH>(
    object : DiffUtil.ItemCallback<TypeFurniture>() {
        override fun areItemsTheSame(oldItem: TypeFurniture, newItem: TypeFurniture) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TypeFurniture, newItem: TypeFurniture) = oldItem == newItem
    }
) {

    class CategoryVH(private val binding: TypesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TypeFurniture, onClickListener: OnClickListener) {
            binding.type = item

            binding.root.setOnClickListener {
                onClickListener.onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TypesListItemBinding.inflate(inflater, parent, false)

        return CategoryVH(binding)
    }

    override fun onBindViewHolder(holder: CategoryVH, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }

    class OnClickListener(private var onClickListener: (TypeFurniture) -> Unit) {
        fun onClick(type: TypeFurniture) = onClickListener(type)
    }
}
