package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.api.vacancy.VacancyInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.util.Resource

class VacancyInteractorImpl(private val repository: VacanciesRepository) : VacancyInteractor {
    override suspend fun getVacancy(id: String): Flow<Resource<VacancyDetail>> = repository.getVacancy(id)
}
