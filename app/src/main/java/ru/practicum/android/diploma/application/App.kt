package ru.practicum.android.diploma.application

import android.app.Application
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import ru.practicum.android.diploma.di.favorites.favorites

class App : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                favorites
            )
        }
    }
}
