package ru.apphabit.features.checkup.model

import java.time.LocalDate

data class CheckUp (
    val id: Int,
    val dateCheckUp: LocalDate,
    val userToHabitId: Int
)
