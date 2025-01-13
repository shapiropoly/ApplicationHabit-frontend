package ru.apphabit.features.collections.data

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import ru.apphabit.features.collections.model.HabitToCollection


interface HabitToCollectionApiService {
    @GET("habits_to_collections")
    suspend fun getAllHabitsToCollections(): Response<List<HabitToCollection>>

    @POST("add_habit_to_collection")
    suspend fun addHabitToCollection(@Body collection: HabitToCollection): Response<HabitToCollection>

    @PUT("update_habit_to_collection/{id}")
    fun updateHabitToCollection(@Path("id") id: Int, @Body collection: HabitToCollection): Call<HabitToCollection>

    @DELETE("delete_habit_to_collection/{id}")
    fun deleteHabitToCollection(@Path("id") id: Int): Call<HabitToCollection>
}