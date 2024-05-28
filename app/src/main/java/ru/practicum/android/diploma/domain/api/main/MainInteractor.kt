package ru.practicum.android.diploma.domain.api.main

import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.util.Resource

interface MainInteractor {
    suspend fun searchVacancies(vacancy: String, page: Int): Resource<Vacancies>
    suspend fun searchVacanciesWithFilters(
        vacancy: String? = null,
        page: Int = 0,
        perPage: Int = 20,
        area: Int? = null,
        searchField: String? = "name",
        industry: String? = null,
        salary: Int? = null,
        onlyWithSalary: Boolean = false
    ): Resource<Vacancies>
}
