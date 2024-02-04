package com.fazliddin.armebel.domain.repository

import androidx.lifecycle.LiveData
import com.fazliddin.armebel.data.database.models.CartFurniture
import com.fazliddin.armebel.data.database.models.LikedFurniture
import com.fazliddin.armebel.data.remote.dto.BaseFurniture
import com.fazliddin.armebel.data.remote.dto.Category
import com.fazliddin.armebel.data.remote.dto.Furniture
import com.fazliddin.armebel.data.remote.dto.TypeFurniture

interface FurnitureRepository {
    fun getLikedFurniture(): LiveData<List<LikedFurniture>>?
    fun getLikedFurnitureById(id: Long): LikedFurniture?
    fun removeLikedFurniture(furniture: LikedFurniture)
    fun addLikedFurniture(furniture: LikedFurniture)
    fun addToCart(furniture: BaseFurniture)
    fun getFurnitureCountOnCart(): LiveData<Int>?
    fun getFurnitureOnCart(): LiveData<List<CartFurniture>>?
    fun updateCartFurniture(cartFurniture: CartFurniture)
    suspend fun getCategories(): List<Category>
    suspend fun getTypes(categoryId: Long): List<TypeFurniture>
    suspend fun getFurniture(categoryId: Long? = null, typeId: Long? = null): List<Furniture>
}