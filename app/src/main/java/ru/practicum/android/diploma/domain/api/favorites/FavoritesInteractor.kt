package ru.practicum.android.diploma.domain.api.favorites

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetail

interface FavoritesInteractor {

    fun getAllVacancies(): Flow<List<VacancyDetail>>
    suspend fun addVacancy(vacancy: VacancyDetail)
    suspend fun removeVacancy(id: String)
    fun checkVacancy(id: String): Flow<Boolean>
}
