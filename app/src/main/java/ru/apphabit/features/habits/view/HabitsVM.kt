package ru.apphabit.features.habits.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.apphabit.features.habits.data.HabitRepository
import ru.apphabit.features.habits.model.Habit

class HabitsVM (private val repository: HabitRepository) : ViewModel() {

//    private val _habit = MutableLiveData<Habit>()
//    val habit: LiveData<Habit> get() = _habit

    private val _habits = MutableLiveData<List<Habit>>()
    val habits: LiveData<List<Habit>> get() = _habits

    init {
        getAllHabits()
    }

    fun getAllHabits() {
        viewModelScope.launch {
            val habitsList = repository.getAllHabits()
            Log.d("HabitsVM", "Habits received: $habitsList")
            _habits.value = habitsList
        }
    }

//    fun getHabitById(id: Int) {
//        viewModelScope.launch {
//            _habit.value = repository.getHabitById(id)
//        }
//    }

    fun updateHabit(id: Int, habit: Habit) {
        repository.updateHabit(id, habit).enqueue(object : Callback<Habit> {
            override fun onResponse(call: Call<Habit>, response: Response<Habit>) {
                // handle the response
            }

            override fun onFailure(call: Call<Habit>, t: Throwable) {
                // handle the failure
            }
        })
    }

    fun deleteHabit(id: Int) {
        repository.deleteHabit(id).enqueue(object : Callback<Habit> {
            override fun onResponse(call: Call<Habit>, response: Response<Habit>) {
                // handle the response
            }

            override fun onFailure(call: Call<Habit>, t: Throwable) {
                // handle the failure
            }
        })
    }
}