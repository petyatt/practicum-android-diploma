package ru.practicum.android.diploma.domain.api.main

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.domain.models.VacancyDescription
import ru.practicum.android.diploma.util.Resource

interface MainInteractor {

    fun searchVacancies(vacancy: String): Flow<Pair<Vacancies?, String?>>

    fun getVacancyDescription(vacancyId: String): Flow<Pair<VacancyDescription?, String?>>
}
