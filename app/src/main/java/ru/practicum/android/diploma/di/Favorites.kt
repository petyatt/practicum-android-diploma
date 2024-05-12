package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.ui.favorites.FavoritesViewModel

val favorites = module {

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database")
            .allowMainThreadQueries()
            .build()
    }

    viewModel {
        FavoritesViewModel()
    }

}
