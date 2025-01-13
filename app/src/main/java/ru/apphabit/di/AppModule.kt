package ru.apphabit.di


import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.apphabit.features.habits.view.HabitsVM
import ru.apphabit.features.collections.view.CollectionsVM
import ru.apphabit.features.habits.view.CategoriesVM
import ru.apphabit.features.habits.view.UserToHabitVM
import ru.apphabit.features.profile.view.UsersVM



val appModule = module {
    viewModelOf(::HabitsVM)
    viewModelOf(::CollectionsVM)
    viewModelOf(::UsersVM)
    viewModelOf(::CategoriesVM)
    viewModelOf(::UserToHabitVM)
}