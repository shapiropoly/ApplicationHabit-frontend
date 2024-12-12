package ru.apphabit.features.collections.data

import android.util.Log
import retrofit2.Call
import ru.apphabit.features.collections.model.Collection


interface CollectionRepository {
    suspend fun getAllCollections(): List<Collection>
    suspend fun addCollection(collection: Collection): Collection
    suspend fun getCollectionById(id: Int): Collection
    fun updateCollection(id: Int, collection: Collection): Call<Collection>
    fun deleteCollection(id: Int): Call<Collection>
}

class CollectionRepositoryImpl (private val service: CollectionApiService): CollectionRepository {

    override suspend fun getAllCollections(): List<Collection> {
        val response = service.getAllCollections()
        if (response.isSuccessful) {
            val body = response.body()
            Log.d("collection", "Response: $body")
            return body ?: emptyList()
        } else {
            Log.e("collection", "Error: ${response.errorBody()?.string()}")
            return emptyList()
        }
    }

    override suspend fun addCollection(collection: Collection): Collection {
        val body = service.addCollection(collection).body()!!
        return body
    }

    override suspend fun getCollectionById(id: Int): Collection {
        val body = service.getCollectionById(id).body()!!
        return body
    }

    override fun updateCollection(id: Int, collection: Collection): Call<Collection> {
        return service.updateCollection(id, collection)
    }

    override fun deleteCollection(id: Int): Call<Collection> {
        return service.deleteCollection(id)
    }
}