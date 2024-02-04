package com.fazliddin.armebel.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fazliddin.armebel.data.remote.dto.BaseFurniture
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "cart")
data class CartFurniture(
    @PrimaryKey
    override val id: Long = 0,
    override val name: String = "",
    override val description: String = "",
    override val image1: String = "",
    override val image2: String? = "",
    override val image3: String? = "",
    override val model3d: String = "",
    override val price: Double = 0.0,
    var quantity: Int = 1,

    @ColumnInfo(name = "added_date")
    val dateAdded: Long = System.currentTimeMillis()
) : BaseFurniture() {

    constructor(cartFurniture: CartFurniture) : this(
        cartFurniture.id,
        cartFurniture.name,
        cartFurniture.description,
        cartFurniture.image1,
        cartFurniture.image2,
        cartFurniture.image3,
        cartFurniture.model3d,
        cartFurniture.price,
        cartFurniture.quantity,
        cartFurniture.dateAdded
    )
}