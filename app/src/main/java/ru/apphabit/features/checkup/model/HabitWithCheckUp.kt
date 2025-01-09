package ru.apphabit.features.checkup.model

import ru.apphabit.features.habits.model.Habit
import java.time.LocalDate

data class HabitWithCheckUp (
    val habit: Habit,
    val frequency: String,
    val dateCheckUp: LocalDate,
    val checkUp: CheckUp?
)