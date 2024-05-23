package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.api.main.MainInteractor

class MainInteractorImpl(private val mainRepository: VacanciesRepository) : MainInteractor {
    override suspend fun searchVacancies(vacancy: String, page: Int) = mainRepository.searchVacancies(vacancy, page)
}
