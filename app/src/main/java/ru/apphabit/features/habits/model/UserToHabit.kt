package ru.apphabit.features.habits.model

import com.google.gson.annotations.SerializedName
import ru.apphabit.features.profile.model.User
import java.time.LocalDate

data class UserToHabit (
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("habit_id") val habitId: Int,
    @SerializedName("frequency") val frequency: String,
    @SerializedName("replay") val reply: Int,
    @SerializedName("date_start") val dateStart: String,
    val habit: Habit? = null
)
