package com.fazliddin.armebel.data.remote.dto

import android.os.Parcelable
import com.fazliddin.armebel.data.database.models.CartFurniture

abstract class BaseFurniture : Parcelable {
    abstract val id: Long
    abstract val name: String
    abstract val description: String
    abstract val image1: String
    abstract val image2: String?
    abstract val image3: String?
    abstract val model3d: String
    abstract val price: Double

    fun toCartFurniture(): CartFurniture {
        return CartFurniture(id, name, description, image1, image2, image3, model3d, price)
    }
}
