package ru.practicum.android.diploma.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.di.favorites
import ru.practicum.android.diploma.di.filter
import ru.practicum.android.diploma.di.main
import ru.practicum.android.diploma.di.vacancy

class App : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                favorites,
                filter,
                main,
                vacancy
            )
        }
    }
}
