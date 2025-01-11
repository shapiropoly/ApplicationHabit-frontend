package ru.apphabit.features.habits.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.apphabit.features.habits.data.UserToHabitRepository
import ru.apphabit.features.habits.model.Habit
import ru.apphabit.features.habits.model.UserToHabit

class UserToHabitVM(
    private val repository: UserToHabitRepository
) : ViewModel() {

    private val _userToHabits = MutableLiveData<List<UserToHabit>>()
    val userToHabits: LiveData<List<UserToHabit>> = _userToHabits


    fun getUserToHabitByUserId(userId: Int) {
        viewModelScope.launch {
            try {
                val habits = repository.getUserToHabitByUserId(userId)
                _userToHabits.postValue(habits)
            } catch (e: Exception) {
                Log.e("UserToHabitVM", "Error fetching habits", e)
            }
        }
    }

    fun deleteUserToHabit(habitId: Int) {
        viewModelScope.launch {
            try {
                repository.deleteUserToHabit(habitId)
                _userToHabits.value = _userToHabits.value?.filter { it.habitId != habitId }
            } catch (e: Exception) {
                Log.e("UserToHabitVM", "Error deleting habit", e)
            }
        }
    }
}
