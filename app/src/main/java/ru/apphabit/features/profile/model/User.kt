package ru.apphabit.features.profile.model

import java.time.LocalDate

data class User (
    val id: Int?,
    val name: String,
    val username: String,
    val email: String,
    val password: String,
    val dateRegistration: LocalDate,
    val dateLastActivity: LocalDate
)