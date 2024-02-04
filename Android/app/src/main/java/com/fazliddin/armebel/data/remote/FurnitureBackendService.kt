package com.fazliddin.armebel.data.remote

import com.fazliddin.armebel.common.Constants
import com.fazliddin.armebel.data.remote.dto.Furniture
import com.fazliddin.armebel.data.remote.dto.Category
import com.fazliddin.armebel.data.remote.dto.TypeFurniture
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val httpClient = OkHttpClient.Builder().apply {
    addInterceptor {
        val request: Request = it.request()
            .newBuilder()
            .addHeader("x-key", Constants.API_KEY)
            .build()
        it.proceed(request)
    }
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .client(httpClient.build())
    .build()

interface FurnitureServiceAPI {
    @GET("categories")
    suspend fun getCategories(): List<Category>

    @GET("types")
    suspend fun getTypes(@Query("category_id") categoryId: Long): List<TypeFurniture>

    @GET("furniture")
    suspend fun getFurniture(
        @Query("category_id") categoryId: Long? = null,
        @Query("type_id") typeId: Long? = null
    ): List<Furniture>
}

object FurnitureAPI {
    val retrofitService: FurnitureServiceAPI by lazy {
        retrofit.create(FurnitureServiceAPI::class.java)
    }
}