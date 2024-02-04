package com.fazliddin.armebel.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fazliddin.armebel.databinding.FurnitureListItemBinding
import com.fazliddin.armebel.data.remote.dto.BaseFurniture

class FurnituresListAdapter(private val furnitureOnClickListener: OnClickListener) :
    ListAdapter<BaseFurniture, FurnituresListAdapter.FurnitureViewHolder>(object :
        DiffUtil.ItemCallback<BaseFurniture>() {
        override fun areItemsTheSame(oldItem: BaseFurniture, newItem: BaseFurniture) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: BaseFurniture, newItem: BaseFurniture) = oldItem == newItem
    }) {
    class FurnitureViewHolder(private val binding: FurnitureListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(item: BaseFurniture, furnitureOnClickListener: OnClickListener) {
                binding.furniture = item
                binding.root.setOnClickListener {
                    furnitureOnClickListener.onClick(item)
                }
                binding.executePendingBindings()
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FurnitureViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FurnitureListItemBinding.inflate(inflater, parent, false)


        return FurnitureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FurnitureViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item, furnitureOnClickListener)
    }

    class OnClickListener(private var onClickListener: (BaseFurniture) -> Unit) {
        fun onClick(furniture: BaseFurniture) = onClickListener(furniture)
    }
}