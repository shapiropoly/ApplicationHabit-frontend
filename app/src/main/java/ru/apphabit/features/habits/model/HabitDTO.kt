package ru.apphabit.features.habits.model

data class HabitDTO (
    val title: String,
    val description: String,
    val image: String,
    val categoryId: Int
)