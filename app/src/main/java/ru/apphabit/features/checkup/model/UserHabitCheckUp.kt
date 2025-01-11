package ru.apphabit.features.checkup.model

import java.time.LocalDate

data class UserHabitCheckUp (
    val checkUpId: Int,
    val dateCheckUp: LocalDate,
    val userId: Int,
    val habitId: Int,
    val dateStart: LocalDate,
    val frequency: String,
    val replay: Int
)
