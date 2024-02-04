package com.fazliddin.armebel.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fazliddin.armebel.data.database.models.CartFurniture
import com.fazliddin.armebel.data.database.models.LikedFurniture

@Dao
interface FurnitureDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFurniture(furniture: LikedFurniture)

    @Delete
    fun deleteLikedFurniture(furniture: LikedFurniture)

    @Query("SELECT * FROM liked_furniture ORDER BY added_date DESC")
    fun getLikedFurniture(): LiveData<List<LikedFurniture>>

    @Query("SELECT * FROM liked_furniture WHERE id=:id")
    fun getLikedFurnitureById(id: Long): LikedFurniture?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToCart(furniture: CartFurniture)

    @Delete
    fun deleteFromCart(furniture: CartFurniture)

    @Query("SELECT * FROM cart ORDER BY added_date DESC")
    fun getFurnitureOnCart(): LiveData<List<CartFurniture>>

    @Query("SELECT SUM(quantity) FROM cart")
    fun getFurnitureCount(): LiveData<Int>

    @Query("SELECT * FROM cart WHERE id=:id")
    fun getFurnitureOnCart(id: Long): CartFurniture?

    @Update
    fun updateFurniture(furniture: CartFurniture)
}