package com.fazliddin.armebel.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fazliddin.armebel.data.database.models.CartFurniture
import com.fazliddin.armebel.data.database.models.LikedFurniture

@Database(entities = [LikedFurniture::class, CartFurniture::class], version = 4)
abstract class FurnitureDatabase : RoomDatabase() {
    abstract val dao: FurnitureDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: FurnitureDatabase? = null

        fun getInstance(context: Context): FurnitureDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        FurnitureDatabase::class.java,
                        "furniture_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}