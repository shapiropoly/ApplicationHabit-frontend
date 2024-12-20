package ru.apphabit.features.profile.data

import android.util.Log
import retrofit2.Call
import ru.habit_app.app.features.users.model.User

interface UserRepository {
    suspend fun getAllUsers(): List<User>
    suspend fun addUser(user: User): User
    suspend fun getUserById(id: Int): User
    fun updateUser(id: Int, user: User): Call<User>
    fun deleteUser(id: Int): Call<User>
}

class UserRepositoryImpl (private val service: UserApiService): UserRepository {

    override suspend fun getAllUsers(): List<User> {
        val response = service.getAllUsers()
        if (response.isSuccessful) {
            val body = response.body()
            Log.d("checkup", "Response: $body")
            return body ?: emptyList()
        } else {
            Log.e("checkup", "Error: ${response.errorBody()?.string()}")
            return emptyList()
        }
    }

    override suspend fun addUser(user: User): User {
        val body = service.addUser(user).body()!!
        return body
    }

    override suspend fun getUserById(id: Int): User {
        val body = service.getUserById(id).body()!!
        return body
    }

    override fun updateUser(id: Int, user: User): Call<User> {
        return service.updateUser(id, user)
    }

    override fun deleteUser(id: Int): Call<User> {
        return service.deleteUser(id)
    }
}