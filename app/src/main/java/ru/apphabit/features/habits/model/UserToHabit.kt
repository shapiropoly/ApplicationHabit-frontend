package ru.apphabit.features.habits.model

import ru.apphabit.features.profile.model.User
import java.time.LocalDate

data class UserToHabit (
    val id: Int,
    val user: User,
    val habit: Habit,
    val frequency: String,
    val reply: Int,
    val dateStart: LocalDate
)
