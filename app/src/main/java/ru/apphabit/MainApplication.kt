package ru.apphabit

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.apphabit.di.appModule
import ru.apphabit.di.dataModule
import ru.apphabit.di.networkModule


class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(networkModule)
            modules(dataModule)
            modules(appModule)
        }
    }
}