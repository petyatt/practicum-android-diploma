package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.impl.vacancy.HeadHunterVacanciesRepositoryImpl
import ru.practicum.android.diploma.domain.api.HeadHunterVacanciesRepository
import ru.practicum.android.diploma.domain.api.vacancy.VacancyInteractor
import ru.practicum.android.diploma.domain.impl.VacancyInteractorImpl
import ru.practicum.android.diploma.ui.vacancy.VacancyViewModel

val vacancy = module {
    single<HeadHunterVacanciesRepository> {
        HeadHunterVacanciesRepositoryImpl()
    }

    single<VacancyInteractor> {
        VacancyInteractorImpl(repository = get())
    }

    viewModel {
        VacancyViewModel(vacancyInteractor = get())
    }
}
