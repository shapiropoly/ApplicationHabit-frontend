package ru.apphabit.features.profile.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.apphabit.features.profile.data.UserRepository
import ru.apphabit.features.profile.model.User

class UsersVM (private val repository: UserRepository) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user


    init {
        getAllUsers()
    }

    private fun getAllUsers() {
        viewModelScope.launch {
            val usersList = repository.getAllUsers()
            Log.d("UsersVM", "Users received: $usersList")
            _users.value = usersList
        }
    }

    fun addHabit(user: User) {
        viewModelScope.launch {
            val newUser = repository.addUser(user)
            Log.d("Add user from UsersVM", "User: $user")
            _user.value = newUser
        }
    }

    fun getUserById(id: Int) {
        viewModelScope.launch {
            _user.value = repository.getUserById(id)
        }
    }

    fun updateUser(id: Int, user: User) {
        repository.updateUser(id, user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
            }
        })
    }

    fun deleteUser(id: Int) {
        repository.deleteUser(id).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                // handle the response
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                // handle the failure
            }
        })
    }
}