package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.impl.sharedpreferences.SharedPreferencesRepositoryImpl
import ru.practicum.android.diploma.domain.api.sharedpreferences.SharedPreferencesInteractor
import ru.practicum.android.diploma.domain.api.sharedpreferences.SharedPreferencesRepository
import ru.practicum.android.diploma.domain.impl.SharedPreferencesInteractorImpl

val sharedPreferences = module {

    single<SharedPreferencesRepository> {
        SharedPreferencesRepositoryImpl(androidContext())
    }

    single<SharedPreferencesInteractor> {
        SharedPreferencesInteractorImpl(get())
    }

}
