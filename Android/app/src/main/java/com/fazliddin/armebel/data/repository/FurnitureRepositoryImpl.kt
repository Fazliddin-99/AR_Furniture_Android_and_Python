package com.fazliddin.armebel.data.repository

import androidx.lifecycle.LiveData
import com.fazliddin.armebel.data.database.models.CartFurniture
import com.fazliddin.armebel.data.database.FurnitureDatabaseDao
import com.fazliddin.armebel.data.database.models.LikedFurniture
import com.fazliddin.armebel.data.remote.FurnitureServiceAPI
import com.fazliddin.armebel.data.remote.dto.BaseFurniture
import com.fazliddin.armebel.domain.repository.FurnitureRepository
import javax.inject.Inject

class FurnitureRepositoryImpl @Inject constructor(
    private val api: FurnitureServiceAPI,
    private val db: FurnitureDatabaseDao
) : FurnitureRepository {

    override fun getLikedFurniture() = db.getLikedFurniture()

    override fun getLikedFurnitureById(id: Long) = db.getLikedFurnitureById(id)

    override fun removeLikedFurniture(furniture: LikedFurniture) {
        db.deleteLikedFurniture(furniture)
    }

    override fun addLikedFurniture(furniture: LikedFurniture) {
        db.insertFurniture(furniture)
    }

    override fun addToCart(furniture: BaseFurniture) {
        val furnitureOnCart = db.getFurnitureOnCart(furniture.id)
        val cartFurniture =
            if (furnitureOnCart == null) {
                furniture.toCartFurniture()
            } else {
                furnitureOnCart.quantity++
                furnitureOnCart
            }

        db.insertToCart(cartFurniture)
    }

    override fun getFurnitureCountOnCart(): LiveData<Int> {
        return db.getFurnitureCount()
    }

    override fun getFurnitureOnCart() = db.getFurnitureOnCart()

    override fun updateCartFurniture(cartFurniture: CartFurniture) {
        if (cartFurniture.quantity <= 0)
            db.deleteFromCart(cartFurniture)
        else
            db.updateFurniture(cartFurniture)
    }

    override suspend fun getCategories() = api.getCategories()

    override suspend fun getTypes(categoryId: Long) =
        api.getTypes(categoryId)

    override suspend fun getFurniture(categoryId: Long?, typeId: Long?) =
        api.getFurniture(categoryId, typeId)
}