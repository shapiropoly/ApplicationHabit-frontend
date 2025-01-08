package ru.apphabit.features.habits.data

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import ru.apphabit.features.habits.model.Category

interface CategoryApiService {
    @GET("categories")
    suspend fun getAllCategories(): Response<List<Category>>

    @POST("add_category")
    suspend fun addCategory(@Body category: Category): Response<Category>

    @GET("categories/{id}")
    suspend fun getCategoryById(@Path("id") id: Int): Response<Category>

    @PUT("update_category/{id}")
    fun updateCategory(@Path("id") id: Int, @Body category: Category): Call<Category>

    @DELETE("delete_category/{id}")
    fun deleteCategory(@Path("id") id: Int): Call<Category>
}