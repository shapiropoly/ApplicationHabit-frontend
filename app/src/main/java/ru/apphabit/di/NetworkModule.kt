package ru.apphabit.di

import BASE_URL
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.apphabit.features.habits.data.CategoryApiService
import ru.apphabit.features.habits.data.HabitApiService


fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

fun provideRetrofit(
    gsonConverterFactory: GsonConverterFactory
): Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(gsonConverterFactory)
    .build()

fun provideHabitService(retrofit: Retrofit): HabitApiService =
    retrofit.create(HabitApiService::class.java)

fun provideCategoryService(retrofit: Retrofit): CategoryApiService =
    retrofit.create(CategoryApiService::class.java)

//fun provideTrainService(retrofit: Retrofit): TrainService =
//    retrofit.create(TrainService::class.java)
//
//fun provideWagonService(retrofit: Retrofit): WagonService =
//    retrofit.create(WagonService::class.java)

val networkModule = module {
    single { provideConverterFactory() }
    single { provideRetrofit(get()) }
    single { provideHabitService(get()) }
    single { provideCategoryService(get()) }

//    single { provideTrainService(get()) }
//    single { provideWagonService(get()) }
}