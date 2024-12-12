package ru.apphabit.features.habits.data

import android.util.Log
import retrofit2.Call
import ru.apphabit.features.habits.model.Habit

interface HabitRepository {
    suspend fun getAllHabits(): List<Habit>
    suspend fun getHabitById(id: Int): Habit
    suspend fun addHabit(habit: Habit): Habit
    fun updateHabit(id: Int, train: Habit): Call<Habit>
    fun deleteHabit(id:Int): Call<Habit>
}

class HabitRepositoryImpl(private val service: HabitApiService): HabitRepository {

    override suspend fun getAllHabits(): List<Habit> {
        val response = service.getAllHabits()
        if (response.isSuccessful) {
            val body = response.body()
            Log.d("habit", "Response: $body")
            return body ?: emptyList()
        } else {
            Log.e("habit", "Error: ${response.errorBody()?.string()}")
            return emptyList()
        }
    }

    override suspend fun addHabit(habit: Habit) : Habit {
        val body = service.addHabit(habit).body()!!
        return body
    }

    override suspend fun getHabitById(id: Int): Habit {
        val body = service.getHabitById(id).body()!!
        return body
    }

    override fun updateHabit(id: Int, train: Habit) : Call<Habit> {
        return service.updateHabit(id, train)
    }

    override fun deleteHabit(id: Int): Call<Habit> {
        return service.deleteHabit(id)
    }
}