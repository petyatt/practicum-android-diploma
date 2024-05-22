package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.util.Resource

interface VacanciesRepository {
    fun searchVacancies(vacancy: String, page: Int): Flow<Resource<Vacancies>>
    suspend fun getVacancy(id: String): Flow<Resource<VacancyDetail>>
}
