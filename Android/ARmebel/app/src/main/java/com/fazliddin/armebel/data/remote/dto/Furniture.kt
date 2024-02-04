package com.fazliddin.armebel.data.remote.dto

import com.fazliddin.armebel.data.database.models.LikedFurniture
import kotlinx.parcelize.Parcelize

@Parcelize
data class Furniture(
    override val id: Long = 0,
    override val name: String = "",
    override val description: String = "",
    override val image1: String = "",
    override val image2: String? = "",
    override val image3: String? = "",
    override val model3d: String = "",
    override val price: Double = 0.0,
) : BaseFurniture() {

    fun toLikedFurniture(): LikedFurniture {
        return LikedFurniture(id, name, description, image1, image2, image3, model3d, price)
    }
}
