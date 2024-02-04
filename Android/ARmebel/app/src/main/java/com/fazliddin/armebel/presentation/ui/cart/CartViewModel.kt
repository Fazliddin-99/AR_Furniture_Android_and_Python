package com.fazliddin.armebel.presentation.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazliddin.armebel.data.database.models.CartFurniture
import com.fazliddin.armebel.domain.repository.FurnitureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val repository: FurnitureRepository) : ViewModel() {

    val cartFurnitureList: LiveData<List<CartFurniture>>? = repository.getFurnitureOnCart()

    fun updateCartFurnitureQuantity(furniture: CartFurniture, quantity: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val cartFurniture = CartFurniture(furniture)
                cartFurniture.quantity = quantity
                repository.updateCartFurniture(cartFurniture)
            }
        }
    }
}