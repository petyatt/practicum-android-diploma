package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.impl.sharedpreferences.SharedPreferencesRepositoryImpl
import ru.practicum.android.diploma.domain.api.sharedpreferences.FilterIneractor
import ru.practicum.android.diploma.domain.api.sharedpreferences.SharedPreferencesRepository
import ru.practicum.android.diploma.domain.impl.FilterIneractorImpl

val sharedPreferences = module {

    single<SharedPreferencesRepository> {
        SharedPreferencesRepositoryImpl(androidContext())
    }

    single<FilterIneractor> {
        FilterIneractorImpl(get())
    }

}
