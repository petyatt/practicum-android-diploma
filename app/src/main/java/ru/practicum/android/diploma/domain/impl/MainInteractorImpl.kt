package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.api.main.MainInteractor
import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.util.Resource

class MainInteractorImpl(private val repository: VacanciesRepository) : MainInteractor {
    override suspend fun searchVacancies(vacancy: String, page: Int) = repository.searchVacancies(vacancy, page)
    override suspend fun searchVacanciesWithFilters(
        vacancy: String?,
        page: Int,
        perPage: Int,
        area: String?,
        searchField: String?,
        industry: String?,
        salary: Int?,
        onlyWithSalary: Boolean
    ): Resource<Vacancies> = repository.searchVacanciesWithFilters(
        vacancy,
        page,
        perPage,
        area,
        searchField,
        industry,
        salary,
        onlyWithSalary
    )

}


