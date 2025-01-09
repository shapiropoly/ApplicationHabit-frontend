package ru.apphabit.features.habits.data

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import ru.apphabit.features.habits.model.UserToHabit

interface UserToHabitApiService {
    @GET("user_to_habit")
    suspend fun getAllUserToHabits(): Response<List<UserToHabit>>

    @POST("add_user_to_habit")
    suspend fun addUserToHabit(@Body userToHabit: UserToHabit): Response<UserToHabit>

    @GET("user_to_habit/{id}")
    suspend fun getUserToHabitById(@Path("id") id: Int): Response<UserToHabit>

    @GET("user_to_habit/{user_id}")
    suspend fun getUserToHabitByUserId(@Path("user_id") userId: Int): Response<List<UserToHabit>>

    @PUT("update_user_to_habit/{id}")
    fun updateUserToHabit(@Path("id") id: Int, @Body userToHabit: UserToHabit): Call<UserToHabit>

    @DELETE("delete_user_to_habit/{id}")
    fun deleteUserToHabit(@Path("id") id: Int): Call<UserToHabit>
}