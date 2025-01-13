package ru.apphabit.features.collections.data

import android.util.Log
import retrofit2.Call
import ru.apphabit.features.collections.model.Collection
import ru.apphabit.features.collections.model.HabitToCollection

interface HabitToCollectionRepository {
    suspend fun getAllHabitsToCollections(): List<HabitToCollection>
    suspend fun addHabitToCollection(collection: HabitToCollection): HabitToCollection
    fun updateHabitToCollection(id: Int, collection: HabitToCollection): Call<HabitToCollection>
    fun deleteHabitToCollection(id: Int): Call<HabitToCollection>
}

class HabitToCollectionRepositoryImpl (private val service: HabitToCollectionApiService): HabitToCollectionRepository {
    override suspend fun getAllHabitsToCollections(): List<HabitToCollection> {
        val response = service.getAllHabitsToCollections()
        if (response.isSuccessful) {
            val body = response.body()
            Log.d("collection", "Response: $body")
            return body ?: emptyList()
        } else {
            Log.e("collection", "Error: ${response.errorBody()?.string()}")
            return emptyList()
        }
    }

    override suspend fun addHabitToCollection(collection: HabitToCollection): HabitToCollection {
        val body = service.addHabitToCollection(collection).body()!!
        return body
    }

    override fun updateHabitToCollection(id: Int, collection: HabitToCollection): Call<HabitToCollection> {
        return service.updateHabitToCollection(id, collection)
    }

    override fun deleteHabitToCollection(id: Int): Call<HabitToCollection> {
        return service.deleteHabitToCollection(id)
    }
}