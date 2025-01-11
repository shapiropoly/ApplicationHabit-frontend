package ru.apphabit.features.collections.model

import ru.apphabit.features.habits.model.Habit

data class Collection (
    val id: Int,
    val title: String,
    val description: String,
    val image: String,
    val habits: List<Habit> = listOf()
)