package ru.apphabit.features.collections.model

data class Collection (
    val id: Int,
    val title: String,
    val description: String,
    val image: String,
    val collections: List<Collection> = listOf()
)