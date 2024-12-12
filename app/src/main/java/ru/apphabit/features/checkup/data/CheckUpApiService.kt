package ru.apphabit.features.checkup.data

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import ru.apphabit.features.checkup.model.CheckUp
import java.util.*

interface CheckUpApiService {
    @GET
    suspend fun getAllCheckUps(): Response<List<CheckUp>>

    @POST("add_checkup")
    suspend fun addCheckUp(@Body checkup: CheckUp): Response<CheckUp>

    @GET("checkups/{id}")
    suspend fun getCheckUpById(@Path("id") id: Int): Response<CheckUp>

    @PUT("update_checkup/{id}")
    fun updateCheckUp(@Path("id") id: Int, @Body checkup: CheckUp): Call<CheckUp>

    @DELETE("delete_checkup/{id}")
    fun deleteCheckUp(@Path("id") id: Int): Call<CheckUp>
}