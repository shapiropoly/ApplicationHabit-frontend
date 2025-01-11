package ru.apphabit.features.checkup.data

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import ru.apphabit.features.checkup.model.CheckUp
import ru.apphabit.features.checkup.model.UserHabitCheckUp

interface CheckUpApiService {
    @GET("checkups")
    suspend fun getAllCheckUps(): Response<List<CheckUp>>

    @POST("add_checkup")
    suspend fun addCheckUp(@Body checkup: CheckUp): Response<CheckUp>

    @GET("checkups/{user_id}")
    suspend fun getCheckUpsByUserId(@Path("user_id") userId: Int) : Response<List<CheckUp>>

    @GET("checkups/user/{userId}")
    suspend fun getCheckUpsWithDetailsByUserId(@Path("user_id") userId: Int) : Response<List<UserHabitCheckUp>>

    @PUT("update_checkup/{id}")
    fun updateCheckUp(@Path("id") id: Int, @Body checkup: CheckUp): Call<CheckUp>

    @DELETE("delete_checkup/{id}")
    fun deleteCheckUp(@Path("id") id: Int): Call<CheckUp>
}