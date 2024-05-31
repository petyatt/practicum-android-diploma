package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.api.main.MainInteractor
import ru.practicum.android.diploma.domain.models.Filter

class MainInteractorImpl(private val repository: VacanciesRepository) : MainInteractor {
    override suspend fun searchVacancies(vacancy: String, page: Int, filter: Filter) =
        repository.searchVacancies(vacancy, page, filter)
}
