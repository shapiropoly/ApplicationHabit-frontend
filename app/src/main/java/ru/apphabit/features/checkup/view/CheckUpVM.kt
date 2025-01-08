package ru.apphabit.features.checkup.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.apphabit.features.checkup.data.CheckUpRepository
import ru.apphabit.features.checkup.model.CheckUp
import ru.apphabit.features.habits.data.HabitRepository
import ru.apphabit.features.habits.model.Habit
import ru.apphabit.features.profile.data.UserRepository
import ru.apphabit.features.profile.model.User

class CheckUpVM (private val repositoryCheckUp: CheckUpRepository,
                 private val repositoryUser: UserRepository,
                 private val repositoryHabit: HabitRepository
    ) : ViewModel() {

    private val _checkup = MutableLiveData<List<CheckUp>>()
    val checkup: LiveData<List<CheckUp>> get() = _checkup

    private val _user = MutableLiveData<List<User>>()
    val user: LiveData<List<User>> get() = _user

    private val _habit = MutableLiveData<List<Habit>>()
    val habit: LiveData<List<Habit>> get() = _habit



//    fun getAllCheckUp(date: LocalDate) {
//        viewModelScope.launch {
//            val checkupList = repositoryCheckUp.getAllCheckUps(date)
//            _checkup.value = checkupList
//
//
//            val user = repositoryUser.getUserById()
//            val habit = repositoryHabit.getHabitById()
//            _user.value.
//        }
//    }


//    fun updateCheckUp(id: Int, checkup: CheckUp) {
//        repository.updateCheckUp(id, checkup).enqueue(object : Callback<CheckUp> {
//            override fun onResponse(call: Call<CheckUp>, response: Response<CheckUp>) {
//                // handle the response
//            }
//
//            override fun onFailure(call: Call<CheckUp>, t: Throwable) {
//                // handle the failure
//            }
//        })
//    }

//    fun deleteCheckUp(id: Int) {
//        repository.deleteCheckUp(id).enqueue(object : Callback<CheckUp> {
//            override fun onResponse(call: Call<CheckUp>, response: Response<CheckUp>) {
//                // handle the response
//            }
//
//            override fun onFailure(call: Call<CheckUp>, t: Throwable) {
//                // handle the failure
//            }
//        })
//    }
}