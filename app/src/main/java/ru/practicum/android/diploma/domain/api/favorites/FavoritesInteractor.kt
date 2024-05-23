package ru.practicum.android.diploma.domain.api.favorites

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface FavoritesInteractor {

    fun getAllVacancies(): Flow<List<Vacancy>>
    suspend fun addVacancy(vacancy: Vacancy)
    suspend fun removeVacancy(id: String)
    fun checkVacancy(id: String): Flow<Boolean>
}
