package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.impl.sharedpreferences.FilterRepositoryImpl
import ru.practicum.android.diploma.domain.api.sharedpreferences.FilterIneractor
import ru.practicum.android.diploma.domain.api.sharedpreferences.FilterRepository
import ru.practicum.android.diploma.domain.impl.FilterIneractorImpl

val sharedPreferences = module {

    single<FilterRepository> {
        FilterRepositoryImpl(androidContext())
    }

    single<FilterIneractor> {
        FilterIneractorImpl(get())
    }

}
