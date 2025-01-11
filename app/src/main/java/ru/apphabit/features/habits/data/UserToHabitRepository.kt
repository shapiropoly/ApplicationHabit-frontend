package ru.apphabit.features.habits.data

import android.util.Log
import retrofit2.Call
import ru.apphabit.features.habits.model.UserToHabit

interface UserToHabitRepository {
    suspend fun getAllUserToHabits(): List<UserToHabit>
    suspend fun getUserToHabitById(id: Int): UserToHabit
    suspend fun getUserToHabitByUserId(userId: Int): List<UserToHabit>
    suspend fun addUserToHabit(userToHabit: UserToHabit): UserToHabit
    fun updateUserToHabit(id: Int, userToHabit: UserToHabit): Call<UserToHabit>
    suspend fun deleteUserToHabit(id: Int)
}

class UserToHabitRepositoryImpl(private val service: UserToHabitApiService): UserToHabitRepository {
    override suspend fun getAllUserToHabits(): List<UserToHabit> {
        val response = service.getAllUserToHabits()
        if (response.isSuccessful) {
            val body = response.body()
            Log.d("userToHabit", "Response: $body")
            return body ?: emptyList()
        } else {
            Log.e("userToHabit", "Error: ${response.errorBody()?.string()}")
            return emptyList()
        }
    }

    override suspend fun getUserToHabitById(id: Int): UserToHabit {
        val body = service.getUserToHabitById(id).body()!!
        return body
    }

    override suspend fun getUserToHabitByUserId(userId: Int): List<UserToHabit> {
        val response = service.getUserToHabitByUserId(userId)
        Log.d("UserToHabitRepository", "Response body: ${response.body()}")

        val body = service.getUserToHabitByUserId(userId).body()!!
        return body
    }

    override suspend fun addUserToHabit(userToHabit: UserToHabit): UserToHabit {
        val body = service.addUserToHabit(userToHabit).body()!!
        return body
    }

    override fun updateUserToHabit(id: Int, userToHabit: UserToHabit): Call<UserToHabit> {
        return service.updateUserToHabit(id, userToHabit)
    }

    override suspend fun deleteUserToHabit(id: Int) {
        val response = service.deleteUserToHabit(id)
        if (!response.isSuccessful) {
            Log.e("userToHabit", "Failed to delete habit with id $id: ${response.errorBody()?.string()}")
        }
    }

}