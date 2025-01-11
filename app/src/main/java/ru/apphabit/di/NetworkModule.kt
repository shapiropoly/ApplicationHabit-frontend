package ru.apphabit.di

import BASE_URL
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.apphabit.features.habits.data.CategoryApiService
import ru.apphabit.features.habits.data.HabitApiService
import ru.apphabit.features.habits.data.UserToHabitApiService
import ru.apphabit.features.profile.data.UserApiService


fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

fun provideRetrofit(
    gsonConverterFactory: GsonConverterFactory
): Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(gsonConverterFactory)
    .build()

fun provideHabitService(retrofit: Retrofit): HabitApiService =
    retrofit.create(HabitApiService::class.java)

fun provideUserService(retrofit: Retrofit): UserApiService =
    retrofit.create(UserApiService::class.java)

fun provideCategoryService(retrofit: Retrofit): CategoryApiService =
    retrofit.create(CategoryApiService::class.java)

fun provideUserToHabitService(retrofit: Retrofit): UserToHabitApiService =
    retrofit.create(UserToHabitApiService::class.java)


val networkModule = module {
    single { provideConverterFactory() }
    single { provideRetrofit(get()) }
    single { provideHabitService(get()) }
    single { provideCategoryService(get()) }
    single { provideUserToHabitService(get()) }
    single { provideUserService(get()) }
}