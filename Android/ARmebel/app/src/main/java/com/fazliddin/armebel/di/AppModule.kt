package com.fazliddin.armebel.di

import android.content.Context
import com.fazliddin.armebel.data.database.FurnitureDatabase
import com.fazliddin.armebel.data.database.FurnitureDatabaseDao
import com.fazliddin.armebel.data.remote.FurnitureAPI
import com.fazliddin.armebel.data.remote.FurnitureServiceAPI
import com.fazliddin.armebel.data.repository.FurnitureRepositoryImpl
import com.fazliddin.armebel.domain.repository.FurnitureRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFurnitureApi(): FurnitureServiceAPI {
        return FurnitureAPI.retrofitService
    }

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext appContext: Context): FurnitureDatabaseDao {
        return FurnitureDatabase.getInstance(appContext).dao
    }

    @Provides
    @Singleton
    fun provideRepository(api: FurnitureServiceAPI, db: FurnitureDatabaseDao): FurnitureRepository {
        return FurnitureRepositoryImpl(api, db)
    }
}