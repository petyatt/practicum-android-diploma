package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

interface VacanciesRepository {
    suspend fun searchVacancies(vacancy: String, page: Int): Resource<Vacancies>
    suspend fun getVacancy(id: String): Resource<Vacancy>
}
