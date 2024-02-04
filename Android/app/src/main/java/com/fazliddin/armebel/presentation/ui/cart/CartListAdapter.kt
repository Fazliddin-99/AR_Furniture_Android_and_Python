package com.fazliddin.armebel.presentation.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fazliddin.armebel.data.database.models.CartFurniture
import com.fazliddin.armebel.databinding.CartItemBinding

class CartListAdapter(private val updateFurnitureCallback: (CartFurniture, Int) -> Unit) :
    ListAdapter<CartFurniture, CartListAdapter.CartVH>(
        object : DiffUtil.ItemCallback<CartFurniture>() {
            override fun areItemsTheSame(oldItem: CartFurniture, newItem: CartFurniture) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CartFurniture, newItem: CartFurniture) =
                oldItem == newItem
        }
    ) {

    class CartVH(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartFurniture, updateFurnitureCallback: (CartFurniture, Int) -> Unit) {
            binding.furniture = item

            binding.minusBtn.setOnClickListener {
                updateFurnitureCallback(item, item.quantity - 1)
                binding.notifyPropertyChanged(binding.furnitureQuantity.id)
            }

            binding.plusBtn.setOnClickListener {
                updateFurnitureCallback(item, item.quantity + 1)
                binding.notifyPropertyChanged(binding.furnitureQuantity.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartVH {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CartVH(binding)
    }

    override fun onBindViewHolder(holder: CartVH, position: Int) {
        holder.bind(getItem(position), updateFurnitureCallback)
    }
}