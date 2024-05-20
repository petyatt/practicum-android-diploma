package ru.practicum.android.diploma.domain.api.vacancy

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.util.Resource

interface VacancyInteractor {
    suspend fun getVacancy(id: String): Flow<Resource<VacancyDetail>>
}