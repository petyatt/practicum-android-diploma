package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

interface VacanciesRepository {
    suspend fun searchVacancies(vacancy: String, page: Int): Resource<Vacancies>
    suspend fun getVacancy(id: String): Resource<Vacancy>
    suspend fun searchVacanciesWithFilters(
        vacancy: String? = null,
        page: Int = 0,
        perPage: Int = 20,
        area: String? = null,
        searchField: String? = "name",
        industry: String? = null,
        salary: Int? = null,
        onlyWithSalary: Boolean = false
    ): Resource<Vacancies>
}



