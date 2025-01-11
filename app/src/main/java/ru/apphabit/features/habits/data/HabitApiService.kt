package ru.apphabit.features.habits.data

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import ru.apphabit.features.habits.model.Habit

interface HabitApiService {
    @GET("habits")
    suspend fun getAllHabits(): Response<List<Habit>>

    @POST("add_habit")
    suspend fun addHabit(@Body habit: Habit): Response<Habit>

    @GET("habits/{id}")
    suspend fun getHabitById(@Path("id") id: Int): Response<Habit>

    @GET("habits/{user_id}")
    suspend fun getHabitsByUserId(@Path("user_id") userId: Int): Response<List<Habit>>

    @GET("habits/collections/{collection_id}")
    suspend fun getHabitsByCollectionId(@Path("collection_id") collectionId: Int): Response<List<Habit>>

    @PUT("update_habit/{id}")
    fun updateHabit(@Path("id") id: Int, @Body habit: Habit): Call<Habit>

    @DELETE("delete_habit/{id}")
    fun deleteHabit(@Path("id") id: Int): Call<Habit>
}