package ru.apphabit.features.habits.model

data class Habit (
    val id: Int,
    val title: String,
    val description: String,
    val image: String,
    val categoryId: Int
)