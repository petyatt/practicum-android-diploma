package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.converters.VacancyDbConverter
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.impl.favorites.FavoritesRepositoryImpl
import ru.practicum.android.diploma.domain.api.favorites.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.favorites.FavoritesRepository
import ru.practicum.android.diploma.domain.impl.FavoritesInteractorImpl
import ru.practicum.android.diploma.ui.favorites.viewmodel.FavoritesViewModel

val favorites = module {

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database")
            .allowMainThreadQueries()
            .build()
    }

    single<VacancyDbConverter> { VacancyDbConverter() }

    single<FavoritesRepository> { FavoritesRepositoryImpl(get(), get()) }

    single<FavoritesInteractor> { FavoritesInteractorImpl(get()) }

    viewModel {
        FavoritesViewModel(get())
    }

}
