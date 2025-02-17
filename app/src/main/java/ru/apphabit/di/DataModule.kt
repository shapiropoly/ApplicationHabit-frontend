package ru.apphabit.di

import org.koin.dsl.module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import ru.apphabit.features.collections.data.CollectionRepository
import ru.apphabit.features.collections.data.CollectionRepositoryImpl
import ru.apphabit.features.collections.data.HabitToCollectionRepository
import ru.apphabit.features.collections.data.HabitToCollectionRepositoryImpl
import ru.apphabit.features.habits.data.HabitRepository
import ru.apphabit.features.habits.data.HabitRepositoryImpl
import ru.apphabit.features.profile.data.UserRepository
import ru.apphabit.features.profile.data.UserRepositoryImpl
import ru.apphabit.features.habits.data.CategoryRepository
import ru.apphabit.features.habits.data.CategoryRepositoryImpl
import ru.apphabit.features.habits.data.UserToHabitRepository
import ru.apphabit.features.habits.data.UserToHabitRepositoryImpl


val dataModule = module {
    singleOf(::HabitRepositoryImpl) bind HabitRepository::class
    singleOf(::CategoryRepositoryImpl) bind CategoryRepository::class
    singleOf(::UserRepositoryImpl) bind UserRepository::class
    singleOf(::CollectionRepositoryImpl) bind CollectionRepository::class
    singleOf(::UserToHabitRepositoryImpl) bind UserToHabitRepository::class
    singleOf(::HabitToCollectionRepositoryImpl) bind HabitToCollectionRepository::class
}