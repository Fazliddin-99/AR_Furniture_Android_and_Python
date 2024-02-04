package com.fazliddin.armebel.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fazliddin.armebel.data.remote.dto.BaseFurniture
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "liked_furniture")
data class LikedFurniture(
    @PrimaryKey
    override val id: Long = 0,
    override val name: String = "",
    override val description: String = "",
    override val image1: String = "",
    override val image2: String? = "",
    override val image3: String? = "",
    override val model3d: String = "",
    override val price: Double = 0.0,
    var isLiked: Boolean = false,

    @ColumnInfo(name = "added_date")
    val dateAdded: Long = System.currentTimeMillis()
) : BaseFurniture()
