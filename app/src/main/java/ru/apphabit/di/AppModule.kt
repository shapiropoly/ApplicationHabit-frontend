package ru.apphabit.di


import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.apphabit.features.habits.view.HabitsVM

val appModule = module {
    viewModelOf(::HabitsVM)
}