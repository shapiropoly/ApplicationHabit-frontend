package ru.apphabit.features.checkup.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback

import ru.apphabit.features.checkup.data.CheckUpRepository
import ru.apphabit.features.checkup.model.CheckUp
import ru.apphabit.features.checkup.model.HabitWithCheckUp
import ru.apphabit.features.checkup.model.UserHabitCheckUp
import ru.apphabit.features.habits.data.HabitRepository
import ru.apphabit.features.habits.data.UserToHabitRepository
import java.time.LocalDate

class CheckUpVM (
    private val repositoryCheckUp: CheckUpRepository,
    private val repositoryHabit: HabitRepository,
    private val repositoryUserToHabit: UserToHabitRepository,
) : ViewModel() {

    private val _checkup = MutableLiveData<CheckUp>()
    val checkup: MutableLiveData<CheckUp> get() = _checkup

    private val _checkups = MutableLiveData<List<CheckUp>>()
    val checkups: LiveData<List<CheckUp>> get() = _checkups

    private val _userHabitCheckups = MutableLiveData<List<UserHabitCheckUp>>()
    val userHabitCheckups: LiveData<List<UserHabitCheckUp>> get() = _userHabitCheckups

    private val _habitWithCheckUp = MutableLiveData<List<HabitWithCheckUp>>()
    val habitWithCheckUp: LiveData<List<HabitWithCheckUp>> get() = _habitWithCheckUp


    init {
        getAllCheckUps()
    }

    private fun getAllCheckUps() {
        viewModelScope.launch {
            val checkUpsList = repositoryCheckUp.getAllCheckUps()
            Log.d("CheckUpsVM", "CheckUps received: $checkUpsList")
            _checkups.value = checkUpsList
        }
    }

    fun addCheckUp(checkUp: CheckUp) {
        viewModelScope.launch {
            val newCheckUp = repositoryCheckUp.addCheckUp(checkUp)
            Log.d("Add checkUp from CheckUpVM", "CheckUp: $checkUp")
            _checkup.value = newCheckUp
        }
    }

    // TODO: реализовать на бэке метод получения списка чекапов по id User
    fun getCheckUpByUserId(userId: Int) {
        viewModelScope.launch {
            _checkups.value = repositoryCheckUp.getCheckUpsByUserId(userId)
        }
    }

    fun updateCheckUp(id: Int, checkUp: CheckUp) {
        repositoryCheckUp.updateCheckUp(id, checkUp).enqueue(object : Callback<CheckUp> {
            override fun onResponse(call: Call<CheckUp>, response: Response<CheckUp>) {
                // handle the response
            }

            override fun onFailure(call: Call<CheckUp>, t: Throwable) {
                // handle the failure
            }
        })
    }

    fun deleteCheckUp(id: Int) {
        repositoryCheckUp.deleteCheckUp(id).enqueue(object : Callback<CheckUp> {
            override fun onResponse(call: Call<CheckUp>, response: Response<CheckUp>) {
            }

            override fun onFailure(call: Call<CheckUp>, t: Throwable) {
            }
        })
    }

    fun getCheckUpsWithDetailsByUserId(userId: Int) {
        viewModelScope.launch {
            val userHabitCheckupsList = repositoryCheckUp.getCheckUpsWithDetailsByUserId(userId)
            Log.d("UserHabitCheckupsListVM", "UserHabitCheckupsListVM received: $userHabitCheckupsList")
            _userHabitCheckups.value = userHabitCheckupsList
        }
    }

//    fun getListHabitsWithDetails(userId: Int) {
//        viewModelScope.launch {
//            val habits = repositoryHabit.getHabitsByUserId(userId)
//            val userToHabitIds = repositoryUserToHabit.getUserToHabitByUserId(userId)
//            val checkUps = repositoryCheckUp.getCheckUpsByUserId(userId)
//
//            val habitWithCheckUp = habits.map { habit ->
//                val userToHabit = userToHabitIds.find { it.habit.id == habit.id }
//                val checkUp = checkUps.find { it.userToHabitId == userToHabit?.id }
//                val dateCheckUp = checkUps.dateCheckUp
//
//                HabitWithCheckUp(
//                    habit = habit,
//                    frequency = userToHabit?.frequency ?: "N/A",
//                    dateCheckUp = dateCheckUp,
//                    checkUp = checkUp
//                )
//            }
//
//            _habitWithCheckUp.value = habitWithCheckUp
//        }
//    }

}