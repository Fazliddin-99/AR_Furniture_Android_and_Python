package com.fazliddin.armebel.presentation.ui.alltypes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fazliddin.armebel.databinding.TypesAllListItemBinding
import com.fazliddin.armebel.data.remote.dto.TypeFurniture

class AllTypesListAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<TypeFurniture, AllTypesListAdapter.TypeFurnitureVH>(
        object : DiffUtil.ItemCallback<TypeFurniture>() {
            override fun areItemsTheSame(oldItem: TypeFurniture, newItem: TypeFurniture) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TypeFurniture, newItem: TypeFurniture) =
                oldItem == newItem
        }
    ) {
    class TypeFurnitureVH(private val binding: TypesAllListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TypeFurniture, onClickListener: OnClickListener) {
            binding.type = item
            binding.root.setOnClickListener {
                onClickListener.onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeFurnitureVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TypesAllListItemBinding.inflate(inflater, parent, false)

        binding.categoryImage.clipToOutline = true

        return TypeFurnitureVH(binding)
    }

    override fun onBindViewHolder(holder: TypeFurnitureVH, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }

    class OnClickListener(private var onClickListener: (TypeFurniture) -> Unit) {
        fun onClick(type: TypeFurniture) = onClickListener(type)
    }

}