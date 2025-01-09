package ru.apphabit.features.profile.data

import android.util.Log
import retrofit2.Call
import ru.apphabit.features.profile.model.User

interface UserRepository {
    suspend fun getAllUsers(): List<User>
    suspend fun addUser(user: User): User
    suspend fun getUserById(id: Int): User
    suspend fun getUserByEmail(email: String): User
    fun updateUser(id: Int, user: User): Call<User>
    fun deleteUser(id: Int): Call<User>
}

class UserRepositoryImpl (private val service: UserApiService): UserRepository {

    override suspend fun getAllUsers(): List<User> {
        val response = service.getAllUsers()
        if (response.isSuccessful) {
            val body = response.body()
            Log.d("users", "Response: $body")
            return body ?: emptyList()
        } else {
            Log.e("users", "Error: ${response.errorBody()?.string()}")
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

    override suspend fun getUserByEmail(email: String): User {
        val body = service.getUserByEmail(email).body()!!
        return body
    }

    override fun updateUser(id: Int, user: User): Call<User> {
        return service.updateUser(id, user)
    }

    override fun deleteUser(id: Int): Call<User> {
        return service.deleteUser(id)
    }
}