package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.Resource
import ru.practicum.android.diploma.data.network.response.VacancyResponse

interface HeadHunterVacanciesRepository {
    fun getVacancies(
        query: String? = null,
        page: Int = 0,
        perPage: Int = 20,
        salary: Int? = null,
        area: Int? = null,
        industry: String? = null,
        onlyWithSalary: Boolean = false
    ): Flow<Resource<VacancyResponse>>
}
