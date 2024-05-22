package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.converters.VacanciesConverter
import ru.practicum.android.diploma.data.converters.VacanciesDescriptionConverter
import ru.practicum.android.diploma.data.impl.main.VacanciesRepositoryImpl
import ru.practicum.android.diploma.data.network.HeadHunterApiService
import ru.practicum.android.diploma.domain.api.main.MainInteractor
import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.impl.MainInteractorImpl
import ru.practicum.android.diploma.ui.main.MainViewModel

val main = module {

    viewModel {
        MainViewModel(get())
    }
    single<MainInteractor> {
        MainInteractorImpl(get())
    }
    single<VacanciesRepository> {
        VacanciesRepositoryImpl(get(), get())
    }

    single<VacanciesConverter> {
        VacanciesConverter()
    }
    single<VacanciesDescriptionConverter> {
        VacanciesDescriptionConverter()
    }

    single<HeadHunterApiService> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HeadHunterApiService::class.java)
    }
}
