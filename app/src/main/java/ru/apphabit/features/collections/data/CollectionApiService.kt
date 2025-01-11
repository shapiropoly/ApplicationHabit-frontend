package ru.apphabit.features.collections.data

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import ru.apphabit.features.collections.model.Collection



interface CollectionApiService {
    @GET("collections")
    suspend fun getAllCollections(): Response<List<Collection>>

    @POST("add_collection")
    suspend fun addCollection(@Body collection: Collection): Response<Collection>

    @GET("collections/{id}")
    suspend fun getCollectionById(@Path("id") id: Int): Response<Collection>

    @PUT("update_collection/{id}")
    fun updateCollection(@Path("id") id: Int, @Body collection: Collection): Call<Collection>

    @DELETE("delete_collection/{id}")
    fun deleteCollection(@Path("id") id: Int): Call<Collection>
}