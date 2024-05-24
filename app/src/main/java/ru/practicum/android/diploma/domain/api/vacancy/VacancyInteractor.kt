package ru.practicum.android.diploma.domain.api.vacancy

import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

interface VacancyInteractor {

    suspend fun get(id: String): Resource<Vacancy>
    suspend fun getIsFavorite(id: String): Boolean

    suspend fun setIsFavorite(vacancy: Vacancy, value: Boolean): Boolean
}
