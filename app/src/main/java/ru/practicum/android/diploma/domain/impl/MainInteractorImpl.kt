package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.main.MainInteractor
import ru.practicum.android.diploma.domain.api.VacanciesRepository

class MainInteractorImpl(private val mainRepository: VacanciesRepository) : MainInteractor {
    override fun searchVacancies(vacancy: String, page: Int) = mainRepository.searchVacancies(vacancy, page)
}
