package ru.apphabit.features.profile.data

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import ru.apphabit.features.profile.model.User

interface UserApiService {
    @GET("users")
    suspend fun getAllUsers(): Response<List<User>>

    @POST("add_user")
    suspend fun addUser(@Body user: User): Response<User>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): Response<User>

    @GET("users/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): Response<User>

    @PUT("update_user/{id}")
    fun updateUser(@Path("id") id: Int, @Body user: User): Call<User>

    @DELETE("delete_user/{id}")
    fun deleteUser(@Path("id") id: Int): Call<User>
}