package ru.practicum.android.diploma.domain.api.favorites

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDescription

interface FavoritesRepository {

    fun getAllVacancies(): Flow<List<VacancyDescription>>
    suspend fun addVacancy(vacancy: VacancyDescription)
    suspend fun removeVacancy(vacancy: VacancyDescription)
    fun checkVacancy(id: String): Flow<Boolean>
}
