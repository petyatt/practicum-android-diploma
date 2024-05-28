package ru.practicum.android.diploma.di

import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.converters.FilterConverter
import ru.practicum.android.diploma.data.converters.VacanciesConverter
import ru.practicum.android.diploma.data.impl.vacancy.VacanciesRepositoryImpl
import ru.practicum.android.diploma.data.network.AuthInterceptor
import ru.practicum.android.diploma.data.network.HeadHunterApiService
import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.api.main.MainInteractor
import ru.practicum.android.diploma.domain.impl.MainInteractorImpl
import ru.practicum.android.diploma.ui.main.MainViewModel

val main = module {

    viewModel {
        MainViewModel(mainInteractor = get(), filterInteractor = get())
    }
    single<MainInteractor> {
        MainInteractorImpl(repository = get())
    }
    single<VacanciesRepository> {
        VacanciesRepositoryImpl(networkClient = get(), vacanciesConverter = VacanciesConverter(), filterConverter = FilterConverter())
    }

    single<HeadHunterApiService> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru")
            .client(OkHttpClient.Builder().addInterceptor(AuthInterceptor()).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HeadHunterApiService::class.java)
    }
}
